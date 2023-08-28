package com.qing.erp.member.service;

// Java类
import java.util.List;
// 动态类
import java.util.Date;
// Spring类
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.entity.UserLevelEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
public interface IUserLevelService {
    // 增
    UserLevelEntity add(UserLevelEntity entity);
    // List<UserLevelEntity> addList(List<UserLevelEntity> entity);

    // 删
    void remove(Integer id);
    void removeList(Integer[] ids);

    // 改
    UserLevelEntity update(UserLevelEntity entity);
    // List<UserLevelEntity> updateList(List<UserLevelEntity> entity);

    // 查
    UserLevelEntity queryOne(Integer id);
    Page<UserLevelEntity> queryPage(Integer page, Integer show);

    UserLevelEntity findByUserId(Integer id);
}
