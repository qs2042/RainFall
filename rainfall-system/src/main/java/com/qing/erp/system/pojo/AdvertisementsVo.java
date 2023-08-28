package com.qing.erp.system.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementsVo {
    private static final long serialVersionUID = 1L;

    /**
     * 广告唯一标识符
     */
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

    // 点击次数
    private Integer clicks;
}