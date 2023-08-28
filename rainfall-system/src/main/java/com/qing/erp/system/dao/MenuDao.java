package com.qing.erp.system.dao;

import com.qing.erp.system.pojo.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface MenuDao extends JpaRepository<MenuEntity, Integer>, Serializable {
    List<MenuEntity> findAllByFlag(boolean flag);
}
