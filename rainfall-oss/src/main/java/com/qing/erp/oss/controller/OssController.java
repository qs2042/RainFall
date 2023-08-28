package com.qing.erp.oss.controller;

import com.qing.erp.common.data.R;
import com.qing.erp.common.web.SpringMVCUtil;
import com.qing.erp.oss.entity.ObjectMetaDataEntity;
import com.qing.erp.oss.service.MinIoImpl;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;

@RestController
@RequestMapping("oss")
public class OssController {

    @Autowired
    private MinIoImpl impl;

    // bucket
    @GetMapping("/getBuckets")
    public R getBuckets() {
        return R.ok().addList(impl.getBuckets());
    }

    @GetMapping("/getBucket")
    public R getBucket(String bucketName) {
        return R.ok().addMap(impl.getBucket(bucketName));
    }

    @PostMapping("/createBucket")
    public R createBucket(String bucketName) {
        val bucket = impl.createBucket(bucketName);
        return R.ok()
                .dataAdd("isSuccess", bucket != null)
                .addMap(bucket);
    }

    @PostMapping("/deleteBucket")
    public R deleteBucket(String bucketName) {
        impl.deleteBucket(bucketName);
        return R.ok();
    }

    @GetMapping("/isBucketExist")
    public R isBucketExist(String bucketName) {
        return R.ok().addResult(impl.isBucketExist(bucketName));
    }

    @GetMapping("/getBucketPolicy")
    public R getBucketPolicy(String bucketName) {
        return R.ok().addResult(impl.getBucketPolicy(bucketName));
    }
    // setBucketPolicy

    // object
    @GetMapping("/getObjects")
    public R getObjects(String bucketName) {
        return R.error("暂不支持获取多个文件");
    }

    @GetMapping("/getObject")
    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName) {
        val resp = SpringMVCUtil.getResp();

        val object = impl.getObject(bucketName, objectName);
        IOUtils.copy(object, resp.getOutputStream());
        object.close();

        resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(objectName, "UTF-8"));
        return null;
    }

    @GetMapping("/getObjectsMetadata")
    public R getObjectsMetadata(String bucketName) {
        return R.ok().addList(impl.getObjectsMetadata(bucketName));
    }

    @GetMapping("/getObjectMetadata")
    public R getObjectMetadata(String bucketName, String objectName) {
        return R.ok().addMap(impl.getObjectMetadata(bucketName, objectName));
    }

    @GetMapping("/getObjectUrl")
    public R getObjectUrl(String bucketName, String objectName, Integer expires) {
        return R.ok().addResult(impl.getObjectUrl(bucketName, objectName, expires));
    }

    @GetMapping("/isObjectExist")
    public R isObjectExist(String bucketName, String objectName) {
        return R.ok().addResult(impl.isObjectExist(bucketName, objectName));
    }

    @PostMapping("/updateObjectMetadata")
    public R updateObjectMetadata(String bucketName, String objectName, ObjectMetaDataEntity omd) {
        return R.error();
    }

    // CUDA
    @PostMapping("/uploadByPresigned")
    public R uploadByPresigned(
            @RequestParam(value = "bucketName", required = false) String bucketName,
            String objectName
    ) {
        return R.ok().addMap(impl.uploadByPresigned(bucketName, objectName));
    }

    @PostMapping("/uploadObjectByUrl")
    public R uploadObjectByUrl(
            @RequestParam(value = "bucketName", required = false) String bucketName,
            String url
    ) {
        return R.ok().addMap(impl.uploadObjectByUrl(bucketName, url));
    }

    @PostMapping("/upload")
    public R uploadObjectByMultipartFile(
            MultipartFile file,
            @RequestParam(value = "bucketName", required = false, defaultValue = "private") String bucketName
    ) {

        return R.ok().addMap(impl.uploadObjectByMultipartFile(file, bucketName));
    }

    // uploadObjectByBreakpoint
    // uploadObjectByPart
    // uploadObjectByBytes

    public void copyObject(String sourceBucket, String sourceObject, String destinationBucket, String destinationObject) {

    }

    public void moveObject(String sourceBucket, String sourceObject, String destinationBucket, String destinationObject) {

    }

    public void removeObject(String bucketName, String objectName) {

    }
}
