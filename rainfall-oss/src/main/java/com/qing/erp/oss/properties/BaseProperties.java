package com.qing.erp.oss.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "oss")
@Component
@Data
public class BaseProperties {
    // 用户名 && 密码
    private String accessKey;
    private String secretKey;

    public static final String DEFAULT_BUCKET = "public";
}

