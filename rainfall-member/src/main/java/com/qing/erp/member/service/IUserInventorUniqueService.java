package com.qing.erp.member.service;

// Java类
import java.util.List;
// 动态类
import java.util.Date;
// Spring类
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.entity.UserInventorUniqueEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
public interface IUserInventorUniqueService {
    // 增
    UserInventorUniqueEntity add(UserInventorUniqueEntity entity);
    // List<UserInventorUniqueEntity> addList(List<UserInventorUniqueEntity> entity);

    // 删
    void remove(Integer id);
    void removeList(Integer[] ids);

    // 改
    UserInventorUniqueEntity update(UserInventorUniqueEntity entity);
    // List<UserInventorUniqueEntity> updateList(List<UserInventorUniqueEntity> entity);

    // 查
    UserInventorUniqueEntity queryOne(Integer id);
    Page<UserInventorUniqueEntity> queryPage(Integer page, Integer show);

    List<UserInventorUniqueEntity> findAllByUserId(Integer id);
}
