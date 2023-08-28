package com.qing.erp.common.system;

import lombok.val;

import java.lang.management.*;
import java.util.HashMap;
import java.util.List;

public class JvmUtil {
    // JVM内存所需的常量
    private static final long MEGABYTE = 1024 * 1024;

    // JVM总内存
    public static long getTotalMemory() {
        return Runtime.getRuntime().totalMemory() / MEGABYTE;
    }

    // JVM已用内存
    public static long getUsedMemory() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / MEGABYTE;
    }

    // JVM剩余内存
    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory() / MEGABYTE;
    }

    // JVM内存使用率
    public static double getMemoryUsage() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        long totalPhysicalMemorySize = ((com.sun.management.OperatingSystemMXBean) osBean).getTotalPhysicalMemorySize();
        long freePhysicalMemorySize = ((com.sun.management.OperatingSystemMXBean) osBean).getFreePhysicalMemorySize();
        long usedPhysicalMemorySize = totalPhysicalMemorySize - freePhysicalMemorySize;
        double memoryUsage = (double) usedPhysicalMemorySize / (double) totalPhysicalMemorySize;
        return memoryUsage * 100;
    }

    // Jvm空闲率
    public static double getMemoryIdleRatio() {
        return 100 - getMemoryUsage();
    }

    // Jvm名称
    public static String getName() {
        return System.getProperty("java.vm.name");
    }

    // Jvm版本
    public static String getVersion() {
        return System.getProperty("java.vm.version");
    }

    // JvmUrl
    public static String getUrl() {
        return System.getProperty("java.vm.url");
    }

    // 获取当前JVM垃圾回收的大小
    public static long getGarbageCollectionSize() {
        long before = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.gc();
        long after = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        return before - after;
    }

    // 获取当前JVM垃圾回收的次数
    public static long getGarbageCollectionCount() {
        long gcCount = 0;
        for (GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
            gcCount += gc.getCollectionCount();
        }
        return gcCount;
    }

    /**
     * 获取所有JVM垃圾回收的信息
     *
     * PS Scavenge垃圾回收器
     *      主要用于清理年轻代(Young Generation)中的垃圾对象
     *      它采用“标记-复制”算法,将年轻代分为一个Eden区和两个Survivor区(通常是一个From区和一个To区)
     *      其中Eden区用于分配新对象,Survivor区用于存储年轻代中幸存的对象
     *      当Eden区满时,PS Scavenge垃圾回收器会将Eden区和Survivor区中存活的对象复制到To区
     *      并清空Eden区和From区,然后将From区和To区交换角色
     *      PS Scavenge垃圾回收器的特点是速度快,适用于多核CPU,但存活对象多时会导致频繁的垃圾回收,造成系统暂停.
     *
     * PS MarkSweep垃圾回收器
     *      主要用于清理老年代(Old Generation)中的垃圾对象
     *      它采用“标记-清除”算法,将老年代分为两个区域,一部分为已使用的对象区域,另一部分为未使用的对象区域
     *      当老年代中的对象区域满时,PS MarkSweep垃圾回收器会对老年代中的所有对象进行标记
     *      标记完成后,会清除未被标记的对象,并将已使用的对象区域和未使用的对象区域合并
     *      PS MarkSweep垃圾回收器的特点是清除效率高,但标记和清除的过程会导致系统暂停
     *      因此适用于对系统暂停时间要求不高的场景.
     * @return
     */
    public static HashMap<String, HashMap<String, Object>> getAllGarbageCollectorInfo () {
        val res = new HashMap<String, HashMap<String, Object>>();
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeans) {
            val name = garbageCollectorMXBean.getName();
            res.put(name, new HashMap<String, Object>(){{
                put("count", garbageCollectorMXBean.getCollectionCount());
                put("Time", garbageCollectorMXBean.getCollectionTime() + " ms");
            }});
        }
        return res;
    }
}
