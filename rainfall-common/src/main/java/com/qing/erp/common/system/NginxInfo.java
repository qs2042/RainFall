package com.qing.erp.common.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NginxInfo {
    private static final Pattern CONNECTIONS_PATTERN = Pattern.compile("Active connections: (\\d+) ");
    private static final Pattern ACCEPTS_PATTERN = Pattern.compile("server accepts handled requests\\s+(\\d+)\\s+(\\d+)\\s+(\\d+) ");
    private static final Pattern STATUS_PATTERN = Pattern.compile("Reading: (\\d+) Writing: (\\d+) Waiting: (\\d+) ");

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NginxData {
        // 活动连接数
        private int activeConnections;
        // 服务器接受的连接数
        private int serverAccepts;
        // 服务器处理的连接数
        private int serverHandled;
        // 服务器请求的连接数
        private int serverRequests;
        // 读取状态
        private int reading;
        // 写入状态
        private int writing;
        // 等待状态
        private int waiting;

        // getter and setter methods...
    }

    /**
     * 提取nginx数据
     *
     * @param nginxData 包含nginx数据的字符串
     * @return 一个NginxData对象
     */
    public static NginxData parseNginxData(String nginxData) {
        NginxData data = new NginxData();
        data.setActiveConnections(extractActiveConnections(nginxData));
        int[] accepts = extractAccepts(nginxData);
        data.setServerAccepts(accepts[0]);
        data.setServerHandled(accepts[1]);
        data.setServerRequests(accepts[2]);
        int[] status = extractStatus(nginxData);
        data.setReading(status[0]);
        data.setWriting(status[1]);
        data.setWaiting(status[2]);
        return data;
    }

    private static int extractActiveConnections(String nginxData) {
        Matcher matcher = CONNECTIONS_PATTERN.matcher(nginxData);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    private static int[] extractAccepts(String nginxData) {
        Matcher matcher = ACCEPTS_PATTERN.matcher(nginxData);
        if (matcher.find()) {
            int[] accepts = new int[3];
            accepts[0] = Integer.parseInt(matcher.group(1));
            accepts[1] = Integer.parseInt(matcher.group(2));
            accepts[2] = Integer.parseInt(matcher.group(3));
            return accepts;
        }
        return new int[3];
    }

    private static int[] extractStatus(String nginxData) {
        Matcher matcher = STATUS_PATTERN.matcher(nginxData);
        if (matcher.find()) {
            int[] status = new int[3];
            status[0] = Integer.parseInt(matcher.group(1));
            status[1] = Integer.parseInt(matcher.group(2));
            status[2] = Integer.parseInt(matcher.group(3));
            return status;
        }
        return new int[3];
    }
    @SneakyThrows
    public static void main(String[] args) {
        String url = "http://localhost/nginx_status";
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String responseStr = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(responseStr);

        System.out.println(parseNginxData(responseStr));
    }



}
