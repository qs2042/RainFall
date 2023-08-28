package com.qing.erp.system.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.qing.erp.system.pojo.DevLogsEntity;
import com.qing.erp.system.service.DevLogsImpl;


import com.qing.erp.common.data.R;

/**
 * 这是自动生成的类
 *
 * @author halfRain
 * @email 2042136767@qq.com
 * @date Thu Jul 06 20:31:48 CST 2023
 */
@RequestMapping("devLogs")
@RestController
public class DevLogsController {
    @Autowired
    private DevLogsImpl impl;

    @PostMapping("/add")
    public R add(DevLogsEntity entity) {
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
    public R update(DevLogsEntity entity) {
        return impl.update(entity);
    }

    @GetMapping("/queryPage")
    public R queryPage(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "show", required = false, defaultValue = "8") Integer show) {
        return impl.queryPage(page, show);
    }
}