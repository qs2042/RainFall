package com.qing.erp.system.service;

import com.qing.erp.common.data.R;
import com.qing.erp.system.pojo.MenuEntity;

import java.util.List;

public interface IMenuService {
    // 增
    R add(MenuEntity entity);

    // 删
    R remove(Integer id);
    R removeList(Integer[] ids);

    // 改
    R update(MenuEntity entity);

    // 查
    R queryPage(Integer page, Integer show);
    R queryTree(Boolean flag);
}
