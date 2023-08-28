package com.qing.erp.system.service;

import com.qing.erp.common.data.R;
import com.qing.erp.system.pojo.DictEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Mon Jul 03 16:00:00 CST 2023
*/
public interface IDictService {
    // 增
    R add(DictEntity entity);

    // 删
    R remove(Integer id);
    R removeList(Integer[] ids);

    // 改
    R update(DictEntity entity);

    // 查
    R queryPage(Integer page, Integer show);
}
