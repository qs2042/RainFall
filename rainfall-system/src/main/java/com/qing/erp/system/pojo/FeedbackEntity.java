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
* @date Sun Jul 16 19:02:28 CST 2023
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedback")
public class FeedbackEntity {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
    * 用户ID
    */
    private Integer userId;

    /**
    * 反馈文本
    */
    private String text;

    /**
    * 反馈提交日期
    */
    private Date date;

    /**
    * 反馈评分
    */
    private Integer rating;

    /**
    * 反馈分类
    */
    private Integer category;

    /**
    * 反馈处理状态
    */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createdAt;

}