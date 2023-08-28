package com.qing.erp.system.controller;

import com.qing.erp.common.data.R;
import com.qing.erp.system.aop.log.RLog;
import com.qing.erp.system.pojo.MenuEntity;
import com.qing.erp.system.service.MenuImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("menu")
public class MenuController {
    @Autowired
    MenuImpl impl;

    @PostMapping("/add")
    public R add(MenuEntity entity) {
        return impl.add(entity);
    }

    @PostMapping("/remove")
    public R remove(Integer id) {
        return impl.remove(id);
    }

    @PostMapping("/removeList")
    public R removeList(Integer[] ids) {
        impl.removeList(ids);
        return R.ok();
    }

    @PostMapping("/update")
    public R update(MenuEntity entity) {
        return impl.update(entity);
    }

    @GetMapping("/queryPage")
    public R queryPage(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "show", required = false, defaultValue = "5") Integer show) {
        return impl.queryPage(page, show);
    }

    @RLog(module = "menu")
    @GetMapping("/queryTree")
    public R queryTree(@RequestParam(value = "flag", required = false) Boolean flag) {
        return impl.queryTree(flag);
    }
}
