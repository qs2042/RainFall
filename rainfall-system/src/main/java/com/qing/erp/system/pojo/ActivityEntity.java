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
* @date Sun Jul 16 21:01:14 CST 2023
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "activity")
public class ActivityEntity {
    private static final long serialVersionUID = 1L;

    /**
    * 活动ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
    * 活动名称
    */
    private String name;

    /**
    * 活动描述
    */
    private String description;

    /**
    * 活动开始时间
    */
    private Date startTime;

    /**
    * 活动结束时间
    */
    private Date endTime;

    /**
    * 活动地点
    */
    private String location;

    /**
    * 组织者ID
    */
    private Integer organizerId;

    /**
    * 最大参与人数
    */
    private Integer maxAttendees;

    /**
    * 创建时间
    */
    private Date createdAt;

    /**
    * 更新时间
    */
    private Date updatedAt;

}