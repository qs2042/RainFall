/*
package com.qing.erp.oss.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.qing.erp.common.data.R;
import com.qing.erp.oss.entity.BucketEntity;
import com.qing.erp.oss.entity.ObjectMetaDataEntity;
import com.qing.erp.oss.service.base.IBucketService;
import com.qing.erp.oss.service.base.ICURDService;
import com.qing.erp.oss.service.base.IObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

//@Service
public class AlibabaCloudImpl implements IBucketService, IObjectService, ICURDService {
//    @Autowired
    OSS ossClient;

    private String endpoint;
    private String accessId;

    @Override
    public List<BucketEntity> getBuckets() {
        return null;
    }

    @Override
    public BucketEntity getBucket(String bucketName) {
        return null;
    }

    @Override
    public BucketEntity createBucket(String bucketName) {
        return null;
    }

    @Override
    public void deleteBucket(String bucketName) {

    }

    @Override
    public boolean isBucketExist(String bucketName) {
        return false;
    }

    @Override
    public String getBucketPolicy(String bucketName) {
        return null;
    }

    @Override
    public void setBucketPolicy(String bucketName) {

    }

    @Override
    public void setBucketPolicy(String bucketName, String config) {

    }

    @Override
    public HashMap<String, Object> uploadByPresigned(String bucketName, String objectName) {
        // https://gulimall-wan.oss-cn-shanghai.aliyuncs.com
        String host = "https://" + bucketName + "." + endpoint;

        // callbackUrl为 上传回调服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
//        String callbackUrl = "http://88.88.88.88:8888";

        // 文件在bucket存储目录，若不存在则会自动创建路径。使用日期作为目录
        String dir = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/";

        // 创建OSSClient实例。这里是alicloud starter自动配置，可自动注入
        //OSS ossClient = new OSSClientBuilder().build(endpoint, accessId, accessKey);

        Map<String, String> respMap = null;
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);//
            // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            // respMap.put("expire", formatISO8601Date(expiration));

            // 下面是跨域设置，在网关统一解决跨域
*/
/*            JSONObject jasonCallback = new JSONObject();
            jasonCallback.put("callbackUrl", callbackUrl);
            jasonCallback.put("callbackBody",
                    "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
            jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
            String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
            respMap.put("callback", base64CallbackBody);

            JSONObject ja1 = JSONObject.fromObject(respMap);
            // System.out.println(ja1.toString());
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST");
            response(request, response, ja1.toString());*//*


        } catch (Exception e) {
            // Assert.fail(e.getMessage());
            System.out.println(e.getMessage());
        } finally {
            ossClient.shutdown();
        }
        return R.ok().put("data", respMap);
    }

    @Override
    public ObjectMetaDataEntity uploadObject(String bucketName, String objectName, InputStream input) {
        return null;
    }

    @Override
    public ObjectMetaDataEntity uploadObject(String bucketName, String objectName, InputStream stream, long size, String contextType) {
        return null;
    }

    @Override
    public ObjectMetaDataEntity uploadObjectByUrl(String bucketName, String url) {
        return null;
    }

    @Override
    public ObjectMetaDataEntity uploadObjectByFilePath(String bucketName, String filePath) {
        return null;
    }

    @Override
    public ObjectMetaDataEntity uploadObjectByMultipartFile(MultipartFile file, String bucketName) {
        return null;
    }

    @Override
    public ObjectMetaDataEntity uploadObjectByBytes(String bucketName, String objectName, byte[] bytes) {
        return null;
    }

    @Override
    public List<InputStream> getObjects(String bucketName) {
        return null;
    }

    @Override
    public InputStream getObject(String bucketName, String objectName) {
        return null;
    }

    @Override
    public List<ObjectMetaDataEntity> getObjectsMetadata(String bucketName) {
        return null;
    }

    @Override
    public ObjectMetaDataEntity getObjectMetadata(String bucketName, String objectName) {
        return null;
    }

    @Override
    public String getObjectUrl(String bucketName, String objectName, Integer expires) {
        return null;
    }

    @Override
    public boolean isObjectExist(String bucketName, String objectName) {
        return false;
    }

    @Override
    public ObjectMetaDataEntity updateObjectMetadata(String bucketName, String objectName, ObjectMetaDataEntity omd) {
        return null;
    }
}
*/
