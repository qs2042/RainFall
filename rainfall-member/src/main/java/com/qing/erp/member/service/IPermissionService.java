package com.qing.erp.member.service;

// Java类
import java.util.List;
// 动态类
import java.util.Date;
// Spring类
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.entity.PermissionEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
public interface IPermissionService {
    // 增
    PermissionEntity add(PermissionEntity entity);
    // List<PermissionEntity> addList(List<PermissionEntity> entity);

    // 删
    void remove(Integer id);
    void removeList(Integer[] ids);

    // 改
    PermissionEntity update(PermissionEntity entity);
    // List<PermissionEntity> updateList(List<PermissionEntity> entity);

    // 查
    PermissionEntity queryOne(Integer id);
    Page<PermissionEntity> queryPage(Integer page, Integer show);
}
