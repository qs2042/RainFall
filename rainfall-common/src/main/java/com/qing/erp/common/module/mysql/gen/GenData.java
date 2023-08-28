package com.qing.erp.common.module.mysql.gen;

import com.qing.erp.common.str.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class GenData {
    private String packagePath;
    private List<String> importList;
    private String comments;

    private String author;
    private String email;

    private String datetime;

    private String tableName;
    //
    private String className;
    private String camelCase;

    // 默认是将数据库名称作为网关的前缀
    private String databaseName;

    public GenData(String packagePath, String comments, String author, String email, String databaseName, String tableName) {
        this.packagePath = packagePath;
        this.importList = new ArrayList<>();
        this.comments = comments;
        this.author = author;
        this.email = email;
        this.datetime = new Date().toString();
        this.databaseName = databaseName;
        this.tableName = tableName;
        this.className = StrUtil.underscoreToCamel(tableName);
        this.camelCase = StrUtil.underscoreToCamelCase(tableName);
    }
}
