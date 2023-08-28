package com.qing.erp.common.str;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StrUtil {
    // 字符串是否为空或者为 null
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    // 删除空格
    public static String removeSpaces(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return str.replaceAll("\\s+", "");
    }

    // 是否为数字
    public static boolean isNumeric(String str) {
        if (isNullOrEmpty(str)) {
            return false;
        }

        return str.matches("\\d+");
    }

    // 字符串到 CamelCase 风格
    public static String toCamelCase(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        String[] words = str.split("\\s+");
        for (String word : words) {
            sb.append(word.substring(0, 1).toUpperCase());
            sb.append(word.substring(1).toLowerCase());
        }

        return sb.toString();
    }

    // 字符串到 snake_case 风格
    public static String toSnakeCase(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }

        return str.replaceAll("([a-z0-9])([A-Z])", "$1_$2").toLowerCase();
    }

    // 下划线转小驼峰
    public static String underscoreToCamelCase(String str) {
        if (str == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean capitalizeNext = false;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '_') {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                sb.append(Character.toUpperCase(ch));
                capitalizeNext = false;
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    // 下划线转大驼峰
    public static String underscoreToCamel(String str) {
        return Arrays.stream(str.split("_"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(Collectors.joining());
    }

    // 第一个字母大写
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static void main(String[] args) {
        String str = "table_system_config_pro";
        System.out.println(underscoreToCamelCase(str));
        System.out.println(underscoreToCamel(str));
        System.out.println(capitalizeFirstLetter(str));
    }
}
