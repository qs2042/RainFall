package com.qing.erp.common.module.mysql.pojo;

import com.qing.erp.common.module.mysql.constant.DbType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnMetaData {
    private String name;
    private String type;
    private String remarks;
    private int size;
    private boolean isNullable;

    private String nameCamel;
    private String nameCamelCase;
    private String javaType;

    public ColumnMetaData(String name, String type, String remarks, int size, boolean isNullable) {
        this.name = name;
        this.type = type;
        this.remarks = remarks;
        this.size = size;
        this.isNullable = isNullable;
    }
}
