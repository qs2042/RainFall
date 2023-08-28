package com.qing.erp.test.feign;

import com.qing.erp.common.data.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "rainfall-system")
public interface SystemFeignService {
    // ...
    @GetMapping("/server/maven")
    R maven(@RequestParam boolean viewAggregate);

    @RequestMapping("/test/hello")
    R hello();

    @RequestMapping("test/show")
    R show();

    // ...
    @RequestMapping("/kaptcha/list")
    R list(String token);

    @RequestMapping(value = "/kaptcha/img", produces = MediaType.IMAGE_JPEG_VALUE)
    ResponseEntity<Resource> img();

    @RequestMapping("/kaptcha/check")
    R check(String code);

}
