package com.qing.erp.oss.service;

import com.qing.erp.oss.entity.BucketEntity;
import com.qing.erp.oss.entity.LinkEntity;
import com.qing.erp.oss.entity.ObjectMetaDataEntity;
import com.qing.erp.oss.holder.BaseHolder;
import com.qing.erp.oss.service.base.IBucketService;
import com.qing.erp.oss.service.base.ICURDService;
import com.qing.erp.oss.service.base.IObjectService;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BaseImpl implements IBucketService, IObjectService, ICURDService {
    @Autowired
    private ResourceLoader resourceLoader;

    private static File ROOT_FILE = null;
    static {
        try {
            ROOT_FILE = ResourceUtils.getFile("classpath:static");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String handlerBucketName(String bucketName) {
        if (null == bucketName || bucketName.isEmpty()) {
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        return bucketName;
    }


    @SneakyThrows
    @Override
    public List<BucketEntity> getBuckets() {
        File file = ResourceUtils.getFile("classpath:static");
        return Arrays
                .stream(Objects.requireNonNull(file.listFiles()))
                .filter(File::isDirectory)
                .map(v -> {
                    val b = new BucketEntity();
                    b.setName(v.getName());
                    return b;
                }).collect(Collectors.toList());
    }

    @Override
    public BucketEntity getBucket(String bucketName) {
        return getBuckets().stream().filter(v -> v.getName().equals(bucketName)).findFirst().orElse(null);
    }

    @SneakyThrows
    @Override
    public BucketEntity createBucket(String bucketName) {
        // 是否存在
        val bucket = getBucket(bucketName);
        if (bucket != null) {
            return bucket;
        }

        File file = ResourceUtils.getFile("classpath:static");

        val f = new File(file.getAbsolutePath() + "//" + bucketName);
        if (f.mkdir()) {
            return getBucket(bucketName);
        }

        return null;
    }

    @SneakyThrows
    @Override
    public void deleteBucket(String bucketName) {
        File file = ResourceUtils.getFile("classpath:static");
        val f = new File(file.getAbsolutePath() + "//" + bucketName);
        System.out.println(f.delete());
    }

    @Override
    public boolean isBucketExist(String bucketName) {
        return getBucket(bucketName) != null;
    }

    @SneakyThrows
    @Override
    public String getBucketPolicy(String bucketName) {
        val f = new File(ROOT_FILE.getAbsolutePath() + "//" + bucketName + ".json");
        if (f.exists()) {
            return FileUtils.readFileToString(f);
        }
        return "";
    }

    @SneakyThrows
    @Override
    public void setBucketPolicy(String bucketName) {
        val f = new File(ROOT_FILE.getAbsolutePath() + "//" + bucketName + ".json");
        if (!f.exists()) {
            System.out.println(f.createNewFile());
        }
        FileUtils.write(f, "{}");
    }

    @SneakyThrows
    @Override
    public void setBucketPolicy(String bucketName, String config) {
        val f = new File(ROOT_FILE.getAbsolutePath() + "//" + bucketName + ".json");
        if (!f.exists()) {
            System.out.println(f.createNewFile());
        }
        FileUtils.write(f, config);
    }

    // TODO
    @Override
    public HashMap<String, Object> uploadByPresigned(String bucketName, String objectName) {
        return null;
    }

    @Override
    public ObjectMetaDataEntity uploadObject(String bucketName, String objectName, InputStream input) {
        return uploadObject(bucketName, objectName, input, -1, null);
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

        // 创建资源
        val f = new File(ROOT_FILE.getAbsolutePath() + "\\" + bucketName + "\\" +  objectName);

        try (FileOutputStream outputStream = new FileOutputStream(f)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = stream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        val omd = new ObjectMetaDataEntity();
        omd.setEtag("");
        omd.setVersionId("");
        omd.setName(objectName);
        omd.setBucket(bucketName);
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
        return uploadObject(bucketName, file.getOriginalFilename(), file.getInputStream(), -1, file.getContentType());
    }

    @Override
    public ObjectMetaDataEntity uploadObjectByBytes(String bucketName, String objectName, byte[] bytes) {
        return uploadObject(bucketName, objectName, new ByteArrayInputStream(bytes));
    }

    @Override
    public List<InputStream> getObjects(String bucketName) {
        return null;
    }

    @SneakyThrows
    @Override
    public InputStream getObject(String bucketName, String objectName) {
        val f = new File(ROOT_FILE.getAbsolutePath() + "//" + bucketName + "//" + objectName);
        if (f.exists()) {
            return new FileInputStream(f);
        }
        return null;
    }

    @Override
    public List<ObjectMetaDataEntity> getObjectsMetadata(String bucketName) {
        val file = new File(ROOT_FILE.getAbsolutePath() + "//" + bucketName);

        return Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .filter(v -> {
                    String name = v.getName();
                    String suffix = "_meta.json";

                    // 如果不是json格式
                    if (!"json".equals(name.split("\\.")[1])) {
                        return true;
                    }
                    // 是否为meta文件
                    return !name.endsWith("_meta.json");
                })
                .map(v -> getObjectMetadata(bucketName, v.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public ObjectMetaDataEntity getObjectMetadata(String bucketName, String objectName) {
        String prefix = objectName.split("\\.")[0];
        String suffix = objectName.split("\\.")[1];

        val fo = new File(ROOT_FILE.getAbsolutePath() + "//" + bucketName + "//" + objectName);
        if (!fo.exists()) {
            return null;
        }

        val omd = new ObjectMetaDataEntity();
        omd.setBucket(bucketName);
        omd.setName(prefix);
        omd.setStorageClass(suffix);
        omd.setDir(fo.isDirectory());
        omd.setSize(fo.length());
        omd.setLastModified(LocalDateTime.ofInstant(Instant.ofEpochMilli(fo.lastModified()), ZoneId.systemDefault()));

        val f = new File(ROOT_FILE.getAbsolutePath() + "//" + bucketName + "//" + prefix + "_meta.json");
        if (!f.exists()) {
            return omd;
        }

        omd.setEtag("test");
        omd.setOwner(null);
        omd.setLatest(false);
        omd.setVersionId(null);
        omd.setUserMetadata(null);
        return omd;
    }

    public LinkEntity getObjectUrl(String uuid) {
        val link = BaseHolder.links
                .stream()
                .filter(v -> uuid.equals(v.getUuid()))
                .findFirst()
                .orElse(null);
        if (link == null) {
            return null;
        }

        // 如果图片过期
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createdAt = link.getCreatedAt();
        Integer expires = link.getExpires();

        LocalDateTime expirationTime = createdAt.plusSeconds(expires);
        if (now.isAfter(expirationTime)) {
            System.out.println("删除前: " + BaseHolder.links.size());
            BaseHolder.links.remove(link);
            System.out.println("删除后: " + BaseHolder.links.size());
            return null;
        }

        return link;
    }

    @Override
    public String getObjectUrl(String bucketName, String objectName, Integer expires) {
        val obj = getObject(bucketName, objectName);
        if (obj == null) {
            return null;
        }

        val uuid = UUID.randomUUID().toString().replace("-", "_");
        val url = "http://localhost:88/oss/base/getObjectUrl?uuid=" + uuid;

        val linkEntity = new LinkEntity();
        linkEntity.setBucketName(bucketName);
        linkEntity.setObjectName(objectName);
        linkEntity.setCreatedAt(LocalDateTime.now());
        linkEntity.setExpires(expires);
        linkEntity.setObject(obj);
        linkEntity.setUrl(url);
        linkEntity.setUuid(uuid);
        BaseHolder.links.add(linkEntity);
        return url;
    }

    @Override
    public boolean isObjectExist(String bucketName, String objectName) {
        return getObject(bucketName, objectName) != null;
    }

    @Override
    public ObjectMetaDataEntity updateObjectMetadata(String bucketName, String objectName, ObjectMetaDataEntity omd) {
        return null;
    }
}
