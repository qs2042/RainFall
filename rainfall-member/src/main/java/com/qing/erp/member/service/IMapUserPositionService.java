package com.qing.erp.member.service;

// Java类
import java.util.List;
// Spring类
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.entity.MapUserPositionEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
public interface IMapUserPositionService {
    // 增
    MapUserPositionEntity add(MapUserPositionEntity entity);
    // List<MapUserPositionEntity> addList(List<MapUserPositionEntity> entity);

    // 删
    void remove(Integer id);
    void removeList(Integer[] ids);

    // 改
    MapUserPositionEntity update(MapUserPositionEntity entity);
    // List<MapUserPositionEntity> updateList(List<MapUserPositionEntity> entity);

    // 查
    MapUserPositionEntity queryOne(Integer id);
    Page<MapUserPositionEntity> queryPage(Integer page, Integer show);
}
