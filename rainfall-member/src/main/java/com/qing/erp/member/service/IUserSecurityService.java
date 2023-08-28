package com.qing.erp.member.service;

// Java类
import java.util.List;
// 动态类
import java.util.Date;
// Spring类
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.entity.UserSecurityEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
public interface IUserSecurityService {
    // 增
    UserSecurityEntity add(UserSecurityEntity entity);
    // List<UserSecurityEntity> addList(List<UserSecurityEntity> entity);

    // 删
    void remove(Integer id);
    void removeList(Integer[] ids);

    // 改
    UserSecurityEntity update(UserSecurityEntity entity);
    // List<UserSecurityEntity> updateList(List<UserSecurityEntity> entity);

    // 查
    UserSecurityEntity queryOne(Integer id);
    Page<UserSecurityEntity> queryPage(Integer page, Integer show);

    UserSecurityEntity findByUserId(Integer id);
}
