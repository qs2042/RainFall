package com.qing.erp.system.service;

import com.qing.erp.common.data.R;
import com.qing.erp.system.pojo.LogsEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Wed Jul 05 19:11:13 CST 2023
*/
public interface ILogsService {
    // 增
    R add(LogsEntity entity);

    // 删
    R remove(Integer id);
    R removeList(Integer[] ids);

    // 改
    R update(LogsEntity entity);

    // 查
    R queryPage(Integer page, Integer show);
}
