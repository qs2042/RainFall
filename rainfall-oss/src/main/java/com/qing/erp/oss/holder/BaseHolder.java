package com.qing.erp.oss.holder;

import com.qing.erp.oss.entity.LinkEntity;

import java.util.concurrent.CopyOnWriteArrayList;

public class BaseHolder {
    // TODO: 需要加个定时任务, 看看有没有过期的图片
    public static final CopyOnWriteArrayList<LinkEntity> links = new CopyOnWriteArrayList<>();
}
