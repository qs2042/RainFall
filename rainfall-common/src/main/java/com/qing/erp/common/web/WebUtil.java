package com.qing.erp.common.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WebUtil {

    public static void main(String[] args) {
//        System.out.println(requestParamsToMap("a=1&a=2&b=1"));

        String pattern = "/onLine/{account}/{id}/{age}/{hobby}";
        String string = "/onLine/admin/1/18/mc";

        Map<String, String> result = extractPathVariables(pattern, string);
        System.out.println(result); // 输出: {account=admin, id=1}
    }

    public static Map<String, String> extractPathVariables(String pattern, String string) {
        // 将路径变量部分替换为正则表达式模式, 并编译为Pattern对象, 最后执行匹配操作
        String regexPattern = pattern.replaceAll("\\{([^/]+)}", "([^/]+)");
        Pattern compiledPattern = Pattern.compile(regexPattern);
        Matcher matcher = compiledPattern.matcher(string);

        Map<String, String> variables = new HashMap<>();
        if (matcher.matches()) {
            Pattern variablePattern = Pattern.compile("\\{([^/]+)}");
            Matcher variableMatcher = variablePattern.matcher(pattern);

            int groupCount = matcher.groupCount();
            int groupIndex = 1; // 从1开始计数
            while (variableMatcher.find()) {
                if (groupIndex <= groupCount) {
                    String variableName = variableMatcher.group(1);
                    String variableValue = matcher.group(groupIndex);
                    variables.put(variableName, variableValue);
                    groupIndex++;
                }
            }
        }

        return variables;
    }

    public static Map<String, List<String>> requestParamsToMap(String params) {
        return Arrays.stream(params.split("&"))
                .map(pair -> pair.split("="))
                .collect(Collectors.groupingBy(
                        pair -> pair[0],
                        Collectors.mapping(pair -> pair[1], Collectors.toList())
                ));
    }

}
