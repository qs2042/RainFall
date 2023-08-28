package com.qing.erp.oss.config;

import com.qing.erp.common.network.NetworkUtil;
import com.qing.erp.oss.properties.MinioProperties;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinioConfig {

    @Autowired
    private MinioProperties minioProperties;

    @Bean
    public MinioClient minioClient() {
        // 如果配置参数为空

        // 如果无法ping成功

        // 如果无法访问
//        if (!NetworkUtil.isUrlReachable(minioProperties.getEndpoint())) {
//            return null;
//        }

        return MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

}
