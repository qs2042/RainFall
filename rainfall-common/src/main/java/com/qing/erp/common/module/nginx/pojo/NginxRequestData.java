package com.qing.erp.common.module.nginx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NginxRequestData {
    private String method;
    private String uri;
    private String protocol;
}
