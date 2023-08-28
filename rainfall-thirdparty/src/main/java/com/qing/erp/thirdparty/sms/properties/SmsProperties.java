package com.qing.erp.thirdparty.sms.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "qing.sms.alicloud")
@Component
public class SmsProperties {
    private String host;
    private String path;
    private String appcode;
    private String sign;
    private String skin;
}
