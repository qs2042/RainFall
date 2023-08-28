package com.qing.erp.member.dao;

// Java类
import java.io.Serializable;
// Spring类
import org.springframework.data.jpa.repository.JpaRepository;
// 动态类
import java.util.Date;
import java.util.List;
// 项目类
import com.qing.erp.member.entity.UserInventorUniqueEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
public interface UserInventorUniqueDao extends JpaRepository<UserInventorUniqueEntity, Integer>, Serializable {
    // 增
    // 删
    // 改
    // 查


    List<UserInventorUniqueEntity> queryAllByUserId(Integer id);
}
