package com.qing.erp.oss.service.base;


import com.qing.erp.oss.entity.ObjectMetaDataEntity;

import java.io.InputStream;
import java.util.List;

public interface IObjectService {
    /**
     * 获取指定Bucket的所有文件
     */
    List<InputStream> getObjects(String bucketName);

    /**
     * 获取指定Bucket的指定文件
     */
    InputStream getObject(String bucketName, String objectName);

    /**
     * 获取指定Bucket中的所有文件(信息)
     */
    List<ObjectMetaDataEntity> getObjectsMetadata(String bucketName);

    /**
     * 获取指定Bucket中的指定文件(信息)
     */
    ObjectMetaDataEntity getObjectMetadata(String bucketName, String objectName);

    /**
     * 获取指定Bucket的指定文件(外链)
     */
    String getObjectUrl(String bucketName, String objectName, Integer expires);

    // 判断 Object 是否存在
    boolean isObjectExist(String bucketName, String objectName);

    // 更新文件元信息
    ObjectMetaDataEntity updateObjectMetadata(String bucketName, String objectName, ObjectMetaDataEntity omd);

    // 获取 Bucket 的生命周期规则
    // 设置 Bucket 的生命周期规则

    // 获取 Bucket 的跨域资源共享（CORS）配置
    // 设置 Bucket 的跨域资源共享（CORS）配置

    // 获取 Bucket 的防盗链配置
    // 设置 Bucket 的防盗链配置
}
