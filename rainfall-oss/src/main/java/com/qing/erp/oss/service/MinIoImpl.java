package com.qing.erp.oss.service;

import com.qing.erp.oss.entity.BucketEntity;
import com.qing.erp.oss.entity.ObjectMetaDataEntity;
import com.qing.erp.oss.service.base.IBucketService;
import com.qing.erp.oss.service.base.ICURDService;
import com.qing.erp.oss.service.base.IObjectService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MinIoImpl implements IBucketService, IObjectService, ICURDService {
    @Autowired
    private MinioClient mc;

    private String handlerBucketName(String bucketName) {
        if (null == bucketName || bucketName.isEmpty()) {
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        return bucketName;
    }

    @SneakyThrows
    @Override
    public List<BucketEntity> getBuckets() {
        return mc
                .listBuckets()
                .stream().map(v -> {
                    val bucket = new BucketEntity();
                    BeanUtils.copyProperties(v, bucket);
                    bucket.setCreatedAt(v.creationDate().toLocalDateTime());
                    return bucket;
                }).collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public BucketEntity getBucket(String bucketName) {
        val list = getBuckets();
        return list.stream().filter(v -> v.getName().equals(bucketName)).findFirst().orElse(null);
    }

    @SneakyThrows
    @Override
    public BucketEntity createBucket(String bucketName) {
        if (null == bucketName) {
            return null;
        }
        mc.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        return getBucket(bucketName);
    }

    @SneakyThrows
    @Override
    public void deleteBucket(String bucketName) {
        if (null == bucketName) {
            return;
        }
        mc.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());

    }

    @SneakyThrows
    @Override
    public boolean isBucketExist(String bucketName) {
        if (null == bucketName) {
            return false;
        }
        return mc.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    @SneakyThrows
    @Override
    public String getBucketPolicy(String bucketName) {
        return mc.getBucketPolicy(GetBucketPolicyArgs.builder().bucket(bucketName).build());
    }

    @SneakyThrows
    @Override
    public void setBucketPolicy(String bucketName) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("    \"Statement\": [\n");
        builder.append("        {\n");
        builder.append("            \"Action\": [\n");
        builder.append("                \"s3:GetBucketLocation\",\n");
        builder.append("                \"s3:ListBucket\"\n");
        builder.append("            ],\n");
        builder.append("            \"Effect\": \"Allow\",\n");
        builder.append("            \"Principal\": \"*\",\n");
        builder.append("            \"Resource\": \"arn:aws:s3:::my-bucketname\"\n");
        builder.append("        },\n");
        builder.append("        {\n");
        builder.append("            \"Action\": \"s3:GetObject\",\n");
        builder.append("            \"Effect\": \"Allow\",\n");
        builder.append("            \"Principal\": \"*\",\n");
        builder.append("            \"Resource\": \"arn:aws:s3:::my-bucketname/myobject*\"\n");
        builder.append("        }\n");
        builder.append("    ],\n");
        builder.append("    \"Version\": \"2012-10-17\"\n");
        builder.append("}\n");
        mc.setBucketPolicy(
                SetBucketPolicyArgs.builder().bucket(bucketName).config(builder.toString()).build()
        );
    }

    @SneakyThrows
    public void setBucketPolicy(String bucketName, String config) {
        mc.setBucketPolicy(
                SetBucketPolicyArgs.builder().bucket(bucketName).config(config).build()
        );
    }

    @SneakyThrows
    @Override
    public HashMap<String, Object> uploadByPresigned(String bucketName, String objectName) {
        bucketName = handlerBucketName(bucketName);

        // 判断存储桶是否存在, 如果不存在则创建
        if (!isBucketExist(bucketName)) {
            // 创建桶
            createBucket(bucketName);
            // 设置策略...
        }

        val r = new HashMap<String, Object>();
        // bucket
        r.put("bucket", bucketName);
        // objectName
        r.put("objectName", objectName);
        // 访问秘钥
        r.put("accessKey", null);
        // 策略
        r.put("policy", getBucketPolicy(bucketName));
        // 签名
        r.put("signature", null);
        // 过期时间(30s)
        long expireTime = System.currentTimeMillis() + 30 * 1000;
        r.put("expire", String.valueOf(expireTime / 1000));
        // 文件UUID
        String uuid = UUID.randomUUID().toString().replace("-", "_");
        String fileName = uuid + "_" + objectName;
        r.put("uuid", uuid);

        // OSS主机
        HashMap<String, String> reqParams = new HashMap<>();
        reqParams.put("response-content-type", "application/json");

        String presigned = mc.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        // 这里必须是PUT,如果是GET的话就是文件访问地址了.如果是POST上传会报错.
                        .method(Method.PUT)
                        .bucket(bucketName).object(fileName)
                        .expiry(60 * 5, TimeUnit.SECONDS)
                        .extraQueryParams(reqParams)
                        .build()
        );
        r.put("host", presigned);
        r.put("fileName", fileName);
        return r;
    }

    @SneakyThrows
    @Override
    public ObjectMetaDataEntity uploadObject(String bucketName, String objectName, InputStream stream) {
        return uploadObject(bucketName, objectName, stream, -1, null);
    }

    @SneakyThrows
    @Override
    public ObjectMetaDataEntity uploadObject(String bucketName, String objectName, InputStream stream, long size, String contextType) {
        bucketName = handlerBucketName(bucketName);

        // 判断存储桶是否存在, 如果不存在则创建
        if (!isBucketExist(bucketName)) {
            // 创建桶
            createBucket(bucketName);
            // 设置策略...
        }

        String uuid = UUID.randomUUID().toString().replace("-", "_");
        objectName = uuid + "_" + objectName;

        val builder = PutObjectArgs
                .builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(stream, size, defaultPartSize);

        if (contextType != null) {
            builder.contentType(contextType);
        }

        val r = mc.putObject(builder.build());
        val omd = new ObjectMetaDataEntity();
        omd.setEtag(r.etag());
        omd.setVersionId(r.versionId());
        omd.setName(r.object());
        omd.setBucket(r.bucket());
        // TODO: region

        stream.close();
        return omd;
    }

    @SneakyThrows
    @Override
    public ObjectMetaDataEntity uploadObjectByUrl(String bucketName, String url) {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        val contentType = conn.getHeaderField("content-type").split("/");

        String objectName = UUID.randomUUID().toString().replace("-", "_");
        if (contentType.length >= 2) {
            objectName += "_" + contentType[0] + "." + contentType[1];
        } else {
            objectName += ".error";
        }

        return uploadObject(bucketName, objectName, conn.getInputStream());
    }

    @SneakyThrows
    @Override
    public ObjectMetaDataEntity uploadObjectByFilePath(String bucketName, String filePath) {
        val p = Paths.get(filePath);
        return uploadObject(bucketName, p.getFileName().toString(), Files.newInputStream(p));
    }

    @SneakyThrows
    @Override
    public ObjectMetaDataEntity uploadObjectByMultipartFile(MultipartFile file, String bucketName) {
        // 原本应该是 stream(file.getInputStream(), -1, -1) 现在直接套别的api
        return uploadObject(bucketName, file.getOriginalFilename(), file.getInputStream(), -1, file.getContentType());
    }

    @Override
    public ObjectMetaDataEntity uploadObjectByBytes(String bucketName, String objectName, byte[] bytes) {
        return uploadObject(bucketName, objectName, new ByteArrayInputStream(bytes));
    }

    @SneakyThrows
    @Override
    public List<InputStream> getObjects(String bucketName) {
        val list = getObjectsMetadata(bucketName);
        return list.stream().map(v -> getObject(bucketName, v.getName())).collect(Collectors.toList());
    }

    @Override
    public InputStream getObject(String bucketName, String objectName) {
        try {
            return mc.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            // TODO: 记得写日志
            return null;
        }
    }

    @SneakyThrows
    @Override
    public List<ObjectMetaDataEntity> getObjectsMetadata(String bucketName) {
        val results = mc.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());

        return StreamSupport.stream(results.spliterator(), false)
                .map(v -> {
                    try {
                        // 基础信息
                        val item = v.get();
                        val omd = new ObjectMetaDataEntity();
                        BeanUtils.copyProperties(item, omd);
                        omd.setName(item.objectName());
                        omd.setOwner(new ObjectMetaDataEntity.Owner(item.owner().id(), item.owner().displayName()));
                        omd.setLastModified(item.lastModified().toLocalDateTime());
                        // TODO: isLatest, versionId, userMetadata, isDir, encodingType 这几个属性没测试是否可以正常被复制过来

                        // 其他信息
                        val sor = mc.statObject(StatObjectArgs.builder().bucket(bucketName).object(item.objectName()).build());
                        if (null != sor) {
                            val retentionMode = Optional.ofNullable(sor.retentionMode());
                            retentionMode.ifPresent(mode -> omd.setRetentionMode(mode.name()));

                            val retentionRetainUntilDate = Optional.ofNullable(sor.retentionRetainUntilDate());
                            retentionRetainUntilDate.ifPresent(date -> omd.setRetentionRetainUntilDate(date.toLocalDateTime()));

                            omd.setLegalHold(sor.legalHold().status());

                            omd.setDeleteMarker(sor.deleteMarker());

                            // TODO: ...
                            sor.region();
                            sor.headers();
                            sor.contentType();
                        }

                        return omd;
                    } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());
    }

    @SneakyThrows
    @Override
    public ObjectMetaDataEntity getObjectMetadata(String bucketName, String objectName) {
        // 为了方便就不重复写代码了
        val list = getObjectsMetadata(bucketName);
        return list.stream()
                .filter(v -> v.getName().equals(objectName)).findFirst().orElse(null);
    }

    @SneakyThrows
    @Override
    public String getObjectUrl(String bucketName, String objectName, Integer expires) {
        if (expires == null || expires <= 60) {
            // 60秒打底
            expires = 60;
        }
        return mc.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .expiry(expires, TimeUnit.SECONDS)
                .method(Method.GET)
                .build()
        );
    }

    @Override
    public boolean isObjectExist(String bucketName, String objectName) {
        return getObject(bucketName, objectName) != null;
    }

    // TODO: 之后写
    @Override
    public ObjectMetaDataEntity updateObjectMetadata(String bucketName, String objectName, ObjectMetaDataEntity omd) {
        return null;
    }
}
