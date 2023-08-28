package com.qing.erp.auth.feign;

import com.qing.erp.common.data.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "rainfall-third-party")
public interface ThirdPartFeignService {

    @GetMapping("/test")
    R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code);
}
