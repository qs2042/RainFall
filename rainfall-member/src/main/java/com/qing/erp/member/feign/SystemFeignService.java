package com.qing.erp.member.feign;

import com.qing.erp.common.data.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

// ...
/*
构造请求数据，将对象转为json
RequestTemplate template = buildTemplateFromArgs.create(argv);

发送请求进行执行：【执行成功会解码响应数据】
excuteAndDecode(template)

执行请求会有重试机制
while(true){
    try{
        excuteAndDecode(template)
    }catch() {
        try{
            // 默认重试5次【具体是否重试查看重试器的实现】
            retryer.continueOrPropagate(e);
         }catch() {
            throw ex;
         }
        continue;
    }
}
 */

//   configuration除了interceptor, 还能配合fallback实现自己当前的接口类
//   例如当前的类是 SystemFeignService, 我就可以实现一个SystemFeignImpl
//   { fallback=SystemFeignImpl.class, configuration=SystemFeignImpl.class }
//   当触发熔断之后, 具体会调用SystemFeignImpl, 而不是远程接口
@FeignClient(name = "rainfall-system")
public interface SystemFeignService {

    @RequestMapping("/kaptcha/list")
    R list(String token);

    @RequestMapping(value = "/kaptcha/img", produces = MediaType.IMAGE_JPEG_VALUE)
    ResponseEntity<Resource> img();

    @RequestMapping("/kaptcha/check")
    R check(String code);
}
