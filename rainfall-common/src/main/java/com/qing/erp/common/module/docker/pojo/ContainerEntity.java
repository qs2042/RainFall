package com.qing.erp.common.module.docker.pojo;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContainerEntity {
    // 容器的唯一标识符
    private String Id;

    // 容器的名称数组
    private List<String> Names;

    // 容器所使用的镜像名称
    private String Image;

    // 容器所使用的镜像的唯一标识符
    private String ImageID;

    // 容器启动时所使用的命令
    private String Command;

    // 容器创建的时间戳
    private long Created;

    // 容器的端口映射数组
    private List<Port> Ports;

    // 容器的标签
    private Map<String, String> Labels;

    // 容器的状态
    private String State;

    // 容器的状态描述
    private String Status;

    // 容器的主机配置
    private HostConfig HostConfig;

    // 容器的网络设置
    private NetworkSettings NetworkSettings;

    // 容器的挂载点数组
    private List<Mount> Mounts;

    // 容器的端口映射类
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Port {
        // IP 地址
        private String Ip;

        // 容器端口
        private int PrivatePort;

        // 公共端口
        private int PublicPort;

        // 端口类型
        private String Type;
    }

    // 容器的主机配置类
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HostConfig {
        // 容器的网络模式
        private String NetworkMode;
    }

    // 容器的网络设置类
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NetworkSettings {
        // 容器的网络映射
        private Map<String, Network> Networks;

        // 容器的网络类
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Network {

            // IP 地址配置
            private Object IpamConfig;

            // 连接
            private List<String> Links;

            // 别名
            private List<String> Aliases;

            // 网络 Id
            private String NetworkID;

            // 端点 Id
            private String EndpointID;

            // 网关
            private String Gateway;

            // IP 地址
            private String IpAddress;

            // IP 前缀长度
            private int IpPrefixLen;

            // IPv6 网关
            private String Ipv6Gateway;

            // 全局 IPv6 地址
            private String GlobalIPv6Address;

            // 全局 IPv6 前缀长度
            private int GlobalIPv6PrefixLen;

            // MAC 地址
            private String MacAddress;

            // 驱动选项
            private Object DriverOpts;
        }
    }

    // 容器的挂载点类
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Mount {
        // 类型
        private String Type;

        // 源
        private String Source;

        // 目标
        private String Destination;

        // 模式
        private String Mode;

        // 是否可写
        private boolean Rw;

        // 传播方式
        private String Propagation;
    }

    /*public static class ContainerEntityDeserializer implements JsonDeserializer<ContainerEntity> {
        @Override
        public ContainerEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            ContainerEntity containerEntity = new ContainerEntity();

            // 处理 Created 字段
            if (jsonObject.has("Created")) {
                JsonElement createdElement = jsonObject.get("Created");
                if (createdElement.isJsonPrimitive() && createdElement.getAsJsonPrimitive().isString()) {
                    String createdString = createdElement.getAsString();

                    try {
                        long createdTimestamp = Instant.parse(createdString).toEpochMilli();
                        containerEntity.setCreated(createdTimestamp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // 将剩余字段交给 Gson 进行默认处理
            val jo = jsonObject.remove("Created");
            containerEntity = new Gson().fromJson(jo, ContainerEntity.class);


            return containerEntity;
        }
    }*/
}