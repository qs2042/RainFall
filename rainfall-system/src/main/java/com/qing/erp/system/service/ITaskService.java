package com.qing.erp.system.service;

import com.qing.erp.common.data.R;
import com.qing.erp.system.pojo.TaskEntity;

/**
 * 这是自动生成的类
 *
 * @author halfRain
 * @email 2042136767@qq.com
 * @date Tue Jul 11 19:53:08 CST 2023
 */
public interface ITaskService {
    // 增
    R add(TaskEntity entity);

    // 删
    R remove(Integer id);
    R removeList(Integer[] ids);

    // 改
    R update(TaskEntity entity);

    // 查
    R queryPage(Integer page, Integer show);
}
