package com.qing.erp.member.service;

// Java类
import java.util.List;
// Spring类
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.entity.MapUserRoleEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
public interface IMapUserRoleService {
    // 增
    MapUserRoleEntity add(MapUserRoleEntity entity);
    // List<MapUserRoleEntity> addList(List<MapUserRoleEntity> entity);

    // 删
    void remove(Integer id);
    void removeList(Integer[] ids);

    // 改
    MapUserRoleEntity update(MapUserRoleEntity entity);
    // List<MapUserRoleEntity> updateList(List<MapUserRoleEntity> entity);

    // 查
    MapUserRoleEntity queryOne(Integer id);
    Page<MapUserRoleEntity> queryPage(Integer page, Integer show);
}
