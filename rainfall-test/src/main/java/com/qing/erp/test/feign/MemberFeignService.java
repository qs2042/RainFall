package com.qing.erp.test.feign;

import com.qing.erp.common.data.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "rainfall-member")
public interface MemberFeignService {

    @GetMapping("/server/maven")
    R maven(@RequestParam boolean viewAggregate);
}
