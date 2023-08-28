package com.qing.erp.oss.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkEntity {
    private String bucketName;
    private String objectName;

    private LocalDateTime createdAt;
    private Integer expires;

    private InputStream object;
    private String url;
    private String uuid;
}
