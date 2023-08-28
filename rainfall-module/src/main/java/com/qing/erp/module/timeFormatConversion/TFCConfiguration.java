package com.qing.erp.module.timeFormatConversion;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@ComponentScan("com.qing.erp.module.timeFormatConversion.config")
@Configuration
@Slf4j
public class TFCConfiguration {
    @PostConstruct
    public void init() {
        log.info("正在启动: 时间转换器");
    }
}
