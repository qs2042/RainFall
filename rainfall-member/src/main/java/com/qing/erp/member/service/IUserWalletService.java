package com.qing.erp.member.service;

// Java类
import java.util.List;
// 动态类
import java.math.BigDecimal;
import java.util.Date;
// Spring类
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.entity.UserWalletEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
public interface IUserWalletService {
    // 增
    UserWalletEntity add(UserWalletEntity entity);
    // List<UserWalletEntity> addList(List<UserWalletEntity> entity);

    // 删
    void remove(Integer id);
    void removeList(Integer[] ids);

    // 改
    UserWalletEntity update(UserWalletEntity entity);
    // List<UserWalletEntity> updateList(List<UserWalletEntity> entity);

    // 查
    UserWalletEntity queryOne(Integer id);
    Page<UserWalletEntity> queryPage(Integer page, Integer show);

    UserWalletEntity findByUserId(Integer id);
}
