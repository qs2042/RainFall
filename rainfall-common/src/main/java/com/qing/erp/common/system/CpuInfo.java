package com.qing.erp.common.system;

import com.qing.erp.common.constant.Message;
import lombok.SneakyThrows;
import lombok.val;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// CPU信息查询字段
class WmicCpu {
    public static final String ADDRESS_WIDTH = "AddressWidth";                         // CPU地址位宽
    public static final String ARCHITECTURE = "Architecture";                           // CPU体系结构
    public static final String AVAILABILITY = "Availability";                           // CPU可用性
    public static final String CAPTION = "Caption";                                      // CPU简短描述
    public static final String CPU_STATUS = "CpuStatus";                                 // CPU状态
    public static final String CREATION_CLASS_NAME = "CreationClassName";                // 创建CPU实例的类名称
    public static final String CURRENT_CLOCK_SPEED = "CurrentClockSpeed";                // 当前时钟速度
    public static final String CURRENT_VOLTAGE = "CurrentVoltage";                       // 当前电压
    public static final String DATA_WIDTH = "DataWidth";                                  // CPU数据位宽
    public static final String DESCRIPTION = "Description";                              // CPU详细描述
    public static final String DEVICE_ID = "DeviceID";                                    // CPU设备ID
    public static final String FAMILY = "Family";                                         // CPU家族名称
    public static final String NAME = "Name";                                             // CPU名称
    public static final String MANUFACTURER = "Manufacturer";                             // CPU制造商
    public static final String NUMBER_OF_CORES = "NumberOfCores";                         // CPU核心数
    public static final String NUMBER_OF_LOGICAL_PROCESSORS = "NumberOfLogicalProcessors"; // CPU逻辑处理器数
    public static final String L2_CACHE_SIZE = "L2CacheSize";                             // L2缓存大小
    public static final String LEVEL = "Level";                                           // 缓存级别
    public static final String LOAD_PERCENTAGE = "LoadPercentage";                        // CPU负载百分比
    public static final String MAX_CLOCK_SPEED = "MaxClockSpeed";                          // 最大时钟速度
    public static final String SOCKET_DESIGNATION = "SocketDesignation";                  // 插座名称
    public static final String STATUS = "Status";                                          // CPU状态
    public static final String STATUS_INFO = "StatusInfo";                                 // CPU状态信息
    public static final String STEPPING = "Stepping";                                      // CPU步进号
    public static final String SYSTEM_CREATION_CLASS_NAME = "SystemCreationClassName";     // 创建系统实例的类名称
    public static final String SYSTEM_NAME = "SystemName";                                 // 所在系统名称
    public static final String UPGRADE_METHOD = "UpgradeMethod";                           // 升级方法
    public static final String VERSION = "Version";                                        // CPU版本
    public static final String ROLE = "Role";                                              // CPU角色
    public static final String POWER_MANAGEMENT_SUPPORTED = "PowerManagementSupported";    // 是否支持电源管理
    public static final String PROCESSOR_ID = "ProcessorId";                               // CPU唯一标识符
    public static final String PROCESSOR_TYPE = "ProcessorType";                           // CPU类型
    public static final String REVISION = "Revision";                                      // CPU修订号

    // 测试过有问题的
    final String NUMBER_OF_ENABLED_CORE = "NumberOfEnabledCore";                   // 启用的CPU核心数
    final String THREAD_COUNT = "ThreadCount";                                     // CPU线程数
    final String UNIQUE_ID = "UniqueId";                                           // CPU唯一ID
    final String VIRTUALIZATION_FIRMWARE_ENABLED = "VirtualizationFirmwareEnabled"; // 是否启用虚拟化固件
    final String VM_MONITOR_MODE_EXTENSIONS = "VMMonitorModeExtensions";           // VM监视器模式扩展
    final String CONFIG_MANAGER_ERROR_CODE = "ConfigManagerErrorCode";             // 配置管理器错误代码
    final String CONFIG_MANAGER_USER_CONFIG = "ConfigManagerUserConfig";           // 配置管理器用户配置
    final String ERROR_CLEARED = "ErrorCleared";                                   // 最后一次错误是否已清除
    final String ERROR_DESCRIPTION = "ErrorDescription";                           // 最后一个错误的详细描述
    final String INSTALL_DATE = "InstallDate";                                     // 安装日期和时间
    final String LAST_ERROR_CODE = "LastErrorCode";                                 // 最后一个错误代码
    final String OTHER_FAMILY_DESCRIPTION = "OtherFamilyDescription";               // 其他CPU家族描述
    final String PNP_DEVICE_ID = "PNPDeviceID";                                     // PnP设备ID
    final String SECOND_LEVEL_ADDRESS_TRANSLATION_EXTENSIONS = "SecondLevelAddressTranslationExtensions"; // 第二级地址转换扩展
    final String SERIAL_NUMBER = "SerialNumber";                                    // CPU序列号
    final String POWER_MANAGEMENT_CAPABILITIES = "PowerManagementCapabilities";     // 电源管理功能列表
    final String VOLTAGE_CAPS = "VoltageCaps";                                      // 电压容量

