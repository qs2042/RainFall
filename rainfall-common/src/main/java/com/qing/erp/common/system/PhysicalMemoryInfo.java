package com.qing.erp.common.system;

import lombok.val;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.HashMap;

public class PhysicalMemoryInfo {
    private static final long BYTES_PER_MB = 1024 * 1024;

    private static final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
    private static final RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();

    /**
     * 获取物理内存(总大小)
     */
    public static long getTotalSize() {
        return ((com.sun.management.OperatingSystemMXBean) osBean).getTotalPhysicalMemorySize() / BYTES_PER_MB;
    }

    /**
     * 获取物理内存(剩余)
     */
    public static long getFreeSize() {
        return ((com.sun.management.OperatingSystemMXBean) osBean).getFreePhysicalMemorySize() / BYTES_PER_MB;
    }

    /**
     * 获取物理内存(已用)
     */
    public static long getUsedSize() {
        long totalPhysicalMemorySize = ((com.sun.management.OperatingSystemMXBean) osBean).getTotalPhysicalMemorySize();
        long freePhysicalMemorySize = ((com.sun.management.OperatingSystemMXBean) osBean).getFreePhysicalMemorySize();
        return (totalPhysicalMemorySize - freePhysicalMemorySize) / BYTES_PER_MB;
    }

    /**
     * 获取物理内存(使用率)
     */
    public static double getUsage() {
        long totalPhysicalMemorySize = ((com.sun.management.OperatingSystemMXBean) osBean).getTotalPhysicalMemorySize();
        long freePhysicalMemorySize = ((com.sun.management.OperatingSystemMXBean) osBean).getFreePhysicalMemorySize();
        long usedPhysicalMemorySize = totalPhysicalMemorySize - freePhysicalMemorySize;
        double memoryUsage = (double) usedPhysicalMemorySize / (double) totalPhysicalMemorySize;
        return memoryUsage * 100;
    }

    /**
     * 获取物理内存(空闲率)
     */
    public static double getIdleRatio() {
        return 100 - getUsage();
    }

    /**
     * 获取硬盘信息
     */
    public static HashMap<String, HashMap<String, String>> getHDDInfo() {
        val res = new HashMap<String, HashMap<String, String>>();
        for (File root : File.listRoots()) {
            long totalSpace = root.getTotalSpace();
            long usableSpace = root.getUsableSpace();
            long usedSpace = totalSpace - usableSpace;

            res.put(root.getAbsolutePath(), new HashMap<String, String>() {{
                put("totalSpace", formatSize(totalSpace));
                put("usableSpace", formatSize(usableSpace));
                put("usedSpace", formatSize(usedSpace));
            }});
        }
        return res;
    }
    private static String formatSize(long size) {
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int index = 0;
        double value = size;
        while (value >= 1024 && index < units.length - 1) {
            value /= 1024;
            index++;
        }
        return String.format("%.2f %s", value, units[index]);
    }

    public static HashMap<String, Object> getAll() {
        return new HashMap<String, Object>(){{
            put("TotalSize", getTotalSize());
            put("FreeSize", getFreeSize());
            put("UsedSize", getUsedSize());
            put("Usage", String.format("%.2f%%", getUsage()));
            put("IdleRatio", String.format("%.2f%%", getIdleRatio()));
        }};
    }

}
