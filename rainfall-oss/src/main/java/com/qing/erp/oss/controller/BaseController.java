package com.qing.erp.oss.controller;

import com.qing.erp.common.data.E;
import com.qing.erp.common.data.R;
import com.qing.erp.common.web.SpringMVCUtil;
import com.qing.erp.oss.entity.ObjectMetaDataEntity;
import com.qing.erp.oss.service.BaseImpl;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;

@RestController
@RequestMapping("base")
public class BaseController {

    @Autowired
    private BaseImpl impl;

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
        if (bucketName == null) {
            return R.error();
        }
        val bucket = impl.createBucket(bucketName);
        return R.ok().addMap(bucket);
    }

    @PostMapping("/deleteBucket")
    public R deleteBucket(String bucketName) {
        if (bucketName == null) {
            return R.error();
        }
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
    @GetMapping("/setBucketPolicy")
    public R setBucketPolicy(String bucketName, String config) {
        impl.setBucketPolicy(bucketName, config);
        return R.ok();
    }

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

    @GetMapping("/genObjectUrl")
    public R genObjectUrl(
            String bucketName,
            String objectName,
            @RequestParam(value = "expires", required = false, defaultValue = "30") Integer expires
    ) {
        return R.ok().addResult(impl.getObjectUrl(bucketName, objectName, expires));
    }

    @SneakyThrows
    @GetMapping("/getObjectUrl")
    public InputStream getObjectUrl(String uuid) {
        val link = impl.getObjectUrl(uuid);
        if (link == null) {
            throw new E("获取失败");
        }

        val resp = SpringMVCUtil.getResp();
        IOUtils.copy(link.getObject(), resp.getOutputStream());

        resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(link.getObjectName(), "UTF-8"));
        return null;
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

}