    public static final String command = "wmic cpu get ";

    public static String[] toArray(String... fields) {
        return fields;
    }

    // 将多个
    public static String joinWithComma(String... fields) {
        return String.join(",", fields);
    }

    /**
     * 废弃
     */
    public static Map<String, Object> getCpuInfo() {
        // "wmic cpu get name,manufacturer,currentclockspeed,NumberOfCores,NumberOfLogicalProcessors,L2CacheSize"
        String command = "wmic cpu get ";
        String[] fields = new String[]{
                "AddressWidth",                         // CPU地址位宽
                "Architecture",                         // CPU体系结构
                "Availability",                         // CPU可用性
                "Caption",                              // CPU简短描述
                "CpuStatus",                            // CPU状态
                "CreationClassName",                    // 创建CPU实例的类名称
                "CurrentClockSpeed",                    // 当前时钟速度
                "CurrentVoltage",                       // 当前电压
                "DataWidth",                            // CPU数据位宽
                "Description",                          // CPU详细描述
                "DeviceID",                             // CPU设备ID
                "Family",                               // CPU家族名称
                "Name",                                 // CPU名称
                "Manufacturer",                         // CPU制造商
                "NumberOfCores",                        // CPU核心数
                "NumberOfLogicalProcessors",            // CPU逻辑处理器数
                "L2CacheSize",                          // L2缓存大小
                "Level",                                // 缓存级别
                "LoadPercentage",                       // CPU负载百分比
                "MaxClockSpeed",                        // 最大时钟速度
                "SocketDesignation",                    // 插座名称
                "Status",                               // CPU状态
                "StatusInfo",                           // CPU状态信息
                "Stepping",                             // CPU步进号
                "SystemCreationClassName",              // 创建系统实例的类名称
                "SystemName",                           // 所在系统名称
                "UpgradeMethod",                        // 升级方法
                "Version",                              // CPU版本
                "Role",                                 // CPU角色
                "PowerManagementSupported",             // 是否支持电源管理
                "ProcessorId",                          // CPU唯一标识符
                "ProcessorType",                        // CPU类型
                "Revision",                             // CPU修订号
                /*
                // 不存在
                "NumberOfEnabledCore",                  // 启用的CPU核心数
                "ThreadCount",                          // CPU线程数
                "UniqueId",                             // CPU唯一ID
                "VirtualizationFirmwareEnabled",        // 是否启用虚拟化固件
                "VMMonitorModeExtensions",              // VM监视器模式扩展

                // NULL
                "ConfigManagerErrorCode",               // 配置管理器错误代码
                "ConfigManagerUserConfig",              // 配置管理器用户配置
                "ErrorCleared",                         // 最后一次错误是否已清除
                "ErrorDescription",                     // 最后一个错误的详细描述
                "InstallDate",                          // 安装日期和时间
                "LastErrorCode",                        // 最后一个错误代码
                "OtherFamilyDescription",               // 其他CPU家族描述
                "PNPDeviceID",                          // PnP设备ID
                "SecondLevelAddressTranslationExtensions",  // 第二级地址转换扩展
                "SerialNumber",                         // CPU序列号
                "PowerManagementCapabilities",          // 电源管理功能列表
                "VoltageCaps",                          // 电压容量

            */
        };
        command += String.join(",", fields);

        List<String> keys = null;
        List<String> values = null;
        try {
            // 执行系统命令
            ProcessBuilder builder = new ProcessBuilder("cmd", "/c", command);
            Process process = builder.start();

            // 读取命令输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("\n") || line.equals("")) {
                    continue;
                }
                // 使用正则表达式匹配行中的所有字段
                Pattern pattern = Pattern.compile("\\s{2,}");

                if (keys == null) {
                    keys = pattern.splitAsStream(line.trim()).collect(Collectors.toList());
                } else if (values == null){
                    values = pattern.splitAsStream(line.trim()).collect(Collectors.toList());
                } else {
                    System.out.println("多出来了信息, 可能会错乱");
                    return null;
                }
            }

