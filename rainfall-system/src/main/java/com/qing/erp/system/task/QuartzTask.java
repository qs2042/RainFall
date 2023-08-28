package com.qing.erp.system.task;

import com.qing.erp.system.dao.TaskDao;
import com.qing.erp.system.pojo.TaskEntity;
import com.qing.erp.system.service.TaskImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;


@Component
public class QuartzTask {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private TaskDao dao;

    @Autowired
    private TaskImpl impl;


    //    @Scheduled(fixedRate = 5000)
    public void printMessage() {
        System.out.println("5秒执行一次: " + new Date());
    }

    // 每天凌晨3点执行一次
//    @Scheduled(cron = "0 0 3 * * ?")
    public void test1() {
        System.out.println("每天凌晨3点执行一次: " + new Date());
    }

    // 每小时执行一次
//    @Scheduled(cron = "0 0 * ? * *")
    public void test2() {
        System.out.println("每小时执行一次: " + new Date());
    }

    // 每周日凌晨一点执行一次
//    @Scheduled(cron = "0 0 1 ? * SUN")
    public void test3() {
        System.out.println("每周日凌晨一点执行一次: " + new Date());
    }

    // 每月1日凌晨四点执行一次
//    @Scheduled(cron = "0 4 1 ? * *")
    public void test4() {
        System.out.println("每月1日凌晨四点执行一次: " + new Date());
    }

    // 每天凌晨2点和下午2点
//    @Scheduled(cron = "0 0 2,14 ? * *")
    public void test5() {
        System.out.println("每天凌晨2点和下午2点: " + new Date());
    }

    // 每5分钟
    // */10 * * * * *   每10秒执行一次
//    @Scheduled(cron = "0 0/5 * ? * *")
    public void test6() {
        System.out.println("每5分钟: " + new Date());
    }

    // 每工作日(星期一到五)的下午五点
//    @Scheduled(cron = "0 0 17 ? * MON_FRI")
    public void test7() {
        System.out.println("每工作日(星期一到五)的下午五点: " + new Date());
    }

    // 每季节的第一个月的第一天凌晨3点
//    @Scheduled(cron = "0 3 1 3,6,9,12 ? *")
    public void test8() {
        System.out.println("每季节的第一个月的第一天凌晨3点: " + new Date());
    }



//    @Scheduled(fixedDelay = 10000)
    @PostConstruct
    public void checkForNewTasks() {
        // 从数据库中查询所有启用的任务
        List<TaskEntity> tasks = dao.findByEnabled(true);

        for (TaskEntity task : tasks) {
            // 如果任务的 ID 不在 Map 中,说明这个任务还没有执行过
            if (!TaskHolder.executedTasks.containsKey(task.getId())) {
                // 将任务添加到调度器中
                impl.scheduleTask(task);
            }
        }
    }



}
