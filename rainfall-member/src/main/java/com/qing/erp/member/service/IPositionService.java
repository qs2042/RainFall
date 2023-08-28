package com.qing.erp.member.service;

// Java类
import java.util.List;
// Spring类
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.entity.PositionEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
public interface IPositionService {
    // 增
    PositionEntity add(PositionEntity entity);
    // List<PositionEntity> addList(List<PositionEntity> entity);

    // 删
    void remove(Integer id);
    void removeList(Integer[] ids);

    // 改
    PositionEntity update(PositionEntity entity);
    // List<PositionEntity> updateList(List<PositionEntity> entity);

    // 查
    PositionEntity queryOne(Integer id);
    Page<PositionEntity> queryPage(Integer page, Integer show);
}
