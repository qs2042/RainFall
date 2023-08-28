package com.qing.erp.module;

import com.qing.erp.common.data.R;
import lombok.val;
import org.apache.commons.lang.ArrayUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class TokenInterceptor implements HandlerInterceptor {

    public static ThreadLocal<Map> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取会话信息，获取登录用户信息
        HttpSession session = request.getSession();

        val token = request.getParameter("token");
        // 判断是携带token
        if (token != null) {
            // 向redis确认token是否存在, 并获取用户信息
        }
        // 封装用户信息（登录状态userId非空，游客状态userId空）
        threadLocal.set(R.ok());
        return true;
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest req, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
