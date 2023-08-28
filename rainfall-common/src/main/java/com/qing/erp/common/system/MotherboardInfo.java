package com.qing.erp.common.system;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MotherboardInfo {
    // 主板序列化
    private static final String CMD_BASEBOARD_SERIAL = "wmic baseboard get serialnumber";
    // 主板制造商
    private static final String CMD_BASEBOARD_MANUFACTURER = "wmic baseboard get manufacturer";
    // 主板产品名称
    private static final String CMD_BASEBOARD_PRODUCT = "wmic baseboard get product";
    // 主板芯片组
    private static final String CMD_CHIPSET = "wmic path Win32_PnPEntity where \"Caption like '%Chipset%'\" get Caption";
    // LPC IO
    private static final String CMD_LPCIO = "wmic path Win32_PnPEntity where \"Caption like '%LPC%'\" get Caption";
    // 显卡控制器
    private static final String CMD_GRAPHICS_CONTROLLER = "wmic path Win32_VideoController get VideoProcessor, VideoMemoryType, AdapterRAM";

    public static Map<String, String> getMotherboardInfo() {
        Map<String, String> motherboardInfo = new HashMap<>();

        motherboardInfo.put("Serial Number", executeCommand(CMD_BASEBOARD_SERIAL));
        motherboardInfo.put("Manufacturer", executeCommand(CMD_BASEBOARD_MANUFACTURER));
        motherboardInfo.put("Product", executeCommand(CMD_BASEBOARD_PRODUCT));
        motherboardInfo.put("CHIPSET", executeCommand(CMD_CHIPSET));
        motherboardInfo.put("LPCIO", executeCommand(CMD_LPCIO));
        motherboardInfo.put("GRAPHICS_CONTROLLER", executeCommand(CMD_GRAPHICS_CONTROLLER));
        return motherboardInfo;
    }

    @SneakyThrows
    public static String executeCommand(String command) {
        // 执行命令
        Process process = Runtime.getRuntime().exec(command);

        // 读取数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            // 去除前后空白
            line = line.trim();
            if (!line.isEmpty()) {
                output.append(line);
            }
        }

        return output.toString();
    }

    public static void main(String[] args) {
        /*Map<String, String> motherboardInfo = MotherboardInfo.getMotherboardInfo();
        for (Map.Entry<String, String> entry : motherboardInfo.entrySet()) {
            System.out.println(entry.toString());
        }*/

    }
}
