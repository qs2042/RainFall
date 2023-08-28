package com.qing.erp.common.module.mysql;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLClientFactory {
    @SneakyThrows
    public static MySQLClient build(String host, int port, String database, String username, String password) {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        Connection connection = DriverManager.getConnection(url, username, password);
        return new MySQLClient(connection);
    }
}
