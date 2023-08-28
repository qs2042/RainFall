package com.qing.erp.system.controller;

import com.qing.erp.common.data.R;
import com.qing.erp.system.pojo.TaskEntity;
import com.qing.erp.system.service.TaskImpl;
import lombok.SneakyThrows;
import lombok.val;
import org.quartz.CronTrigger;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 这是自动生成的类
 *
 * @author halfRain
 * @email 2042136767@qq.com
 * @date Tue Jul 11 19:53:08 CST 2023
 */
@RequestMapping("task")
@RestController
public class TaskController {
    @Autowired
    private TaskImpl impl;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @PostMapping("/add")
    public R add(TaskEntity entity) {
        return impl.add(entity);
    }

    @PostMapping("/remove")
    public R remove(Integer id) {
        return impl.remove(id);
    }


    @PostMapping("/removeList")
    public R removeList(Integer[] ids) {
        return impl.removeList(ids);
    }


    @PostMapping("/update")
    public R update(TaskEntity entity) {
        return impl.update(entity);
    }

    @GetMapping("/queryPage")
    public R queryPage(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "show", required = false, defaultValue = "8") Integer show) {
        return impl.queryPage(page, show);
    }

    @SneakyThrows
    @GetMapping("getInfo")
    public R getAllScheduledJobs() {
        return R.ok()
                .dataAdd("PoolSize", taskScheduler.getPoolSize())
                .dataAdd("ActiveCount", taskScheduler.getActiveCount())
                .dataAdd("Clock", taskScheduler.getClock().toString())
                .dataAdd("ThreadGroup", taskScheduler.getThreadGroup())
                .dataAdd("ThreadNamePrefix", taskScheduler.getThreadNamePrefix())
                .dataAdd("ThreadPriority", taskScheduler.getThreadPriority())
                .dataAdd("QueueSize", taskScheduler.getScheduledThreadPoolExecutor().getQueue().size())
                .dataAdd("meta", scheduler.getMetaData());
    }
}