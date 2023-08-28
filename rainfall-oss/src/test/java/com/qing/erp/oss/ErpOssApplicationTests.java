package com.qing.erp.oss;

import com.qing.erp.oss.service.BaseImpl;
import com.qing.erp.oss.service.MinIoImpl;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ErpOssApplicationTests {
    @Autowired
    MinIoImpl impl;

    @Autowired
    BaseImpl baseImpl;

    @Test
    void bucketInfo() {
        // 获取所有
        val list1 = impl.getBuckets();
        // 获取指定
        val list2 = impl.getBucket("2023-05-03");
        val list3 = impl.getBucket("2099-05-03");

        System.out.println(list1);
        System.out.println(list2);
        System.out.println(list3);
    }

    @Test
    void objectInfo() {
        System.out.println(impl.getObjectsMetadata("public"));

        System.out.println(impl.getObjectMetadata("public", "credentials.json"));
//        System.out.println(impl.getObjectMetadata("public", "error.json"));

        System.out.println(impl.getObject("public", "credentials.json"));
//        System.out.println(impl.getObject("public", "error.json"));

    }

    @Test
    void baseBucketInfo() {
        System.out.println(baseImpl.getBuckets());
        System.out.println(baseImpl.getBucket("a1"));
        System.out.println(baseImpl.getBucket("b1"));
        System.out.println();

        System.out.println(baseImpl.createBucket("a3"));
    }

}
