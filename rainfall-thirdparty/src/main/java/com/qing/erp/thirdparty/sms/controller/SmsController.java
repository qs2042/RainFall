package com.qing.erp.thirdparty.sms.controller;

import com.qing.erp.common.data.R;
import com.qing.erp.thirdparty.sms.service.SmsServiceImpl;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// TODO: 没测试过功能
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    SmsServiceImpl smsService;

    /**
     * 发送短信验证码
     * 提供其他模块调用
     * @param phone 号码
     * @param code  验证码
     */
    @GetMapping("/test")
    public R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        if (!smsService.sendCode(phone, code)) {
            return R.error();
        }
        return R.ok();
    }
}
