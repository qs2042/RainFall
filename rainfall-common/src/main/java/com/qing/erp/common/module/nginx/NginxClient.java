package com.qing.erp.common.module.nginx;

import com.qing.erp.common.module.nginx.pojo.NginxAgentData;
import com.qing.erp.common.module.nginx.pojo.NginxLogData;
import com.qing.erp.common.module.nginx.pojo.NginxRequestData;
import lombok.SneakyThrows;
import lombok.val;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NginxClient {
    private static final String NGINX_LOG_URL = "http://localhost/nginx_logs/access_root.log";
    private static final Pattern NGINX_LOG_PATTERN = Pattern.compile("^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"");

    private static NginxRequestData parseNginxRequest(String requestStr) {
        String[] parts = requestStr.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid Nginx request format: " + requestStr);
        }
        return new NginxRequestData(parts[0], parts[1], parts[2]);
    }

    private static NginxAgentData parseNginxAgent(String agentStr) {
        String osName = null;
        String osVersion = null;
        String browserName = null;
        String browserVersion = null;

        // 匹配操作系统信息
        Pattern osPattern = Pattern.compile("Windows NT (\\d+\\.\\d+)");
        Matcher osMatcher = osPattern.matcher(agentStr);
        if (osMatcher.find()) {
            osName = "Windows";
            osVersion = osMatcher.group(1);
        }

        // 匹配浏览器信息
        Pattern browserPattern = Pattern.compile("(Chrome|Firefox|Safari)/(\\d+\\.\\d+)");
        Matcher browserMatcher = browserPattern.matcher(agentStr);
        if (browserMatcher.find()) {
            browserName = browserMatcher.group(1);
            browserVersion = browserMatcher.group(2);
        }

        return new NginxAgentData(osName, osVersion, browserName, browserVersion);
    }

    @SneakyThrows
    private static String get(String url) {
        URL u = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            return null;
        }
        val sb = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            sb.append(inputLine).append("\n");
        }
        in.close();
        return sb.toString();
    }

    @SneakyThrows
    public static ArrayList<NginxLogData> getList(String url) {
        val list = new ArrayList<NginxLogData>();

        String text = get(url);
        if (text == null) {
            return list;
        }

        for (String line : text.split("\n")) {
            // 解析日志数据
            Matcher matcher = NGINX_LOG_PATTERN.matcher(line);
            if (matcher.matches()) {
                //
                String ip = matcher.group(1);

                // 第二个 "-" 是用于分割的, 所以取第二个 "-"
                String user = matcher.group(3);
                if ("-".equals(user)) {
                    user = null;
                }

                String date = matcher.group(4);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
                LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

                String request = matcher.group(5);
                val requestBean = parseNginxRequest(request);

                String status = matcher.group(6);
                String bytes = matcher.group(7);

                String referer = matcher.group(8);
                if ("-".equals(referer)) {
                    user = null;
                }

                String agent = matcher.group(9);
                val agentBean = parseNginxAgent(agent);

                NginxLogData logData = new NginxLogData(ip, user, dateTime, requestBean, status, bytes, referer, agentBean);
                list.add(logData);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        getList(NGINX_LOG_URL).forEach(System.out::println);
    }
}
