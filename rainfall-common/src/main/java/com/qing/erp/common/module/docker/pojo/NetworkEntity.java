package com.qing.erp.common.module.docker.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NetworkEntity {
    private String Name;
    private String Id;
    private String Created;
    private String Scope;
    private String Driver;
    private IPAM IPAM;
    private ConfigFrom ConfigFrom;
    private boolean EnableIPv6;
    private boolean Internal;
    private boolean Attachable;
    private boolean Ingress;
    private boolean ConfigOnly;
    private Map<String, Container> Containers = new HashMap<>();
    private Map<String, String> Options = new HashMap<>();
    private Map<String, String> Labels = new HashMap<>();

    @Data
    public static class IPAM {
        private String Driver;
        private Map<String, Object> Options;
        private List<Config> Config;

        @Data
        public static class Config {
            private String Subnet;
            private String Gateway;
        }
    }

    @Data
    public static class ConfigFrom {
        private String Network;
    }

    @Data
    public static class Container {
        private String Name;
        private String EndpointID;
        private String MacAddress;
        private String IPv4Address;
        private String IPv6Address;
    }
}