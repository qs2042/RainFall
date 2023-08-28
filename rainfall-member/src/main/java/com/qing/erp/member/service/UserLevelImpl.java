package com.qing.erp.member.service;

// Java类
import java.util.Arrays;
// 第三方类
import lombok.val;
// 动态类
import java.util.Date;
// Spring类
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.dao.UserLevelDao;
import com.qing.erp.member.entity.UserLevelEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
@Service
public class UserLevelImpl implements IUserLevelService {
    @Autowired
    private UserLevelDao dao;

    @Override
    public UserLevelEntity add(UserLevelEntity entity) {
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
    public UserLevelEntity update(UserLevelEntity entity) {
        if (entity.getId() == null) {
            return null;
        }
        return dao.save(entity);
    }

    @Override
    public UserLevelEntity queryOne(Integer id) {
        return id == null ? null : dao.findById(id).get();
    }

    @Override
    public Page<UserLevelEntity> queryPage(Integer page, Integer show) {
        if (page == null || show == null) {
            return null;
        }
        return dao.findAll(PageRequest.of(page, show));
    }

    @Override
    public UserLevelEntity findByUserId(Integer id) {
        return dao.findById(id).orElse(null);
    }
}