package com.qing.erp.module.base.config;

import com.qing.erp.common.web.ServletUtil;
import com.qing.erp.common.web.SpringMVCUtil;
import feign.RequestInterceptor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean("requestInterceptorHeaders")
    public RequestInterceptor requestInterceptorHeaders() {
        return requestTemplate -> {
            // 通过Holder上下文保持器获取当前请求的 HttpServletRequest
            val req = SpringMVCUtil.getReq();

            // 将所有headers传下去 考虑要不要将全部header都copy过去
            /*JavaUtils.convertEnumerationToList(req.getHeaderNames())
                    .forEach(k -> {
                        val v = req.getHeader(k);
                        requestTemplate.header(k, v);
                    });*/

            // cache-control, accept-encoding, forwarded...

            // user-agent
            requestTemplate.header("user-agent", req.getHeader("user-agent"));

            // x-forwarded-for
            requestTemplate.header("X-Forwarded-For", ServletUtil.getIp(req));

            // 将用户的token添加到请求头中
            requestTemplate.header("token", req.getHeader("token"));

            // 这里没用SpringSession维护分布式sessions, 所以算是个无效代码
            requestTemplate.header("cookie", req.getHeader("cookie"));
        };
    }
}
