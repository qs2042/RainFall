package com.qing.erp.member.entity;

// lombok
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
// jpa
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
// 动态类
import java.util.Date;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class RoleEntity {
    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    private Integer id;

    /**
    * 代码(唯一)
    */
    @Column(name = "`code`")
    private String code;

    /**
    * 名称
    */
    @Column(name = "`name`")
    private String name;

    /**
    * 标签
    */
    @Column(name = "`label`")
    private String label;

    /**
    * 介绍
    */
    @Column(name = "`introduce`")
    private String introduce;

    /**
    * 逻辑删除
    */
    @Column(name = "`flag`")
    private Boolean flag;

    /**
    * 创建时间
    */
    @Column(name = "`created_at`")
    private Date createdAt;

    /**
    * 修改时间
    */
    @Column(name = "`updated_at`")
    private Date updatedAt;

}