package com.qing.erp.common.system;

import lombok.SneakyThrows;
import lombok.val;

import java.io.*;
import java.lang.management.*;
import java.net.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DeviceUtil {
    //
    private static final String UNKNOWN = "Unknown";
    //
    private static final RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();


    /**
     * 获取硬盘信息
     */
    public static ArrayList<HashMap<String, String>> getHDDInfo() {
        val res = new ArrayList<HashMap<String, String>>();
        for (File root : File.listRoots()) {
            long totalSpace = root.getTotalSpace();
            long usableSpace = root.getUsableSpace();
            long usedSpace = totalSpace - usableSpace;
            res.add(new HashMap<String, String>() {{
                put("key", root.getAbsolutePath());
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

    /**
     * 获取java供应商(vendor)
     */
    public static String getJavaVendor() {
        Map<String, String> systemProperties = runtimeBean.getSystemProperties();
        return systemProperties.get("java.vendor");
    }

    /**
     * 获取java版本(version)
     */
    public static String getJavaVersion() {
        Map<String, String> systemProperties = runtimeBean.getSystemProperties();
        return systemProperties.get("java.version");
    }

    /**
     * 获取java路径
     */
    public static String getJavaPath() {
        String res = System.getenv("JAVA_HOME");
        return res == null ? "Unknown" : res;
    }

    /**
     * 获取系统Path信息
     */
    public static String getSystemPath() {
        String res = System.getenv("path");
        return res == null ? "Unknown" : res;
    }

    /**
     * 获取系统信息
     */
    public static String getSystemOs() {
        String res = System.getenv("os");
        return res == null ? "Unknown" : res;
    }

    /**
     * 获取系统信息(名称)
     */
    public static String getSystemOsName() {
        // ManagementFactory.getOperatingSystemMXBean().getName();
        String res = System.getProperty("os.name");
        return res == null ? "Unknown" : res;
    }

    /**
     * 获取系统信息(版本)
     */
    public static String getSystemOsVersion() {
        // ManagementFactory.getOperatingSystemMXBean().getVersion();
        String res = System.getProperty("os.version");
        return res == null ? "Unknown" : res;
    }

    /**
     * 获取系统信息(架构)
     */
    public static String getSystemOsArch() {
        // ManagementFactory.getOperatingSystemMXBean().getArch();
        String res = System.getProperty("os.arch");
        return res == null ? "Unknown" : res;
    }

    /**
     * 获取系统用户名称
     */
    public static String getSystemUser() {
        String res = System.getenv("user");
        if (res == null) {
            res = System.getenv("USER");
        }
        if (res == null) {
            res = System.getenv("USERNAME");
        }
        return res == null ? "Unknown" : res;
    }

    /**
     * 获取系统用户国家/地区代码
     */
    public static String getSystemUserCountry() {
        return System.getProperty("user.country");
    }

    /**
     * 获取电脑名称
     */
    public static String getComputerName() {
        String res = System.getenv("COMPUTERNAME");
        if (res == null) {
            res = System.getenv("HOSTNAME");
        }
        return res == null ? "Unknown" : res;
    }

    /**
     * 获取设备的运行时间
     *
     * @return 运行时间,以毫秒为单位
     */
    public static long getComputerUptime() {
        return runtimeBean.getUptime();
    }

    /**
     * 获取设备的开启时间
     *
     * @return 开启时间,以毫秒为单位
     */
    public static long getComputerStartTime() {
        return runtimeBean.getStartTime();
    }

    /**
     * 获取设备的功耗
     *
     * @return 功耗,以瓦特为单位
     */
    @SneakyThrows
    public static double getComputerComputerPowerUsage() {
        Process p = Runtime.getRuntime().exec("powercfg /batteryreport");
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("Estimated")) {
                String[] parts = line.split(":");
                String value = parts[1].trim();
                return Double.parseDouble(value.substring(0, value.indexOf("W")));
            }
        }
        return -1;
    }

    /**
     * 获取BIOS版本
     */
    @SneakyThrows
    public static String getComputerBiosVersion() {
        Process p = Runtime.getRuntime().exec("wmic bios get smbiosbiosversion");
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.contains("SMBIOSBIOSVersion")) {
                return line.trim();
            }
        }
        return "";
    }

    /**
     * 获取ME版本
     */
    @SneakyThrows
    public static String getComputerMeVersion() {
        Process p = Runtime.getRuntime().exec("wmic csproduct get name");
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("ME Firmware")) {
                return line.substring(line.indexOf(":") + 1).trim();
            }
        }
        return "";
    }

    /**
     * 获取BMC版本
     */
    public static String getComputerBmcVersion(String bmcIp, String username, String password) throws IOException {
        Process p = Runtime.getRuntime().exec(String.format("ipmitool -I lanplus -H %s -U %s -P %s bmc info", bmcIp, username, password));
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("Firmware Revision")) {
                return line.substring(line.indexOf(":") + 1).trim();
            }
        }
        return "";
    }

    /**
     * 获取电源版本
     */
    public static String getComputerPowerSupplyVersion(String bmcIp, String username, String password) throws IOException {
        Process p = Runtime.getRuntime().exec(String.format("ipmitool -I lanplus -H %s -U %s -P %s power status", bmcIp, username, password));
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("Power Supply")) {
                return line.substring(line.indexOf(":") + 1).trim();
            }
        }
        return "";
    }

    /**
     * 获取CPLD版本
     */
    public static String getComputerCPLDVersion(String bmcIp, String username, String password) throws IOException {
        Process p = Runtime.getRuntime().exec(String.format("ipmitool -I lanplus -H %s -U %s -P %s sdr elist full", bmcIp, username, password));
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("CPLD")) {
                String[] parts = line.split("\\|");
                return parts[3].trim();
            }
        }
        return "";
    }

    // 获取设备警告信息

    // 获取项目启动时间
    public static String getProjectStartTime() {
        long startTime = runtimeBean.getStartTime();
        val localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(startTime), ZoneId.systemDefault()
        );
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    // 获取项目运行时间
    public static String getProjectUptime() {
        long milliseconds = runtimeBean.getUptime();
        if (milliseconds < 1000) {
            return milliseconds + " 毫秒";
        } else if (milliseconds < 60_000) {
            long seconds = milliseconds / 1000;
            return seconds + " 秒";
        } else if (milliseconds < 3_600_000) {
            long minutes = milliseconds / (60 * 1000);
            long seconds = (milliseconds % (60 * 1000)) / 1000;
            return minutes + " 分钟 " + seconds + " 秒";
        } else if (milliseconds < 86_400_000) {
            long hours = milliseconds / (60 * 60 * 1000);
            long minutes = (milliseconds % (60 * 60 * 1000)) / (60 * 1000);
            return hours + " 小时 " + minutes + " 分钟";
        } else {
            long days = milliseconds / (24 * 60 * 60 * 1000);
            long hours = (milliseconds % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
            return days + " 天 " + hours + " 小时";
        }
    }

    // 获取项目路径
    public static String getProjectPath() {
        return System.getProperty("user.dir");
    }

    // 获取项目运行参数
    public static String getProjectArguments() {
        String res = System.getenv("ARG");
        return res == null ? UNKNOWN : res;
    }

    /**
     * 获取项目的进程ID
     */
    public static long getPid() {
        String processName = runtimeBean.getName();
        return Long.parseLong(processName.split("@")[0]);
    }

    @SneakyThrows
    public static void test() {
        List<java.lang.management.GarbageCollectorMXBean> gcBeans =
                ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            String name = gcBean.getName();
            long count = gcBean.getCollectionCount();
            long time = gcBean.getCollectionTime();
            System.out.println("GC Name: " + name + ", Collection Count: " + count + ", Collection Time: " + time);
        }
        System.out.println();
    }


    public static void main(String[] args) {
//        System.out.println(getNetworkDownloadSpeed() + "MB/s");
//        System.out.println(getNetworkUploadSpeed() + "MB/s");
//        System.out.println(getNetworkLatency() + "s");
//        System.out.println(Arrays.toString(getNetworkIPAddresses()));
//        System.out.println(Arrays.toString(getNetworkDNSServers()));
//        System.out.println(Arrays.toString(getNetworkInterfaces()));
    }

}
