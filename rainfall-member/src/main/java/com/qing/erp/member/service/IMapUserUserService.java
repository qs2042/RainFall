package com.qing.erp.member.service;

// Java类
import java.util.List;
// 动态类
import java.util.Date;
// Spring类
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.entity.MapUserUserEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
public interface IMapUserUserService {
    // 增
    MapUserUserEntity add(MapUserUserEntity entity);
    // List<MapUserUserEntity> addList(List<MapUserUserEntity> entity);

    // 删
    void remove(Integer id);
    void removeList(Integer[] ids);

    // 改
    MapUserUserEntity update(MapUserUserEntity entity);
    // List<MapUserUserEntity> updateList(List<MapUserUserEntity> entity);

    // 查
    MapUserUserEntity queryOne(Integer id);
    Page<MapUserUserEntity> queryPage(Integer page, Integer show);
}