            // 等待命令执行完成
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Command failed with exit code " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (keys != null && values != null) {
            return IntStream.range(0, keys.size())
                    .boxed()
                    .collect(Collectors.toMap(keys::get, values::get));
        }

        return null;
    }

    @SneakyThrows
    public static ArrayList<String> executeCommand(String command) {
        // 执行命令
        Process process = Runtime.getRuntime().exec(command);

        // 读取数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        ArrayList<String> result = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.equals("")) {
                continue;
            }
            result.add(line.trim());
        }
        if (result.size() != 2) {
            return null;
        }

        return result;
    }

    public static String executeCommandAndGetValue(String command) {
        val list = executeCommand(command);
        if (list == null) {
            return Message.UNKNOWN;
        }
        return list.get(1);
    }
}

public class CpuInfo {

    /**
     * 获取CPU核心(逻辑)
     */
    public static int getLogicalCore() {
        // osBean.getAvailableProcessors();
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 获取CPU核心(物理)
     */
    public static String getPhysicalCore() {
        int cpuCore = getLogicalCore();
        return cpuCore % 2 == 0 ? String.valueOf(cpuCore / 2) : Message.UNKNOWN;
    }

    /**
     * 获取CPU信息
     */
    public static String getModel() {
        String cpuModel = System.getenv("PROCESSOR_IDENTIFIER");
        return cpuModel == null ? Message.UNKNOWN : cpuModel;
    }

    /**
     * 获取CPU使用率
     */
    public static double getUsage() {
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        long[] threadIds = threadBean.getAllThreadIds();
        double totalCpuTime = 0;
        for (long threadId : threadIds) {
            long cpuTime = threadBean.getThreadCpuTime(threadId);
            if (cpuTime != -1) {
                totalCpuTime += cpuTime;
            }
        }
        double processCpuTime = threadBean.getCurrentThreadCpuTime();
        if (processCpuTime == 0) {
            return 0;
        }
        return totalCpuTime / (processCpuTime * threadIds.length);
    }

    /**
     * 获取CPU空闲率
     */
    public static double getIdleRatio() {
        return 100 - getUsage();
    }

    /**
     * 获取线程数量
     */
    public static int getThreadCount() {
        return Thread.activeCount();
    }

    /**
     * 获取守护线程数量
     */
    public static int getDaemonThreadsCount() {
        int count = 0;
        ThreadGroup rootGroup = Thread.currentThread().getThreadGroup().getParent();
        while (rootGroup.getParent() != null) {
            rootGroup = rootGroup.getParent();
        }
        Thread[] threads = new Thread[rootGroup.activeCount()];
        rootGroup.enumerate(threads);
        for (Thread thread : threads) {
            if (thread != null && thread.isDaemon()) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取CPU地址位宽
     */
    public static String getAddressWidth() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.ADDRESS_WIDTH);
    }

    /**
     * CPU体系结构
     */
    public static String getArchitecture() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.ARCHITECTURE);
    }

    /**
     * CPU可用性
     */
    public static String getAvailability() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.AVAILABILITY);
    }

    /**
     * CPU简短描述
     */
    public static String getCaption() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.CAPTION);
    }

    /**
     * 创建CPU实例的类名称
     */
    public static String getCreationClassName() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.CREATION_CLASS_NAME);
    }

    /**
     * 当前时钟速度
     */
    public static String getCurrentClockSpeed() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.CURRENT_CLOCK_SPEED);
    }

    /**
     * 当前电压
     */
    public static String getCurrentVoltage() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.CURRENT_VOLTAGE);
    }

    /**
     * CPU数据位宽
     */
    public static String getDataWidth() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.DATA_WIDTH);
    }

    /**
     * CPU详细描述
     */
    public static String getDescription() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.DESCRIPTION);
    }

    /**
     * CPU设备ID
     */
    public static String getDeviceId() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.DEVICE_ID);
    }

    /**
     * CPU家族名称
     */
    public static String getFamily() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.FAMILY);
    }

    /**
     * CPU名称
     */
    public static String getName() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.NAME);
    }

    /**
     * CPU制造商
     */
    public static String getManufacturer() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.MANUFACTURER);
    }

    /**
     * CPU核心数
     */
    public static String getNumberOfCores() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.NUMBER_OF_CORES);
    }

    /**
     * CPU逻辑处理器数
     */
    public static String getNumberOfLogicalProcessors() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.NUMBER_OF_LOGICAL_PROCESSORS);
    }

    /**
     * L2缓存大小
     */
    public static String getL2CacheSize() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.L2_CACHE_SIZE);
    }

    /**
     * 缓存级别
     */
    public static String getLevel() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.LEVEL);
    }

    /**
     * CPU负载百分比
     *
     * TODO: 会增加1.3s左右的时间, 有问题
     */
    public static String getLoadPercentage() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.LOAD_PERCENTAGE);
    }

    /**
     * 最大时钟速度
     */
    public static String getMaxClockSpeed() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.MAX_CLOCK_SPEED);
    }

    /**
     * 插座名称
     */
    public static String getSocketDesignation() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.SOCKET_DESIGNATION);
    }

    /**
     * CPU状态
     */
    public static String getStatus() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.STATUS);
    }

    /**
     * CPU状态信息
     */
    public static String getStatusInfo() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.STATUS_INFO);
    }

    /**
     * CPU步进号
     */
    public static String getStepping() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.STEPPING);
    }

    /**
     * 创建系统实例的类名称
     */
    public static String getSystemCreationClassName() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.SYSTEM_CREATION_CLASS_NAME);
    }

    /**
     * 所在系统名称
     */
    public static String getSystemName() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.SYSTEM_NAME);
    }

    /**
     * 升级方法
     */
    public static String getUpgradeMethod() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.UPGRADE_METHOD);
    }

    /**
     * CPU版本
     */
    public static String getVersion() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.VERSION);
    }

    /**
     * CPU角色
     */
    public static String getRole() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.ROLE);
    }

    /**
     * 是否支持电源管理
     */
    public static String getPowerManagementSupported() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.POWER_MANAGEMENT_SUPPORTED);
    }

    /**
     * CPU唯一标识符
     */
    public static String getProcessorId() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.PROCESSOR_ID);
    }

    /**
     * CPU类型
     */
    public static String getProcessorType() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.PROCESSOR_TYPE);
    }

    /**
     * CPU修订号
     */
    public static String getRevision() {
        return WmicCpu.executeCommandAndGetValue(WmicCpu.command + WmicCpu.REVISION);
    }

    /**
     * 获取所有
     */
    public static HashMap<String, Object> getAll() {
        return new HashMap<String, Object>() {{
            put("LogicalCore", getLogicalCore());
            put("PhysicalCore", getPhysicalCore());
            put("Model", getModel());
            put("Usage", String.format("%.2f%%", getUsage()));
            put("IdleRatio", String.format("%.2f%%", getIdleRatio()));
            put("ThreadCount", getThreadCount());
            put("DaemonThreadsCount", getDaemonThreadsCount());

            put("AddressWidth", getAddressWidth());
            put("CurrentClockSpeed", getCurrentClockSpeed());
            put("CurrentVoltage", getCurrentVoltage());
            put("DataWidth", getDataWidth());
            put("Name", getName());
            put("Manufacturer", getManufacturer());
            put("NumberOfCores", getNumberOfCores());
            put("NumberOfLogicalProcessors", getNumberOfLogicalProcessors());
            put("LoadPercentage", null); // getLoadPercentage()
            put("MaxClockSpeed", getMaxClockSpeed());
            put("SocketDesignation", getSocketDesignation());
//            put("Status", getStatus());
            put("PowerManagementSupported", getPowerManagementSupported());
//            put("ProcessorId", getProcessorId());

//            put("Description", getDescription());
//            put("StatusInfo", getStatusInfo());
//            put("Stepping", getStepping());
//            put("SystemCreationClassName", getSystemCreationClassName());
//            put("SystemName", getSystemName());
//            put("UpgradeMethod", getUpgradeMethod());
//            put("Version", getVersion());
//            put("Role", getRole());
            put("L2CacheSize", getL2CacheSize());
            put("Level", getLevel());
//            put("Architecture", getArchitecture());
//            put("Availability", getAvailability());
//            put("Caption", getCaption());
//            put("CreationClassName", getCreationClassName());
//            put("DeviceId", getDeviceId());
//            put("Family", getFamily());
//            put("ProcessorType", getProcessorType());
//            put("Revision", getRevision());
        }};
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        System.out.println(getAll());
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println(totalTime / 1000.0 + " s");
    }

}
