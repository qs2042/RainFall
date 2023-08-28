package com.qing.erp.common.module.mysql;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLClientBuilder {
    private String host;
    private int port = 3306;
    private String database;
    private String username;
    private String password;

    public MySQLClientBuilder host(String host) {
        this.host = host;
        return this;
    }

    public MySQLClientBuilder port(int port) {
        this.port = port;
        return this;
    }

    public MySQLClientBuilder database(String database) {
        this.database = database;
        return this;
    }

    public MySQLClientBuilder username(String username) {
        this.username = username;
        return this;
    }

    public MySQLClientBuilder password(String password) {
        this.password = password;
        return this;
    }

    @SneakyThrows
    public MySQLClient build() {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        Connection connection = DriverManager.getConnection(url, username, password);
        return new MySQLClient(connection);
    }
}
