package com.qing.erp.common.module.docker.pojo;

import com.qing.erp.common.module.docker.BaseJsonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoEntity extends BaseJsonEntity {
    private String ID;
    private int Containers;
    private int ContainersRunning;
    private int ContainersPaused;
    private int ContainersStopped;
    private int Images;
    private int NFd;
    private int NGoroutines;
    private int NEventsListener;
    private int NCPU;
    private long MemTotal;

    private Runtimes Runtimes;
    private Swarm Swarm;
    private Commit ContainerdCommit;
    private Commit RuncCommit;
    private Commit InitCommit;
    private Plugins Plugins;
    private RegistryConfig RegistryConfig;

    private List<List<String>> DriverStatus;
    private List<String> Labels;
    private List<String> SecurityOptions;
    private List<String> Warnings;

    private boolean LiveRestoreEnabled;
    private boolean MemoryLimit;
    private boolean SwapLimit;
    private boolean KernelMemory;
    private boolean KernelMemoryTCP;
    private boolean CpuCfsPeriod;
    private boolean CpuCfsQuota;
    private boolean CPUShares;
    private boolean CPUSet;
    private boolean PidsLimit;
    private boolean IPv4Forwarding;
    private boolean BridgeNfIptables;
    private boolean BridgeNfIp6tables;
    private boolean Debug;
    private boolean OomKillDisable;
    private boolean ExperimentalBuild;

    private String Driver;
    private String SystemTime;
    private String LoggingDriver;
    private String CgroupDriver;
    private String CgroupVersion;
    private String KernelVersion;
    private String OperatingSystem;
    private String OSVersion;
    private String OSType;
    private String Architecture;
    private String IndexServerAddress;
    private Object GenericResources;
    private String DockerRootDir;
    private String HttpProxy;
    private String HttpsProxy;
    private String NoProxy;
    private String Name;
    private String Isolation;
    private String InitBinary;
    private String DefaultRuntime;
    private String ServerVersion;

    @Data
    public static class Plugins {
        private List<String> Volume;
        private List<String> Network;
        private Object Authorization;
        private List<String> Log;
    }

    @Data
    public static class RegistryConfig {
        private List<String> AllowNondistributableArtifactsCIDRs;
        private List<String> AllowNondistributableArtifactsHostnames;
        private List<String> InsecureRegistryCIDRs;
        private IndexConfigs IndexConfigs;
        private List<String> Mirrors;
    }

    @Data
    public static class IndexConfigs {
        private DockerIo docker_io;
    }

    @Data
    public static class DockerIo {
        private String Name;
        private List<String> Mirrors;
        private boolean Secure;
        private boolean Official;
    }

    @Data
    public static class Runtimes {
        private IORuncV2 io_containerd_runc_v2;
        private IORuntimeV1Linux io_containerd_runtime_v1_linux;
        private Runc runc;
    }

    @Data
    public static class IORuncV2 {
        private String path;
    }

    @Data
    public static class IORuntimeV1Linux {
        private String path;
    }

    @Data
    public static class Runc {
        private String path;
    }

    @Data
    public static class Swarm {
        private String NodeID;
        private String NodeAddr;
        private String LocalNodeState;
        private boolean ControlAvailable;
        private String Error;
        private Object RemoteManagers;
    }

    @Data
    public static class Commit {
        private String ID;
        private String Expected;
    }
}
