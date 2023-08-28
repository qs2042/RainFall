package com.qing.erp.system.service;

//import java.sql.Date;
//import java.util.Date;
//import java.sql.Timestamp;
import com.qing.erp.common.data.R;
import com.qing.erp.system.pojo.AdvertisementsEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Jul 13 19:39:05 CST 2023
*/
public interface IAdvertisementsService {
    // 增
    R add(AdvertisementsEntity entity);

    // 删
    R remove(Integer id);
    R removeList(Integer[] ids);

    // 改
    R update(AdvertisementsEntity entity);

    // 查
    R queryPage(Integer page, Integer show);
}
