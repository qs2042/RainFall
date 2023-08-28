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
@Table(name = "user_inventor_unique")
public class UserInventorUniqueEntity {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    private Integer id;

    /**
    * 用户ID
    */
    @Column(name = "`user_id`")
    private Integer userId;

    /**
    * 物品ID
    */
    @Column(name = "`item_id`")
    private Integer itemId;

    /**
    * 自定义名称
    */
    @Column(name = "`name`")
    private String name;

    /**
    * 自定义描述
    */
    @Column(name = "`description`")
    private String description;

    /**
    * 自定义图片URL
    */
    @Column(name = "`image_url`")
    private String imageUrl;

    /**
    * Named Binary Tag
    */
    @Column(name = "`nbt`")
    private String nbt;

    /**
    * 创建时间
    */
    @Column(name = "`created_at`")
    private Date createdAt;

}