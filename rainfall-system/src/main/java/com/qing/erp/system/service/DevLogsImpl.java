package com.qing.erp.system.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import java.util.Arrays;
import java.util.Date;


import com.qing.erp.common.data.R;
import com.qing.erp.system.dao.DevLogsDao;
import com.qing.erp.system.pojo.DevLogsEntity;

/**
 * 这是自动生成的类
 *
 * @author halfRain
 * @email 2042136767@qq.com
 * @date Thu Jul 06 20:31:48 CST 2023
 */
@Service
public class DevLogsImpl implements IDevLogsService {
    @Autowired
    private DevLogsDao dao;

    @Override
    public R add(DevLogsEntity entity) {
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
    public R update(DevLogsEntity entity) {
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