package com.qing.erp.system.service;

import com.qing.erp.common.data.R;
import com.qing.erp.system.pojo.DevLogsEntity;

/**
 * 这是自动生成的类
 *
 * @author halfRain
 * @email 2042136767@qq.com
 * @date Thu Jul 06 20:31:48 CST 2023
 */
public interface IDevLogsService {
    // 增
    R add(DevLogsEntity entity);

    // 删
    R remove(Integer id);
    R removeList(Integer[] ids);

    // 改
    R update(DevLogsEntity entity);

    // 查
    R queryPage(Integer page, Integer show);
}
