package com.qing.erp.module.monitor.controller;

import com.qing.erp.common.data.R;
import com.qing.erp.common.module.docker.DockerClient;
import com.qing.erp.common.module.github.GithubUtil;
import com.qing.erp.common.module.maven.MavenClient;
import com.qing.erp.common.module.nginx.NginxClient;
import com.qing.erp.common.module.redis.RedisClient;
import com.qing.erp.common.system.*;
import com.qing.erp.module.monitor.properties.ServerProperties;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

@RequestMapping("server")
@RestController
public class ServerController {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Autowired
    RedisClient rc;

    @Autowired
    DockerClient dc;

    @Autowired
    ServerProperties serverProperties;

    @GetMapping("/cacheList")
    public R cacheList() {
        return R.ok()
                .dataAdd("Keys", rc.getKeys())
                .dataAdd("CacheKeys", rc.getCacheKeys());
    }

    @GetMapping("/redis")
    public R redis() {
        val info = rc.getInfo();
        return R.ok().put("data", info);
    }

    @GetMapping("/docker")
    public R docker() {
        return R.ok()
                .dataAdd("containerList", dc.containerList())
                .dataAdd("imageList", dc.imageList())
                .dataAdd("networkList", dc.networkList())
                .dataAdd("Info", dc.getInfo())
                .dataAdd("Version", dc.getVersion());
    }

    @GetMapping("/cpu")
    public R cpu() {
        return R.ok().dataAdd("map", CpuInfo.getAll());
    }

    @GetMapping("/physicalMemory")
    public R physicalMemory() {
        return R.ok().dataAdd("map", PhysicalMemoryInfo.getAll());
    }

    @GetMapping("/hdd")
    public R hdd() {
        return R.ok().dataAdd("list", DeviceUtil.getHDDInfo());
    }

    @GetMapping("/network")
    public R network() {
        return R.ok().dataAdd("map", NetworkInfo.getAll());
    }

    // TODO: JVM加载了多少个类
    @GetMapping("/jvm")
    public R jvm() {
        return R.ok()
                .dataAdd("JavaVendor", DeviceUtil.getJavaVendor())
                .dataAdd("JavaVersion", DeviceUtil.getJavaVersion())
                .dataAdd("JavaPath", DeviceUtil.getJavaPath())
                .dataAdd("jvmTotalMemory", JvmUtil.getTotalMemory())
                .dataAdd("jvmUsedMemory", JvmUtil.getUsedMemory())
                .dataAdd("jvmFreeMemory", JvmUtil.getFreeMemory())
                .dataAdd("jvmMemoryUsage", String.format("%.2f%%", JvmUtil.getMemoryUsage()))
                .dataAdd("jvmMemoryIdleRatio", String.format("%.2f%%", JvmUtil.getMemoryIdleRatio()))
//                .dataAdd("jvmGarbageCollectionSize", JvmUtil.getGarbageCollectionSize())
//                .dataAdd("jvmGarbageCollectionCount", JvmUtil.getGarbageCollectionCount())
                .dataAdd("jvmAllGarbageCollectorInfo", JvmUtil.getAllGarbageCollectorInfo());
    }

    @GetMapping("/project")
    public R project() {
        return R.ok()
                .dataAdd("ProjectStartTime", DeviceUtil.getProjectStartTime())
                .dataAdd("ProjectUptime", DeviceUtil.getProjectUptime())
                .dataAdd("ProjectPath", DeviceUtil.getProjectPath())
                .dataAdd("ProjectArguments", DeviceUtil.getProjectArguments())
                .dataAdd("Pid", DeviceUtil.getPid());
    }

    @GetMapping("/all")
    public R all() {
        return R.ok()
                // 杂项
                .dataAdd("SystemPath", DeviceUtil.getSystemPath())
                .dataAdd("SystemOs", DeviceUtil.getSystemOs())
                .dataAdd("SystemOsName", DeviceUtil.getSystemOsName())
                .dataAdd("SystemOsVersion", DeviceUtil.getSystemOsVersion())
                .dataAdd("SystemOsArch", DeviceUtil.getSystemOsArch())
                .dataAdd("SystemUser", DeviceUtil.getSystemUser())
                .dataAdd("SystemUserCountry", DeviceUtil.getSystemUserCountry())

                .dataAdd("ComputerName", DeviceUtil.getComputerName())
                .dataAdd("ComputerUptime", DeviceUtil.getComputerUptime())
                .dataAdd("ComputerStartTime", DeviceUtil.getComputerStartTime())
                .dataAdd("ComputerComputerPowerUsage", DeviceUtil.getComputerComputerPowerUsage())
                .dataAdd("ComputerBiosVersion", DeviceUtil.getComputerBiosVersion())
                .dataAdd("ComputerMeVersion", DeviceUtil.getComputerMeVersion())
                .dataAdd("", "");
    }

    @GetMapping("/repo")
    public R repo() {
        return R.ok().put("data", GithubUtil.getInfo(serverProperties.getRepoUrl()));
    }

    public static String getCurrentProjectPath() {
        // 获取类加载器
        ClassLoader classLoader = ServerController.class.getClassLoader();

        // 获取当前类所在的目录URL
        URL resource = classLoader.getResource("");

        if (resource != null) {
            // 得到路径: /Rainfall/{ rainfall-system, rainfall-member... }/target/classes/
            String basePath = resource.getPath();
            // 获取父目录的父目录
            return new File(basePath).getParentFile().getParent();
        }
        return null;
    }

    @GetMapping("/maven")
    public R maven(@RequestParam(value = "viewAggregate", required = false, defaultValue = "0") boolean viewAggregate) {
        val path = String.join("//", getCurrentProjectPath(), "pom.xml");

        val mc = viewAggregate ? MavenClient.build() : MavenClient.build(path);
        return R.ok().put("tree", mc.getProject());
    }

    @GetMapping("/nginx")
    public R nginx() {
        return R.ok().put("list", NginxClient.getList(serverProperties.getNginxUrl()));
    }

    @GetMapping("/queryAllBeans")
    public R queryAllBeans() {
        String[] beanNames = context.getBeanDefinitionNames();
        return R.ok().dataAdd("list", beanNames);
    }

    @GetMapping("/queryBean")
    public R queryBean(String beanName) {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
        return R.ok()
                .dataAdd("beanName", beanName)
                .dataAdd("beanClassName", beanDefinition.getBeanClassName())
                .dataAdd("scope", beanDefinition.getScope())
                .dataAdd("dependsOn", beanDefinition.getDependsOn())
                .dataAdd("toString", beanDefinition.toString());
    }

}