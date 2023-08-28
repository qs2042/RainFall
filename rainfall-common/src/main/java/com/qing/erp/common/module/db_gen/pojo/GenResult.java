package com.qing.erp.common.module.db_gen.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenResult {
    private String fileName;
    private String fileType;
    private String language;
    private String code;
}
