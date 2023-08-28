package com.qing.erp.system.service;

import com.qing.erp.common.data.R;
import com.qing.erp.system.pojo.ActivityEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Sun Jul 16 21:01:14 CST 2023
*/
public interface IActivityService {
    // 增
    R add(ActivityEntity entity);

    // 删
    R remove(Integer id);
    R removeList(Integer[] ids);

    // 改
    R update(ActivityEntity entity);

    // 查
    R queryPage(Integer page, Integer show);
}
