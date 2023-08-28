package com.qing.erp.member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Wed Jul 19 20:33:06 CST 2023
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    private Integer id;

    /**
    * UUID
    */
    private String uuid;

    /**
    * 账号
    */
    private String account;

    /**
    * 密码
    */
    private String password;

    /**
    * 逻辑删除
    */
    private Boolean flag;

    /**
    * 备注
    */
    private String notes;

    private UserInfoEntity info;
    private UserSecurityEntity security;
    private List<UserInventoryEntity> inventory;
    private List<UserInventorUniqueEntity> inventorUnique;
    private UserWalletEntity wallet;
    private UserLevelEntity level;

    private String token;
}