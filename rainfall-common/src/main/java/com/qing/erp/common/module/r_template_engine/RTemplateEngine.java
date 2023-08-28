package com.qing.erp.common.module.r_template_engine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RTemplateEngine {


    public static String render(String template, Map<String, Object> data) {
        StringBuilder builder = new StringBuilder();
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
        Matcher matcher = pattern.matcher(template);
        int lastIndex = 0;
        while (matcher.find()) {
            builder.append(template, lastIndex, matcher.start());
            Object value = data.get(matcher.group(1));
            builder.append(value != null ? value.toString() : "");
            lastIndex = matcher.end();
        }
        builder.append(template, lastIndex, template.length());

        Pattern loopPattern = Pattern.compile("<#for\\s+(\\w+)\\s+in\\s+(\\w+)>((?:(?!</#for>).)*)</#for>");
        matcher = loopPattern.matcher(builder.toString());
        lastIndex = 0;
        while (matcher.find()) {
            builder.delete(matcher.start(), matcher.end());
            String loopVar = matcher.group(1);
            String loopDataName = matcher.group(2);
            List<?> loopData = (List<?>) data.get(loopDataName);
            if (loopData != null) {
                StringBuilder loopBuilder = new StringBuilder();
                for (Object item : loopData) {
                    String loopContent = matcher.group(2);
                    loopContent = loopContent.replaceAll("\\$\\{" + loopVar + "\\}", item.toString());
                    loopBuilder.append(loopContent);
                }
                builder.insert(matcher.start(), loopBuilder.toString());
                lastIndex = matcher.start() + loopBuilder.length();
            } else {
                lastIndex = matcher.start();
            }
        }

        Pattern listPattern = Pattern.compile("<#list\\s+(\\w+)\\s+as\\s+(\\w+)>((?:(?!</#list>).)*)</#list>");
        matcher = listPattern.matcher(builder.toString());
        lastIndex = 0;
        while (matcher.find()) {
            builder.delete(matcher.start(), matcher.end());
            String loopVar = matcher.group(2);
            String loopDataName = matcher.group(1);
            List<?> loopData = (List<?>) data.get(loopDataName);
            if (loopData != null) {
                StringBuilder loopBuilder = new StringBuilder();
                for (Object item : loopData) {
                    String loopContent = matcher.group(3);
                    loopContent = loopContent.replaceAll("\\$\\{" + loopVar + "\\}", item.toString());
                    loopBuilder.append(loopContent);
                }
                builder.insert(matcher.start(), "<ol>" + loopBuilder.toString() + "</ol>");
                lastIndex = matcher.start() + loopBuilder.length() + 9;
            } else {
                lastIndex = matcher.start();
            }
        }

        return builder.toString();
    }

    public static void main(String[] args) {
        String template = "<html>\n" +
                "  <head>\n" +
                "    <title>${title}</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <h1>${title}</h1>\n" +
                "    <#list items as item>\n" +
                "      <li>${item}</li>\n" +
                "    </#list>\n" +
                "  </body>\n" +
                "</html>";

        Map<String, Object> data = new HashMap<>();
        data.put("title", "My Page");
        data.put("items", Arrays.asList("Item 1", "Item 2", "Item 3"));

        String result = render(template, data);
        System.out.println(result);
    }
}
