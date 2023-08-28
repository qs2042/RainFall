package com.qing.erp.system.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;



import java.util.Date;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Mon Jul 17 13:50:38 CST 2023
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "advertisements_clicks")
public class AdvertisementsClicksEntity {
    private static final long serialVersionUID = 1L;

    /**
    * 广告点击唯一标识符
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    /**
    * 广告唯一标识符
    */

    private Integer advertisementId;

    /**
    * 用户唯一标识符
    */

    private Integer userId;

    /**
    * 广告点击时间
    */

    private Date clickTime;

    /**
    * 用户 IP 地址
    */

    private String ipAddress;

}