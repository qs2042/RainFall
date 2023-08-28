package com.qing.erp.member.service;

// Java类
import java.util.Arrays;
// 第三方类
import lombok.val;
// Spring类
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.dao.MapUserPositionDao;
import com.qing.erp.member.entity.MapUserPositionEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
@Service
public class MapUserPositionImpl implements IMapUserPositionService {
    @Autowired
    private MapUserPositionDao dao;

    @Override
    public MapUserPositionEntity add(MapUserPositionEntity entity) {
        entity.setId(null);
        return dao.save(entity);
    }

    @Override
    public void remove(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public void removeList(Integer[] ids) {
        dao.deleteAllById(Arrays.asList(ids));
    }

    @Override
    public MapUserPositionEntity update(MapUserPositionEntity entity) {
        if (entity.getId() == null) {
            return null;
        }
        return dao.save(entity);
    }

    @Override
    public MapUserPositionEntity queryOne(Integer id) {
        return id == null ? null : dao.findById(id).get();
    }

    @Override
    public Page<MapUserPositionEntity> queryPage(Integer page, Integer show) {
        if (page == null || show == null) {
            return null;
        }
        return dao.findAll(PageRequest.of(page, show));
    }
}