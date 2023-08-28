package com.qing.erp.member.controller;

// Spring类
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
// 项目类
import com.qing.erp.common.data.R;
import com.qing.erp.member.entity.UserSecurityEntity;
import com.qing.erp.member.service.UserSecurityImpl;
// 动态类
import java.util.Date;


/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
@RequestMapping("/userSecurity")
@RestController
public class UserSecurityController {
    @Autowired
    private UserSecurityImpl impl;

    @PostMapping("/add")
    public R add(UserSecurityEntity entity) {
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
    public R update(UserSecurityEntity entity) {
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
        return R.ok().dataAdd("page", impl.queryPage(page, show));
    }
}