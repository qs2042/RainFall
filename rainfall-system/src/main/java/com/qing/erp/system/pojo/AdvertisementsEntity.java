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
 * @date Sun Jul 16 23:21:37 CST 2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "advertisements")
public class AdvertisementsEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 广告唯一标识符
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片 URL 地址
     */
    private String imageUrl;

    /**
     * 链接 URL 地址
     */
    private String linkUrl;

    /**
     * 展示开始日期
     */
    private Date startDate;

    /**
     * 展示结束日期
     */
    private Date endDate;

    /**
     * 广告创建时间
     */
    private Date createdAt;

    /**
     * 广告更新时间
     */
    private Date updatedAt;

}