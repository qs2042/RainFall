package com.qing.erp.system.service;

import com.qing.erp.common.data.R;
import com.qing.erp.system.pojo.ParamsEntity;

/**
 * 这是自动生成的类
 *
 * @author halfRain
 * @email 2042136767@qq.com
 * @date Mon Jul 03 16:38:11 CST 2023
 */
public interface IParamsService {
    // 增
    R add(ParamsEntity entity);

    // 删
    R remove(Integer id);

    // 改
    R update(ParamsEntity entity);

    // 查
    R queryPage(Integer page, Integer show);
}
