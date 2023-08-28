package com.qing.erp.oss.service.base;


import com.qing.erp.oss.entity.BucketEntity;

import java.util.List;

/*
removeIncompleteUpload  ...

presignedGetObject      ...
presignedPutObject      ...
presignedPostPolicy     ...

判断文件是否存在
判断文件夹是否存在
根据文件前置查询文件
获取文件流
断点下载
获取路径下文件列表
拷贝文件
批量删除文件
给presigned URL设置策略
 */

public interface IBucketService {
    /**
     * 获取所有Bucket信息
     */
    List<BucketEntity> getBuckets();

    /**
     * 获取指定Bucket信息
     */
    BucketEntity getBucket(String bucketName);

    /**
     * 创建Bucket
     */
    BucketEntity createBucket(String bucketName);

    /**
     * 删除Bucket
     */
    void deleteBucket(String bucketName);

    // 判断 Bucket 是否存在
    boolean isBucketExist(String bucketName);

    // 查询policy
    String getBucketPolicy(String bucketName);

    // 设置policy
    void setBucketPolicy(String bucketName);
    void setBucketPolicy(String bucketName, String config);
}
