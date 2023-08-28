package com.qing.erp.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import com.qing.erp.system.pojo.LogsEntity;
import com.qing.erp.system.service.LogsImpl;


import com.qing.erp.common.data.R;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Wed Jul 05 19:11:13 CST 2023
*/
@RequestMapping("logs")
@RestController
public class LogsController {
    @Autowired
    private LogsImpl impl;

    @PostMapping("/add")
    public R add(LogsEntity entity) {
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
    public R update(LogsEntity entity) {
        return impl.update(entity);
    }

    @GetMapping("/queryPage")
    public R queryPage(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "show", required = false, defaultValue = "8") Integer show) {
        return impl.queryPage(page, show);
    }
}