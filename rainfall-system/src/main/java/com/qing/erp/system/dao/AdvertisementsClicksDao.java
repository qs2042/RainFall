package com.qing.erp.system.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.qing.erp.system.pojo.AdvertisementsClicksEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Mon Jul 17 13:50:38 CST 2023
*/
public interface AdvertisementsClicksDao extends JpaRepository<AdvertisementsClicksEntity, Integer>, Serializable {
    List<AdvertisementsClicksEntity> findAllByAdvertisementId(Integer advertisementId);
}
