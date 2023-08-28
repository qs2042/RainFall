package com.qing.erp.common.module.mysql.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableMetaData {
    private String name;
    private String type;
    private String schem;
    private String cat;
    private String selfReferencingColName;
    private String remarks;
    private String refGeneration;
    private List<ColumnMetaData> columns;
}
