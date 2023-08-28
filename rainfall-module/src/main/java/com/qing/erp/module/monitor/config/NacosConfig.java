package com.qing.erp.module.monitor.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.naming.NamingMaintainService;
import com.alibaba.nacos.api.naming.NamingService;
import com.qing.erp.common.module.nacos.NacosClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NacosConfig {
    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String nacosServerAddr;

    // 服务注册/发现
    @SneakyThrows
    @Bean
    public NamingService namingService() {
        return NacosFactory.createNamingService(nacosServerAddr);
    }

    // 配置管理
    @SneakyThrows
    @Bean
    public ConfigService configService() {
        return NacosFactory.createConfigService(nacosServerAddr);
    }

    // 维护操作(集群管理、配置备份和恢复)
    @SneakyThrows
    @Bean
    public NamingMaintainService namingMaintainService() {
        return NacosFactory.createMaintainService(nacosServerAddr);
    }

    // 自己接入的API
    @Bean
    public NacosClient nacosClient() {
        return NacosClient.build(nacosServerAddr);
    }
}
