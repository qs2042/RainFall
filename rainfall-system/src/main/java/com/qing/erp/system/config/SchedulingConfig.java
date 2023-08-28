package com.qing.erp.system.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/*
            1        2          3         4        5        6
Quartz cron 秒(0-59) 分钟(0-59) 小时(0-23) 日(1-31) 月(1-12) 星期(1-7, 1为星期天, 2为星期一)
cron                 分钟(0-59) 小时(0-23) 日(1-31) 月(1-12) 星期(0-7, 0和7都表示周日)

如果想要将cron表达式 转为 Quartz cron 表达式,第一项秒钟字段添加为 0,以使其成为 6 个字段的表达式

type        cron           Quartz cron
星期        使用数字        数字 + 缩写(SUN=星期天, MON=星期一)
*/

@Slf4j
@Configuration
@EnableScheduling
public class SchedulingConfig {

//    @Autowired
//    private DataSource dataSource;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

        // 配置数据源
        // DataSource dataSource = ...;
        // schedulerFactoryBean.setDataSource(dataSource);

        // 配置 Quartz 属性
        // Properties quartzProperties = new Properties();
        // quartzProperties.setProperty("org.quartz.scheduler.instanceName", "myScheduler");
        // quartzProperties.setProperty("org.quartz.jobStore.useProperties", "false");
        // schedulerFactoryBean.setQuartzProperties(quartzProperties);

        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler scheduler() {
        SchedulerFactoryBean schedulerFactoryBean = schedulerFactoryBean();
        return schedulerFactoryBean.getScheduler();
    }

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(14);
        return scheduler;
    }

}
