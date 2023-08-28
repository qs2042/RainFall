package com.qing.erp.common.module.docker.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainerInfoEntity {
    // ID
    private String Id;

    // 创建时间
    private String Created;

    // 路径
    private String Path;

    // 参数
    private String[] Args;

    // 状态
    private State State;

    // 镜像
    private String Image;

    // 解析配置路径
    private String ResolvConfPath;

    // 主机名路径
    private String HostnamePath;

    // 主机路径
    private String HostsPath;

    // 日志路径
    private String LogPath;

    // 名称
    private String Name;

    // 重启计数
    private int RestartCount;

    // 驱动
    private String Driver;

    // 平台
    private String Platform;

    // 挂载标签
    private String MountLabel;

    // 进程标签
    private String ProcessLabel;

    // AppArmor配置文件
    private String AppArmorProfile;

    // 执行ID TODO: 不知道类型
    private Object ExecIDs;

    // TODO: HostConfig
    private Map<String, Object> HostConfig;

    // 图形驱动器
    private GraphDriver GraphDriver;

    // TODO: Mounts
    private List<Map<String, Object>> Mounts;

    // TODO: Config
    private Map<String, Object> Config;

    // TODO: NetworkSettings
    private Map<String, Object> NetworkSettings;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class State {
        // 状态
        private String Status;

        // 是否正在运行
        private boolean Running;

        // 是否已暂停
        private boolean Paused;

        // 是否正在重新启动
        private boolean Restarting;

        // 是否因内存不足被终止
        private boolean OOMKilled;

        // 是否已停止
        private boolean Dead;

        // 进程ID
        private int Pid;

        // 退出码
        private int ExitCode;

        // 错误信息
        private String Error;

        // 启动时间
        private String StartedAt;

        // 结束时间
        private String FinishedAt;

        // 构造函数、其他方法等
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GraphDriver {
        // 数据
        private Data Data;

        // 驱动器名称
        private String Name;

        @lombok.Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Data {
            // 底层目录
            private String LowerDir;

            // 合并目录
            private String MergedDir;

            // 上层目录
            private String UpperDir;

            // 工作目录
            private String WorkDir;
        }
    }

}
