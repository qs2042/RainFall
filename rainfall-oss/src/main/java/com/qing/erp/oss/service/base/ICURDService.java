package com.qing.erp.oss.service.base;


import com.qing.erp.oss.entity.ObjectMetaDataEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;

public interface ICURDService {
    /**
     * 默认块大小
     * 上传的时候必须设置分块大小.对象大小直接-1,分块大小[5m,5G],参数的单位是B,
     * 所以最小单位是：5 * 1024 * 1024 = 5242880.minio支持分块传输
     */
    public static final long defaultPartSize = 5242880;

    /**
     * 上传文件(签名直传)
     */
    HashMap<String, Object> uploadByPresigned(String bucketName, String objectName);

    /**
     * 上传文件(InputStream)
     */
    ObjectMetaDataEntity uploadObject(String bucketName, String objectName, InputStream input);

    ObjectMetaDataEntity uploadObject(String bucketName, String objectName, InputStream stream, long size, String contextType);

    /**
     * 上传文件(URL)
     */
    ObjectMetaDataEntity uploadObjectByUrl(String bucketName, String url);

    /**
     * 上传文件(FilePath)
     */
    ObjectMetaDataEntity uploadObjectByFilePath(String bucketName, String filePath);

    /**
     * 上传文件(MultipartFile)
     */
    ObjectMetaDataEntity uploadObjectByMultipartFile(MultipartFile file, String bucketName);

    /**
     * 上传文件(断点续传)
     */
//    ObjectMetaData uploadObjectByBreakpoint(String bucketName, String objectName, File file);

    /**
     * 上传文件(分片)
     */
//    ObjectMetaData uploadObjectByPart(String bucketName, String objectName, File file);

    /**
     * 上传文件(bytes)
     */
    ObjectMetaDataEntity uploadObjectByBytes(String bucketName, String objectName, byte[] bytes);

    /**
     * 复制文件
     *
     * @param sourceBucket      源Bucket名称
     * @param sourceObject      源Object名称
     * @param destinationBucket 目标Bucket名称
     * @param destinationObject 目标Object名称
     */
//    void copyObject(String sourceBucket, String sourceObject, String destinationBucket, String destinationObject);

    /**
     * 移动文件
     *
     * @param sourceBucket      源Bucket名称
     * @param sourceObject      源Object名称
     * @param destinationBucket 目标Bucket名称
     * @param destinationObject 目标Object名称
     */
//    void moveObject(String sourceBucket, String sourceObject, String destinationBucket, String destinationObject);

    /**
     * 删除文件
     */
//    void removeObject(String bucketName, String objectName);
}
