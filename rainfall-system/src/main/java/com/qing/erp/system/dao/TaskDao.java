package com.qing.erp.system.dao;


import com.qing.erp.system.pojo.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

/**
 * 这是自动生成的类
 *
 * @author halfRain
 * @email 2042136767@qq.com
 * @date Tue Jul 11 19:53:08 CST 2023
 */
public interface TaskDao extends JpaRepository<TaskEntity, Integer>, Serializable {
    List<TaskEntity> findByEnabled(boolean enabled);
}

