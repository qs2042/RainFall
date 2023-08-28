package com.qing.erp.module.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ComponentScan({
    "com.qing.erp.module.base.properties",
    "com.qing.erp.module.base.config",
    "com.qing.erp.module.base.controller"
})
@Configuration
@Conditional(EnableBaseCondition.class)
@Slf4j
public class BaseConfiguration {
    @PostConstruct
    public void init() {
        log.info("正在启动: 微服务默认配置");
    }
}
