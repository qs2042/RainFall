package com.qing.erp.system.service;

import com.qing.erp.common.data.R;
import com.qing.erp.system.pojo.AdvertisementsClicksEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Mon Jul 17 13:50:38 CST 2023
*/
public interface IAdvertisementsClicksService {
    // 增
    R add(AdvertisementsClicksEntity entity);

    // 删
    R remove(Integer id);
    R removeList(Integer[] ids);

    // 改
    R update(AdvertisementsClicksEntity entity);

    // 查
    R queryPage(Integer page, Integer show);
}
