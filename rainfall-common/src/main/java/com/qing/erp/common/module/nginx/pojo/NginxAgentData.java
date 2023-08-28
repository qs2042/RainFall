package com.qing.erp.common.module.nginx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NginxAgentData {
    private String osName;
    private String osVersion;
    private String browserName;
    private String browserVersion;
}
