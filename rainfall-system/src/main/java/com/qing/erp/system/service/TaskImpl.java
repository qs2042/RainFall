package com.qing.erp.system.service;

import com.qing.erp.common.data.R;
import com.qing.erp.system.dao.TaskDao;
import com.qing.erp.system.pojo.TaskEntity;
import com.qing.erp.system.task.TaskHolder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * 这是自动生成的类
 *
 * @author halfRain
 * @email 2042136767@qq.com
 * @date Tue Jul 11 19:53:08 CST 2023
 */
@Service
@Slf4j
public class TaskImpl implements ITaskService {
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private TaskDao dao;

    @Override
    public R add(TaskEntity entity) {
        entity.setId(null);
        val task = dao.save(entity);

        // 如果保存成功就加入定时任务
        if (task.getId() != null) {
            scheduleTask(entity);
        }

        return R.ok();
    }

    @Override
    public R remove(Integer id) {
        if (id == null) {
            return R.error();
        }
        val task = dao.findById(id);
        // 不为空
        if (task.isPresent()) {
            // 从数据库中删除
            dao.deleteById(id);

            // 从任务调度器中删除
            unScheduleTask(task.get());
        }

        return R.ok();
    }
    @Override
    public R removeList(Integer[] ids) {
        if (ids == null) {
            return R.error();
        }

        // 从任务调度器中删除
        val tasks = dao.findAllById(Arrays.asList(ids));
        tasks.forEach(this::unScheduleTask);

        // 从数据库中删除
        dao.deleteAllById(Arrays.asList(ids));
        return R.ok();
    }

    @Override
    public R update(TaskEntity entity) {
        if (entity.getId() == null) {
            return R.error();
        }
        entity.setUpdatedAt(new Date());
        val task = dao.save(entity);

        // 从任务调度器中删除
        unScheduleTask(task);

        // 添加进任务调度器中
        scheduleTask(task);
        return R.ok();
    }

    @Override
    public R queryPage(Integer page, Integer show) {
        if (page == null || show == null) {
            return R.error();
        }
        val r = dao.findAll(PageRequest.of(page, show));
        return R.ok()
                .dataAdd("executedTasks", TaskHolder.executedTasks)
                .dataAdd("0", dao.findByEnabled(false))
                .dataAdd("1", dao.findByEnabled(true))
                .dataAdd("page", r);
    }


    // 将一个任务添加到任务调度器中,并根据任务定义的 Cron 表达式进行调度.
    @SneakyThrows
    public void scheduleTask(TaskEntity task) {
        // 如果任务状态为启用
        if (task.getEnabled()) {
            // 通过 Spring CronTrigger类, 根据 Cron 表达式来创建一个触发器
            ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(task, new CronTrigger(task.getCronExpression()));

            // 将任务的 ID 添加到 Map 中
            TaskHolder.executedTasks.put(task.getId(), scheduledFuture);
            log.info(String.format("正在添加任务[%d], 结果为: %s", task.getId(), scheduledFuture.toString()));
        }
    }

    // 从任务调度器中删除一个任务
    public void unScheduleTask(TaskEntity task) {
        val schedule = TaskHolder.executedTasks.get(task.getId());
        if (schedule != null) {
            // 从调度器的任务队列中查找并删除指定的任务对象
            schedule.cancel(false);

            // 删除
            TaskHolder.executedTasks.remove(task.getId());

            log.info(String.format("正在删除任务[%d], 结果为: %s", task.getId(), schedule.isCancelled()));
        }
    }
}