package com.qing.erp.common.module.mysql;

import com.qing.erp.common.module.mysql.pojo.ColumnMetaData;
import com.qing.erp.common.module.mysql.pojo.TableMetaData;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

public interface IClient {

    void close();

    // 获取数据库元信息
    HashMap<String, Object>  getDatabaseMetaDataMap();

    // 获取所有数据库
    List<String> getDatabases();

    // 获取指定数据库的所有表格名称
    List<String> getTableNames(String databaseName);

    // 获取指定数据库的所有表格信息
    List<TableMetaData> getTableMetaData(String databaseName);

    // 获取指定表格的所有列信息
    List<ColumnMetaData> getColumnMetaData(String databaseName, String tableName);

    // 执行 SQL 语句
    void execute(String sql);

    // 执行 SQL 查询并返回结果集
    ResultSet executeQuery(String sql);

    // 执行 SQL 更新操作并返回受影响的行数
    int executeUpdate(String sql);
}
