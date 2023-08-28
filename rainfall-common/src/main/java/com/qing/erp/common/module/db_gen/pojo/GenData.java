package com.qing.erp.common.module.db_gen.pojo;

import com.qing.erp.common.str.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenData {
    // 包路径
    private String packagePath;
    // 介绍
    private String comments;
    // 作者
    private String author;
    // 邮箱
    private String email;
    // 创建时间
    private String datetime;

    // 表格名称(下划线, 小驼峰, 大驼峰)
    private String tableName;
    private String tableNameCamel;
    private String tableNameCamelCase;

    // 数据库名称
    private String databaseName;
    // 网关名称
    private String gatewayPath;

    // 数据库字段
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Field {
        private String name;
        private String type;
        private String remarks;

        private String nameCamel;
        private String nameCamelCase;
        private String javaType;
    }
    private List<Field> fields;
    // 可被编辑字段
    private String editFields;

    // 需要导入的包
    private List<String> importList = new ArrayList<>();

    public void setFields(List<Field> fields) {
        this.fields = fields;
        editFields = fields.stream()
                .filter(v -> "String".equals(v.getType()) || "Integer".equals(v.getType()))
                .map(v -> "'" + v + "'")
                .collect(Collectors.joining(","));
    }

    public static GenData build (String packagePath, String databaseName, String tableName) {
        val gd = new GenData();

        gd.packagePath = packagePath;
        gd.comments = "自动生成的类";
        gd.author = "R";
        gd.email = "2042136767@qq.com";
        gd.datetime = new Date().toString();

        gd.databaseName = databaseName;
        gd.tableName = tableName;
        gd.tableNameCamel = StrUtil.underscoreToCamel(tableName);
        gd.tableNameCamelCase = StrUtil.underscoreToCamelCase(tableName);
        return gd;
    }

    public static GenData build (String packagePath, String comments, String author, String email, String databaseName, String tableName) {
        val gd = new GenData();
        gd.packagePath = packagePath;
        gd.comments = comments;
        gd.author = author;
        gd.email = email;
        gd.datetime = new Date().toString();
        gd.databaseName = databaseName;
        gd.tableName = tableName;
        gd.tableNameCamel = StrUtil.underscoreToCamel(tableName);
        gd.tableNameCamelCase = StrUtil.underscoreToCamelCase(tableName);
        return gd;
    }
}
