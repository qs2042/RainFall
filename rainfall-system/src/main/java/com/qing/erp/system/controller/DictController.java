package com.qing.erp.system.controller;

import com.qing.erp.system.pojo.DictEntity;
import com.qing.erp.system.service.DictImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.qing.erp.common.data.R;

import java.util.Arrays;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Mon Jul 03 16:00:00 CST 2023
*/
@RequestMapping("dict")
@RestController
public class DictController {
    @Autowired
    DictImpl impl;

    @PostMapping("/add")
    public R add(DictEntity entity) {
        System.out.println(entity);
        return impl.add(entity);
    }

    @PostMapping("/remove")
    public R remove(Integer id) {
        return impl.remove(id);
    }

    @PostMapping("/removeList")
    public R removeList(Integer[] ids) {
        return impl.removeList(ids);
    }

    @PostMapping("/update")
    public R update(DictEntity entity) {
        return impl.update(entity);
    }

    @GetMapping("/queryPage")
    public R queryPage(Integer page, Integer show) {
        return impl.queryPage(page, show);
    }
}