package com.qing.erp.module.monitor.controller;

import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.ListView;
import com.qing.erp.common.data.R;
import com.qing.erp.common.module.nacos.NacosClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/nacos")
public class NacosController {
    @Autowired
    private NamingService namingService;

    @Autowired
    private NacosClient client;

    @GetMapping("/getServerStatus")
    @SneakyThrows
    public R getServerStatus() {
        return R.ok().dataAdd("serverStatus", namingService.getServerStatus());
    }

    @GetMapping("/getServiceList")
    @SneakyThrows
    public R getServiceList() {
        ListView<String> services = namingService.getServicesOfServer(1, Integer.MAX_VALUE);
        return R.ok()
                .dataAdd("subscribeServices", namingService.getSubscribeServices())
                .dataAdd("count", services.getCount())
                .dataAdd("list", services.getData());
    }

    @GetMapping("/getServiceInstanceList")
    @SneakyThrows
    public R getServiceInstanceList(String serviceName) {
        return R.ok()
                .dataAdd("list", client.getInstanceList(serviceName))
                .dataAdd("true", namingService.selectInstances(serviceName, true))
                .dataAdd("false", namingService.selectInstances(serviceName, false));
    }
}
