package com.qing.erp.member.service;

// Java类
import java.util.List;
// Spring类
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.entity.MapRolePermissionEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
public interface IMapRolePermissionService {
    // 增
    MapRolePermissionEntity add(MapRolePermissionEntity entity);
    // List<MapRolePermissionEntity> addList(List<MapRolePermissionEntity> entity);

    // 删
    void remove(Integer id);
    void removeList(Integer[] ids);

    // 改
    MapRolePermissionEntity update(MapRolePermissionEntity entity);
    // List<MapRolePermissionEntity> updateList(List<MapRolePermissionEntity> entity);

    // 查
    MapRolePermissionEntity queryOne(Integer id);
    Page<MapRolePermissionEntity> queryPage(Integer page, Integer show);
}
