package com.qing.erp.oss.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "minio")
@Component
@Data
public class MinioProperties {
    // 连接地址 && 域名
    private String endpoint;
    private String nginxHost;

    // 用户名 && 密码
    private String accessKey;
    private String secretKey;

    public static final String DEFAULT_BUCKET = "public";
}

