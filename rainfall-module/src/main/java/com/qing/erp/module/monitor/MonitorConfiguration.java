package com.qing.erp.module.monitor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ComponentScan("com.qing.erp.module.monitor.properties")
@ComponentScan("com.qing.erp.module.monitor.config")
@ComponentScan("com.qing.erp.module.monitor.controller")
@Configuration
@Slf4j
public class MonitorConfiguration {
    @PostConstruct
    public void init() {
        log.info("正在启动: 后台监控");
    }
}
