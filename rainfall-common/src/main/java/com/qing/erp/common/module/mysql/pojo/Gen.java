package com.qing.erp.common.module.mysql.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Gen {
    private String name;
    private String type;
    private String language;
    private String code;
}
