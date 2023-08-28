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
* @date Wed Jul 05 19:11:13 CST 2023
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "logs")
public class LogsEntity {
    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
    * 用户ID
    */
    private Integer userId;

    /**
    * 请求地址
    */
    private String reqUri;

    /**
    * 请求方式
    */
    private String reqMethod;

    /**
    * 请求参数
    */
    private String reqParameter;

    /**
    * IP
    */
    private String ip;

    /**
    * 城市
    */
    private String city;

    /**
    * 设备机型
    */
    private String equipment;

    /**
    * 模块
    */
    private String module;

    /**
    * 备注
    */
    private String notes;

    /**
    * 操作方法的链路
    */
    private String method;

    /**
    * 执行速度
    */
    private Integer spendTime;

    /**
    * 返回结果
    */
    private String result;

    /**
    * 是否成功
    */
    private Boolean status;

    /**
    * 创建时间
    */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;
}