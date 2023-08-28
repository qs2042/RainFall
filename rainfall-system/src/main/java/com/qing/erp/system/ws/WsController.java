package com.qing.erp.system.ws;

import com.qing.erp.common.data.R;
import com.qing.erp.system.ws.holder.WsOnlineHolder;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

// TODO: WS之后需要单独分离成一个微服务, 并且这个微服务还不能多集群
@RequestMapping("ws")
@RestController
public class WsController {
    @GetMapping("/list")
    public R list() {
        val list = new ArrayList<>(WsOnlineHolder.sessions);
        return R.ok().addList(list);
    }
}
