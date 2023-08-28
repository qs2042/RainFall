package com.qing.erp.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// TODO
//    如果是后台请求, 将 /api/member/xx 请求拦截，然后将请求替换成/member/xx
//    如果是前台请求, 按照host拦截，不按照url拦截，例如 [order.rainfall.com, member.rainfall.com]
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
