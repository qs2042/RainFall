package com.qing.erp.system;

import com.qing.erp.module.base.EnableBase;
import com.qing.erp.module.monitor.EnableMonitor;
import com.qing.erp.module.threadPool.EnableThreadPool;
import com.qing.erp.module.timeFormatConversion.EnableTimeFormatConversion;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// 待用 TODO: 开启日志切面(基于ELK实现
/*
@EnableWebSecurity         // 开启安全功能
@EnableAsync               // 开启异步方法的支持
@EnableAspectJAutoProxy    // 开启对 AspectJ 代理的支持
@EnableWebSocket           // 开启WebSocket支持。
@EnableJpaRepositories     // 开启JPA存储库支持。
@EnableBatchProcessing     // 开启批处理支持。
@EnableCircuitBreaker      // 开启断路器（Hystrix）支持。
@EnableKafka               // 开启Kafka消息队列支持。
@EnableSwagger2            // 开启Swagger API文档支持。
@EnableAsync               // 开启异步方法支持。
@EnableWebSecurity         // 开启Spring Security支持。
@EnableAspectJAutoProxy    // 开启AspectJ代理支持。
@EnableRedisRepositories   // 开启Redis存储库支持。
@EnableMongoRepositories   // 开启MongoDB存储库支持。
@EnableAutoConfiguration   //
@ImportResource(locations = { "classpath*: spring.xml" })
 */

@SpringBootApplication       // SpringBoot
@EnableDiscoveryClient       // 开启服务注册与发现功能
@EnableFeignClients          // 开启feign客户端远程调用功能 (basePackages="com.qing.erp.system.feign")
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
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

}
