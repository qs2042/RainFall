package com.qing.erp.auth.feign;

import com.qing.erp.auth.entity.UserLoginVO;
import com.qing.erp.auth.entity.UserRegisterVO;
import com.qing.erp.common.data.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "rainfall-member")
public interface MemberFeignService {

    @RequestMapping("/user/register")
    R regist(UserRegisterVO user);

    @RequestMapping("/user/login")
    R login(UserLoginVO user);
}
