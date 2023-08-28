package com.qing.erp.oss.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BucketEntity {
    private String name;

    private LocalDateTime createdAt;

    // 过期时间

    // 访问权限
    private String asc;
}
