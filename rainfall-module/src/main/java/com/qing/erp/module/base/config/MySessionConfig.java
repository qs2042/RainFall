package com.qing.erp.module.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.session.web.http.CookieSerializer;
//import org.springframework.session.web.http.DefaultCookieSerializer;

// 原理
/*
核心原理: 装饰者模式+自动续期

# [1]
@EnablcRedisHttpSession
导入RedisHttpSessionConfiguration配置

# 1.给容器中添加了一个组件
SessionRepository -> 【RedisOperationsSessionRepository】 -> redis操作session(session的增删改查操作)

# 2.0
SessionRepositoryFiLter -> Filter:session'存储过滤器;每个请求过来都必须经过filter
# 2.1
创建的时侯，就自动从容器中获取到了sessionRepository;
# 2.2
原始的request，response都被包装。SessionRepositoryRequestWrapper，                                                        SessionRepositoryResponseWrapper
# 2.3
以后获取session。 request.getSession();
# 2.4
wrappedRequest.getSession();如果session中不存在，就到redis中查找SessionRepositort中获取到getById(xx)

# [2]
Spring Session 会给redis中的session数据自动延期

Spring Session核心方法：SessionRepositoryFilter过滤器

protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain)throws ServletException,IOException {
    request.setAttribute(SESSION_REPOSITORY_ATTR,this.sessionRepository);

    // 装饰者模式，包装request和response，然后将包装后的request和response对象放行
    // 然后request和response被换成了SessionRepositoryRequestWrapper和SessionRepositoryResponseWrapper对象
    SessionRepositoryFilter<S>.SessionRepositoryRequestWrapper wrappedRequest=new SessionRepositoryFilter.SessionRepositoryRequestWrapper(request,response);
    SessionRepositoryFilter.SessionRepositoryResponseWrapper wrappedResponse=new SessionRepositoryFilter.SessionRepositoryResponseWrapper(wrappedRequest,response);

    try{
        filterChain.doFilter(wrappedRequest,wrappedResponse);
    }finally{
        wrappedRequest.commitSession();
    }
}
 */
//@Configuration
// 我前后端分离, 不需要用到这个
public class MySessionConfig {
    /*@Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setDomainName("127.0.0.1");// 放大作用域
        cookieSerializer.setCookieName("rainfall");
        cookieSerializer.setCookieMaxAge(60 * 60 * 24 * 7);// 指定cookie有效期7天，会话级关闭浏览器后cookie即失效
        return cookieSerializer;
    }

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        // 指定session序列化到redis的序列化器(默认使用jdk序列化器)
        // return new Jackson2JsonRedisSerializer<Object>(Object.class);
        // 无法保存对象类型，反序列化后默认使用Map封装
        return new GenericJackson2JsonRedisSerializer();
    }*/
}
