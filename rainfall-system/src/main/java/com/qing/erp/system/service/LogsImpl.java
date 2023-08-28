package com.qing.erp.system.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;


import com.qing.erp.common.data.R;
import com.qing.erp.system.dao.LogsDao;
import com.qing.erp.system.pojo.LogsEntity;

import java.util.Arrays;
import java.util.Date;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Wed Jul 05 19:11:13 CST 2023
*/
@Service
public class LogsImpl implements ILogsService {
    @Autowired
    private LogsDao dao;

    @Override
    public R add(LogsEntity entity) {
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
    public R update(LogsEntity entity) {
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