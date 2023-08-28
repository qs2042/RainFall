package com.qing.erp.test.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class IndexConfig implements InitializingBean {
    // 多个同时存在的bean(例如类型或是名称), 默认选择这个
    @Primary
    @Bean("testTp")
    public String testTp() {
        return "aaa";
    }

    @Bean("testTp")
    public String testTpPro() {
        return "bbb";
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
