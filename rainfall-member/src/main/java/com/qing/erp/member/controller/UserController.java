package com.qing.erp.member.controller;

// Spring类

import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.entity.UserEntity;
import com.qing.erp.member.service.UserImpl;


/**
 * 这是自动生成的类
 *
 * @author halfRain
 * @email 2042136767@qq.com
 * @date Thu Aug 03 16:55:00 CST 2023
 */
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserImpl impl;

    @PostMapping("/add")
    public R add(UserEntity entity) {
        return R.ok().dataAdd("map", impl.add(entity));
    }

    @PostMapping("/remove")
    public R remove(Integer id) {
        impl.remove(id);
        return R.ok();
    }

    @PostMapping("/removeList")
    public R removeList(Integer[] ids) {
        impl.removeList(ids);
        return R.ok();
    }

    @PostMapping("/update")
    public R update(UserEntity entity) {
        return R.ok().dataAdd("map", impl.update(entity));
    }

    @GetMapping("/queryOne")
    public R queryOne(@RequestParam(value = "id") Integer id) {
        return R.ok().dataAdd("page", impl.queryOne(id));
    }

    @GetMapping("/queryPage")
    public R queryPage(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "show", required = false, defaultValue = "8") Integer show) {
        return R.ok().addPage(impl.queryPage(page, show));
    }

    @PostMapping("/login")
    public R login(String account, String password) {
        val r = impl.login(account, password);

        return R.ok()
                .dataAdd("success", r != null)
                .addMap(r);
    }

    @PostMapping("/register")
    public R register(UserEntity user) {
        val r = impl.register(user);
        return R.ok()
                .dataAdd("success", r != null)
                .addMap(r);
    }

}