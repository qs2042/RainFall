package com.qing.erp.common.module.nacos.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceEntity {
    private int count;
    private List<Service> serviceList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Service {
        private String name;
        private String groupName;
        private int clusterCount;
        private int ipCount;
        private int healthyInstanceCount;
        private String triggerFlag;
    }

}