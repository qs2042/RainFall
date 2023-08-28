package com.qing.erp.member.service;

// Java类
import java.util.List;
// Spring类
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.entity.DepartmentEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
public interface IDepartmentService {
    // 增
    DepartmentEntity add(DepartmentEntity entity);
    // List<DepartmentEntity> addList(List<DepartmentEntity> entity);

    // 删
    void remove(Integer id);
    void removeList(Integer[] ids);

    // 改
    DepartmentEntity update(DepartmentEntity entity);
    // List<DepartmentEntity> updateList(List<DepartmentEntity> entity);

    // 查
    DepartmentEntity queryOne(Integer id);
    Page<DepartmentEntity> queryPage(Integer page, Integer show);
}
