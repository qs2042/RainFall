package com.qing.erp.common.module.mysql.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DbType {
    VARCHAR("VARCHAR", "String"),
    TEXT("TEXT", "String"),
    JSON("JSON", "String"),

    INT("INT", "Integer"),
    SMALLINT("SMALLINT", "Integer"),
    BIGINT("BIGINT", "Long"),
    DOUBLE("DOUBLE", "Double"),
    FLOAT("FLOAT", "Float"),


    TINYINT("TINYINT", "Boolean"),
    BOOLEAN("BOOLEAN", "Boolean"),
    BIT("BIT", "Boolean"),

    DECIMAL("DECIMAL", "java.math.BigDecimal", true),

    // TODO: 如果数据库表格有同时设计DATE和DATETIME, 那么sql和util的DATE会同时存在
    TIMESTAMP("TIMESTAMP", "java.sql.Timestamp", true),
    DATE("DATE", "java.sql.Date", true),
    TIME("TIME", "java.sql.Time", true),
    DATETIME("DATETIME", "java.util.Date", true);

    private String dbType;
    private String javaType;
    private boolean isLibrary;

    DbType(String dbType, String javaType) {
        this(dbType, javaType, false);
    }

    public static String getJavaType(String dbType) {
        for (DbType type : values()) {
            if (type.dbType.equalsIgnoreCase(dbType)) {
                if (type.isLibrary) {
                    String[] parts = type.javaType.split("\\.");
                    return parts[parts.length-1];
                }
                return type.javaType;
            }
        }
        return "Object";
    }

    public static String getJavaType(String dbType, List<String> imports) {
        for (DbType type : values()) {
            if (type.dbType.equalsIgnoreCase(dbType)) {
                if (type.isLibrary) {
                    imports.add("import " + type.javaType + ";");
                    String[] parts = type.javaType.split("\\.");
                    return parts[parts.length-1];
                }
                return type.javaType;
            }
        }
        return "Object";
    }
}
