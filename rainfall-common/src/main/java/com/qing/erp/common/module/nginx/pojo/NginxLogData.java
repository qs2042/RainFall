package com.qing.erp.common.module.nginx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NginxLogData {
    private String ip;
    private String user;
    private LocalDateTime date;
    private NginxRequestData request;
    private String status;//int
    private String bytes;//long
    private String referer;
    private NginxAgentData agent;

}