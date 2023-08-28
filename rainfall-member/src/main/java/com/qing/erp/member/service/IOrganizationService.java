package com.qing.erp.member.service;

// Java类
import java.util.List;
// Spring类
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.entity.OrganizationEntity;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
public interface IOrganizationService {
    // 增
    OrganizationEntity add(OrganizationEntity entity);
    // List<OrganizationEntity> addList(List<OrganizationEntity> entity);

    // 删
    void remove(Integer id);
    void removeList(Integer[] ids);

    // 改
    OrganizationEntity update(OrganizationEntity entity);
    // List<OrganizationEntity> updateList(List<OrganizationEntity> entity);

    // 查
    OrganizationEntity queryOne(Integer id);
    Page<OrganizationEntity> queryPage(Integer page, Integer show);
}
