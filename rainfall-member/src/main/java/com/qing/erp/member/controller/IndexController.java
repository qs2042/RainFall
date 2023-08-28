package com.qing.erp.member.controller;

import com.qing.erp.common.data.R;
import com.qing.erp.common.io.IOUtils;
import com.qing.erp.common.web.ServletUtil;
import com.qing.erp.member.feign.SystemFeignService;
import com.qing.erp.member.pojo.User;
import com.qing.erp.member.valid.group.UpdateGroup;
import com.qing.erp.member.vo.UserVo;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    SystemFeignService systemFeignService;

    @GetMapping("/list")
    public R list() {
        return systemFeignService.list("");
    }
    @SneakyThrows
    @GetMapping("/img")
    public void img(HttpServletRequest req, HttpServletResponse resp) {
        // 定义response输出类型为image/jpeg类型
        resp.setHeader("Cache-Control", "no-store");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");

        val image = systemFeignService.img();

        val is = image.getBody().getInputStream();

        // 使用response输出流输出图片的byte数组
        ServletOutputStream responseOutputStream = resp.getOutputStream();
        responseOutputStream.write(IOUtils.toByteArray(is));

        responseOutputStream.flush();
        responseOutputStream.close();
    }

    // 将异常交给全局异常管理器处理
    @GetMapping("/test")
    public R test(@Valid User user) {
        return R.ok().dataAdd("user", user);
    }

    // 手动捕获Valid异常(通过BindingResult)
    @GetMapping("/testResult")
    public R testResult(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            val r = result.getFieldErrors().stream().map(v -> new HashMap<String, String>(){{
                put("field", v.getField());
                put("msg", v.getDefaultMessage());
            }}).collect(Collectors.toList());
            return R.error().dataAdd("list", r);
        }

        return R.ok().dataAdd("user", user);
    }

    // spring提供的分组校验
    @GetMapping("/testValidated")
    public R testValidated(@Validated(UpdateGroup.class) User user) {
        return R.ok().dataAdd("user", user);
    }

    @GetMapping("/testVo")
    public R testVo(UserVo userVo) {
        val user = new User();
        BeanUtils.copyProperties(userVo, user);
        // 保存基本数据: userMapper.save(user);
        // 保存关联关系: UserInfoMapper.save(new UserInfo(userVo.getSex()));
        // 保存关联关系: UserLvMapper.save(new UserLv(userVo.getExp()));
        return R.ok().dataAdd("user", user).dataAdd("userVo", userVo);
    }

}
