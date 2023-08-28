package com.qing.erp.search;

import com.qing.erp.search.service.HighRestImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class SearchApplicationTests {
    @Autowired
    HighRestImpl highRest;

    @Test
    void index() {
        System.out.println("index");
        System.out.println(highRest.getIndexList());
        System.out.println();
        System.out.println("test是否存在: " + highRest.existsIndex("test"));
        System.out.println("创建test是否成功: " + highRest.createIndex("test", HighRestImpl.properties));
        System.out.println("获取test信息: " + highRest.getIndex("test"));
        System.out.println();
        System.out.println(highRest.getIndexList());
        System.out.println("删除test是否成功: " + highRest.deleteIndex("test").isAcknowledged());
        System.out.println(highRest.getIndexList());
        System.out.println();
    }

    @Test
    void doc() {
        System.out.println("index-customer: " + highRest.getDocList("customer"));
        System.out.println("index-qs2042: " + highRest.getDocList("qs2042"));

        System.out.println("index-4是否存在: " + highRest.existsDoc("qs2042", "4"));
        System.out.println("创建index-4是否成功: " + highRest.createDoc("test", "4", "username", "admin").status());
        System.out.println("获取index-4信息: " + highRest.getDoc("test", "4").getSourceAsString());
        System.out.println();
        System.out.println(highRest.getDocList("qs2042"));
        System.out.println("删除index-4是否成功: " + highRest.deleteDoc("test", "4").status());
        System.out.println(highRest.getDocList("qs2042"));
        System.out.println();
        System.out.println();
    }

}
