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
@Table(name = "user_inventory")
public class UserInventoryEntity {
    private static final long serialVersionUID = 1L;

    /**
    * 唯一物品的唯一标识符
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
    * 物品数量
    */
    @Column(name = "`quantity`")
    private Integer quantity;

    /**
    * 获取时间
    */
    @Column(name = "`created_at`")
    private Date createdAt;

    /**
    * 修改时间
    */
    @Column(name = "`updated_at`")
    private Date updatedAt;

}