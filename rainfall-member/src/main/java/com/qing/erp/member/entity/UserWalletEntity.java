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
import java.math.BigDecimal;
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
@Table(name = "user_wallet")
public class UserWalletEntity {
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
    * 纸币
    */
    @Column(name = "`coins`")
    private BigDecimal coins;

    /**
    * 金币
    */
    @Column(name = "`gold`")
    private BigDecimal gold;

    /**
    * 钻石
    */
    @Column(name = "`diamonds`")
    private BigDecimal diamonds;

    /**
    * 自定义货币({id, balance})
    */
    @Column(name = "`custom_currency`")
    private String customCurrency;

    /**
    * 更新时间
    */
    @Column(name = "`updated_at`")
    private Date updatedAt;

}