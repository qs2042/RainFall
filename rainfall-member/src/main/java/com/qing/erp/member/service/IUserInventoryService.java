package com.qing.erp.member.service;

// Java类
import java.util.List;
// 动态类
import java.util.Date;
// Spring类
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.entity.UserInventoryEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
public interface IUserInventoryService {
    // 增
    UserInventoryEntity add(UserInventoryEntity entity);
    // List<UserInventoryEntity> addList(List<UserInventoryEntity> entity);

    // 删
    void remove(Integer id);
    void removeList(Integer[] ids);

    // 改
    UserInventoryEntity update(UserInventoryEntity entity);
    // List<UserInventoryEntity> updateList(List<UserInventoryEntity> entity);

    // 查
    UserInventoryEntity queryOne(Integer id);
    Page<UserInventoryEntity> queryPage(Integer page, Integer show);

    List<UserInventoryEntity> findAllByUserId(Integer id);
}
