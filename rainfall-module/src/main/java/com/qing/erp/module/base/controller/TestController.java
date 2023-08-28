package com.qing.erp.module.base.controller;

import com.qing.erp.common.data.R;
import com.qing.erp.common.web.ServletUtil;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
@RequestMapping("test")
public class TestController {

    @ResponseBody
    @RequestMapping("hello")
    public R hello() {
        return R.ok();
    }

    @ResponseBody
    @RequestMapping("getAuthor")
    public R getAuthor() {
        return R.ok()
                .dataAdd("qq", "2042136767")
                .dataAdd("author", "R");
    }

    // 用于测试feign的headers(token，IP)是否传递
    @ResponseBody
    @RequestMapping("show")
    public R show(HttpServletRequest req, HttpServletResponse resp) {
        val reqHeaders = ServletUtil.getHeadersByMap(req);
        return R.ok()
                .dataAdd("req_headers", reqHeaders)
                .dataAdd("token", req.getHeader("token"))
                .dataAdd("ip", ServletUtil.getIp(req));
    }

    // 用于测试分布式session, 不过我前后端分离, 不需要用到这个
    // http://localhost:88/api/system/test/setCookie?url=http://localhost:88/api/auth/test/getCookie
    /*
      1.return "redirect:http://auth.gulimall.com/reg.html"【采用】 重定向Get请求【配合RedirectAttributes共享数据】
      2.return "redirect:http:/reg.html"                   【采用】 重定向Get请求，省略当前服务url【配合RedirectAttributes共享数据】
      3.return "redirect:/reg.html"                                重定向Get请求，使用视图控制器拦截请求并映射reg视图【配合RedirectAttributes共享数据】【bug：会以ip+port来重定向】
      4.return "forward:http://auth.gulimall.com/reg.html";        请求转发与当前请求方式一致（Post请求）【配合Model共享数据】【异常404：当前/reg.html不存在post请求】
      5.return "forward:http:/reg.html";                           请求转发与当前请求方式一致（Post请求），省略当前服务url 【配合Model共享数据】【异常404：当前/reg.html不存在post请求】
      6.return "forward:/reg.html";                                请求转发与当前请求方式一致（Post请求），使用视图控制器拦截请求并映射reg视图【配合Model共享数据】【异常405：Request method 'POST' not supported，视图控制器必须使用GET请求访问，而当前请求转发使用post方式，导致异常】
      7.return "reg";                                              视图解析器前后拼串查找资源返回【配合Model共享数据】
     */
    @RequestMapping("setCookie")
    public String setCookie(String url, HttpServletRequest req, HttpServletResponse response) {
        String token = UUID.randomUUID().toString().replace("-", "");

        // [问题1] 不能跨域名共享cookie
        // 不同微服务之间传递cookie, 需要使用特殊方案
        // 因为不同域名或不同端口都会造成跨域, 导致不能共享cookie
        Cookie cookie = new Cookie("sso_token", token);

        // 1.放大cookie的作用域(自己设置domain or 使用springSession设置domain放大作用域)
        // 首次使用session时，spring会自动颁发cookie设置domain，所以这里手动设置cookie很麻烦，
//        cookie.setDomain("127.0.0.1:11000");
        // 采用springSession的方式颁发父级域名的domain权限

        // [问题2] 集群下同一个服务不能跨JVM共享session
        // 集群环境下，多个会员服务节点和不同服务之间也不共享JVM，session不共享

        // 1.Tomcat修改配置文件就可以支持
        // 但是缺点: 延迟, 占用带宽, 内存占用【所有Tomcat都需要全量保存数据】

        // 2.客户端存储
        // 但是缺点: 每次请求都会携带大量信息, 且cookie有长度限制, 保存在用户电脑可能会遭受泄露篡改窃取

        // 3.hash一致性, 只需要改nginx配置, 不需要修改应用代码
        // 只要hash属性的值分布是均匀的, 多态web-server的负载是均衡的
        // 可支持web-server水平扩展(session同步法受内存限制是不行的)
        // 但是缺点: session还是存在web-server中, 重启可能会导致部分session丢失
        // 如果web-server水平扩展或是rehash后session重新分布, 会导致部分用户路由不到正确的session

        // [问题3] 分布式下不同服务共享session

        // 1.统一储存
        // 没有安全隐患, 可以水平扩展, 数据库/缓存水平切分即可
        // web-server重启或扩容都不会丢失session
        // 但是: 增加网络调用, 需要修改应用代码, 且redis速度不如内存, 但是SpringSession可以解决

        // 2.token令牌
        // 使用redis + springSecurity存token令牌，每个调用接口都带令牌访问

        response.addCookie(cookie);

        // 重定向可以利用 RedirectAttributes 来设置重定向数据域
        // 原理是使用session，重定向请求后根据cookie拿到session的数据
        // attributes.addAttribute()        session中的数据可多次使用
        // attributes.addFlashAttribute();  session中的数据只使用一次
        return "redirect:" + url + "?token=" + token;
    }

    @ResponseBody
    @RequestMapping("getCookie")
    public R Cookie(String token, HttpServletRequest req) {
        val cookies = req.getCookies();
        return R.ok()
                .dataAdd("token", token)
                .addList(cookies);
    }
}
