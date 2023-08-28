package com.qing.erp.system.task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

// TODO: 定时任务之后需要单独分离成一个微服务, 并且这个微服务还不能多集群
//   或者数据库中加一个是否正在运行的字段
//   是否开启 √ 是否运行中 √ 不运行
//   是否开启 √ 是否运行中 × 运行

public class TaskHolder {
    public static final Map<Integer, ScheduledFuture<?>> executedTasks = new ConcurrentHashMap<>();
}
