package com.qing.erp.oss.config;

import com.qing.erp.oss.properties.BaseProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
@EnableConfigurationProperties(BaseProperties.class)
public class BaseConfig {

    @Autowired
    private BaseProperties baseProperties;
}
