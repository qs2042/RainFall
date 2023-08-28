package com.qing.erp.module.threadPool;

import com.qing.erp.module.threadPool.properties.ThreadPoolProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// @EnableConfigurationProperties(ThreadPoolProperties.class)
// 不用这种方法是因为可能其他地方需要引入properties
@ComponentScan("com.qing.erp.module.threadPool.properties")
@Configuration
@Slf4j
public class ThreadPoolConfiguration {
    @PostConstruct
    public void init() {
        log.info("正在启动: 线程池");
    }

    @Autowired
    ThreadPoolProperties tpp;

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(
                tpp.getCoreSize(),
                tpp.getMaxSize(),
                tpp.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(100000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
