package com.qing.erp.search;

import com.qing.erp.module.base.EnableBase;
import com.qing.erp.module.monitor.EnableMonitor;
import com.qing.erp.module.threadPool.EnableThreadPool;
import com.qing.erp.module.timeFormatConversion.EnableTimeFormatConversion;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient       // 开启服务注册与发现功能
@EnableFeignClients          // 开启feign客户端远程调用功能 (basePackages="com.qing.erp.search.feign")
@EnableScheduling            // 开启定时任务
@EnableCaching               // 开启缓存功能
@EnableTransactionManagement // 开启事务管理
//@EnableRedisHttpSession    // 开启分布式Session(基于Redis)
@EnableRabbit                // 开启Rabbit
@ServletComponentScan        // 扫描Servlet注解
@EnableBase                  // 开启基础模块
@EnableMonitor               // 开启后台监控
@EnableTimeFormatConversion  // 开启时间转换
@EnableThreadPool            // 开启线程池
public class SearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }

}
