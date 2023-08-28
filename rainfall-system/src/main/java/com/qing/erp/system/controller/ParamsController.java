package com.qing.erp.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.qing.erp.system.pojo.ParamsEntity;
import com.qing.erp.system.service.ParamsImpl;


import com.qing.erp.common.data.R;

/**
 * 这是自动生成的类
 *
 * @author halfRain
 * @email 2042136767@qq.com
 * @date Mon Jul 03 16:38:11 CST 2023
 */
@RequestMapping("params")
@RestController
public class ParamsController {
    @Autowired
    private ParamsImpl impl;

    @PostMapping("/add")
    public R add(ParamsEntity entity) {
        return impl.add(entity);
    }

    @PostMapping("/remove")
    public R remove(Integer id) {
        return impl.remove(id);
    }

    @PostMapping("/update")
    public R update(ParamsEntity entity) {
        return impl.update(entity);
    }

    @GetMapping("/queryPage")
    public R queryPage(Integer page, Integer show) {
        return impl.queryPage(page, show);
    }
}