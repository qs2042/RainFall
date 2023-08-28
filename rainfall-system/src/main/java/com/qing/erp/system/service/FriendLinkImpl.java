package com.qing.erp.system.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import java.util.Arrays;
import java.util.Date;


import com.qing.erp.common.data.R;
import com.qing.erp.system.dao.FriendLinkDao;
import com.qing.erp.system.pojo.FriendLinkEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Wed Jul 12 18:22:26 CST 2023
*/
@Service
public class FriendLinkImpl implements IFriendLinkService {
    @Autowired
    private FriendLinkDao dao;

    @Override
    public R add(FriendLinkEntity entity) {
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
    public R update(FriendLinkEntity entity) {
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