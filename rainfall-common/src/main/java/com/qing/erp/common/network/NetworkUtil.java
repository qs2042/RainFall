package com.qing.erp.common.network;

import lombok.val;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLDecoder;

public class NetworkUtil {
    // 将URLDecoder编码转成UTF8
    public static String urlDecoder2Utf8(String str) throws UnsupportedEncodingException {
        String url = str.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        return URLDecoder.decode(url, "UTF-8");
    }

    // 是否可以正常访问
    public static boolean isUrlReachable(String urlString) {
        try {
            val url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (Exception e) {
            return false;
        }
    }

    // 是否能 ping 通对方主机
    public static boolean isHostReachable(String host) {
        try {
            InetAddress address = InetAddress.getByName(host);
            return address.isReachable(5000); // 超时时间为 5000 毫秒
        } catch (Exception e) {
            return false;
        }
    }

    // 获取Host
    public static String extractHostFromUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            return url.getHost();
        } catch (Exception e) {
            // 处理 URL 解析错误
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(isUrlReachable("https://www.bilibili.com/"));
        System.out.println(isUrlReachable("http://192.168.126.128:9000"));
        System.out.println(isUrlReachable("www.qingshou2042.com"));

        System.out.println(isHostReachable("baidu.com"));
        System.out.println(isUrlReachable("192.168.126.128"));
        System.out.println(isHostReachable("qingshou2042.com"));
    }
}
