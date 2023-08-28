package com.qing.erp.member.service;

// Java类
import java.util.List;
// Spring类
import com.qing.erp.member.entity.UserVO;
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.entity.UserEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
public interface IUserService {
    // 增
    UserEntity add(UserEntity entity);
    // List<UserEntity> addList(List<UserEntity> entity);

    // 删
    void remove(Integer id);
    void removeList(Integer[] ids);

    // 改
    UserEntity update(UserEntity entity);
    // List<UserEntity> updateList(List<UserEntity> entity);

    // 查
    UserEntity queryOne(Integer id);
    Page<UserVO> queryPage(Integer page, Integer show);

    UserVO login(String account, String password);
    UserVO register(UserEntity user);
}
