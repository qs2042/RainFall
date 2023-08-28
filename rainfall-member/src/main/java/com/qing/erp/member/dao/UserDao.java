package com.qing.erp.member.dao;

// Java类
import java.io.Serializable;
// Spring类
import org.springframework.data.jpa.repository.JpaRepository;
// 项目类
import com.qing.erp.member.entity.UserEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
public interface UserDao extends JpaRepository<UserEntity, Integer>, Serializable {
    // 增
    // 删
    // 改
    // 查
    UserEntity findByAccountAndPassword(String account, String password);
}
