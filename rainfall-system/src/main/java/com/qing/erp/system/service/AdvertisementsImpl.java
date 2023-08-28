package com.qing.erp.system.service;

import com.qing.erp.system.pojo.AdvertisementsVo;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import java.util.Arrays;
import java.util.Date;


import com.qing.erp.common.data.R;
import com.qing.erp.system.dao.AdvertisementsDao;
import com.qing.erp.system.pojo.AdvertisementsEntity;

/**
 * 这是自动生成的类
 *
 * @author halfRain
 * @email 2042136767@qq.com
 * @date Sun Jul 16 23:21:37 CST 2023
 */
@Service
public class AdvertisementsImpl implements IAdvertisementsService {
    @Autowired
    private AdvertisementsDao dao;

    @Autowired
    private AdvertisementsDao advertisementsDao;

    @Override
    public R add(AdvertisementsEntity entity) {
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
    public R update(AdvertisementsEntity entity) {
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
        val n = r.map(v -> {
            val aev = new AdvertisementsVo();
            BeanUtils.copyProperties(v, aev);
            //
            aev.setClicks(0);
            return aev;
        });
        return R.ok().dataAdd("page", r).dataAdd("pn", n);
    }
}