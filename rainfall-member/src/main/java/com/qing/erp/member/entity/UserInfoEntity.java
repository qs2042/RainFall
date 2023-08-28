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
@Table(name = "user_info")
public class UserInfoEntity {
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
    * 昵称
    */
    @Column(name = "`nick`")
    private String nick;

    /**
    * 头像
    */
    @Column(name = "`face`")
    private String face;

    /**
    * 年龄
    */
    @Column(name = "`age`")
    private Integer age;

    /**
    * 性别
    */
    @Column(name = "`sex`")
    private Integer sex;

    /**
    * 个人介绍
    */
    @Column(name = "`introduce`")
    private String introduce;

    /**
    * 注册IP
    */
    @Column(name = "`register_ip`")
    private String registerIp;

    /**
    * 注册时间
    */
    @Column(name = "`created_at`")
    private Date createdAt;

    /**
    * 更新时间
    */
    @Column(name = "`updated_at`")
    private Date updatedAt;

}