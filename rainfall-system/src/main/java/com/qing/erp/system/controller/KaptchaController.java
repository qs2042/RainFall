package com.qing.erp.system.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.qing.erp.common.data.E;
import com.qing.erp.common.data.R;
import com.qing.erp.common.web.ServletUtil;
import com.qing.erp.system.aop.log.RLog;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/kaptcha")
public class KaptchaController {
    @Autowired
    DefaultKaptcha defaultKaptcha;

    private static HashMap<String, Object> map = new HashMap<String, Object>();

    // 查看验证码
    @RequestMapping("/list")
    @ResponseBody
    public R list(@RequestParam(name = "token", required = false, defaultValue = "null") String token) {
        /*if (!token.equals("1447583021+2042136767+1933516825")) {
            return R.error("无访问权限");
        }*/
        return R.ok().dataAdd("list", map);
    }

    // 生成验证码
    @RLog(notes = "生成验证码")
    @RequestMapping("/img")
    public void img(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // 生成文本验证码
        String createText = defaultKaptcha.createText();

        // 保存文本验证码
        val ip = ServletUtil.getIp(req);
        map.put(ip, createText);                      // 无过期时间
        //kaptchaMapBean.put(ip, createText, 1, TimeUnit.HOURS); // 有效期为1小时

        // 生成图片验证码
        BufferedImage challenge = defaultKaptcha.createImage(createText);

        // BufferedImage对象转为byte写入到byte数组中
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        ImageIO.write(challenge, "jpg", jpegOutputStream);

        // 定义response输出类型为image/jpeg类型
        resp.setHeader("Cache-Control", "no-store");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");

        // 使用response输出流输出图片的byte数组
        ServletOutputStream responseOutputStream = resp.getOutputStream();
        responseOutputStream.write(jpegOutputStream.toByteArray());

        // 刷新, 关闭流
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    // 校对验证码
    @RequestMapping("/check")
    @ResponseBody
    public R check(HttpServletRequest req, @RequestParam(name = "code") String code) {
        val ip = ServletUtil.getIp(req);

        val rightCode = (String) map.get(ip);

        if (rightCode != null && rightCode.equals(code)) {
            map.remove(code);
            return R.ok("校验成功");
        }

        return R.error("校验失败: 验证码为空或错误");
    }
}

