package com.qing.erp.system.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.qing.erp.common.data.R;
import com.qing.erp.system.dao.DictDao;
import com.qing.erp.system.pojo.DictEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Mon Jul 03 16:00:00 CST 2023
*/
@Service
public class DictImpl implements IDictService {
    @Autowired
    private DictDao dao;

    @Override
    public R add(DictEntity entity) {
        entity.setId(null);
        dao.save(entity);
        return R.ok();
    }

    @Override
    public R remove(Integer id) {
        if (id == null) {
            return R.error();
        }
        dao.deleteById(id);
        return R.ok();
    }

    @Override
    public R removeList(Integer[] ids) {
        if (ids == null) {
            return R.error();
        }
        dao.deleteAllById(Arrays.asList(ids));
        return R.ok();
    }

    @Override
    public R update(DictEntity entity) {
        if (entity.getId() == null) {
            return R.error();
        }
        entity.setUpdatedAt(new Date());
        dao.save(entity);
        return R.ok();
    }

    @Override
    public R queryPage(Integer page, Integer show) {
        if (page == null || show == null) {
            return R.error();
        }
        val r = dao.findAll(PageRequest.of(page, show));
        return R.ok().dataAdd("page", r);
    }
}