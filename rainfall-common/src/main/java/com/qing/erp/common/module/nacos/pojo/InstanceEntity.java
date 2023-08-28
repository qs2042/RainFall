package com.qing.erp.common.module.nacos.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstanceEntity {
    private String name;
    private String groupName;
    private String clusters;
    private int cacheMillis;
    private List<Host> hosts;
    private long lastRefTime;
    private String checksum;
    private boolean allIPs;
    private boolean reachProtectionThreshold;
    private boolean valid;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Host {
        private String instanceId;
        private String ip;
        private int port;
        private double weight;
        private boolean healthy;
        private boolean enabled;
        private boolean ephemeral;
        private String clusterName;
        private String serviceName;
        private Map<String, String> metadata;
        private int instanceHeartBeatTimeOut;
        private int ipDeleteTimeout;
        private int instanceHeartBeatInterval;

        // constructors and getters/setters omitted for brevity
    }
}
