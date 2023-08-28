package com.qing.erp.member.service;

// Java类
import java.util.List;
// 动态类
import java.util.Date;
// Spring类
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.entity.UserInfoEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
public interface IUserInfoService {
    // 增
    UserInfoEntity add(UserInfoEntity entity);
    // List<UserInfoEntity> addList(List<UserInfoEntity> entity);

    // 删
    void remove(Integer id);
    void removeList(Integer[] ids);

    // 改
    UserInfoEntity update(UserInfoEntity entity);
    // List<UserInfoEntity> updateList(List<UserInfoEntity> entity);

    // 查
    UserInfoEntity queryOne(Integer id);
    Page<UserInfoEntity> queryPage(Integer page, Integer show);

    UserInfoEntity findByUserId(Integer id);
}
