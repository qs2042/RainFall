package com.qing.erp.common.system;

import lombok.SneakyThrows;
import sun.net.dns.ResolverConfiguration;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.*;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;

public class NetworkInfo {
    // 网络所需
    //    private static final String NetworkDownloadSpeed = "http://example.com/file.zip";
    //    private static final String NetworkUploadSpeed = "http://example.com/upload";
    //    private static final String host = "http://example.com/";
    private static final String NetworkDownloadSpeed = "https://www.baidu.com/";
    private static final String NetworkUploadSpeed = "https://www.baidu.com/";
    private static final String host = "baidu.com";

    // TODO: 获取网络类型, MAC地址, IPv4地址, IPv4类型

    // 获取网络下载速度
    public static double getDownloadSpeed() {
        try {
            URL url = new URL(NetworkDownloadSpeed);
            URLConnection conn = url.openConnection();
            long startTime = System.currentTimeMillis();
            conn.connect();
            long endTime = System.currentTimeMillis();
            double timeElapsed = (endTime - startTime) / 1000.0;
            double fileSize = conn.getContentLengthLong() / 1024.0 / 1024.0;
            return fileSize / timeElapsed;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 获取网络上传速度
    public static double getUploadSpeed() {
        try {
            URL url = new URL(NetworkUploadSpeed);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStream out = conn.getOutputStream();
            byte[] data = new byte[1024 * 1024]; // 1 MB
            long startTime = System.currentTimeMillis();
            out.write(data);
            out.flush();
            out.close();
            long endTime = System.currentTimeMillis();
            double timeElapsed = (endTime - startTime) / 1000.0;
            return data.length / timeElapsed / 1024.0 / 1024.0;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 获取网络延迟
    public static double getLatency() {
        try {
            long startTime = System.currentTimeMillis();
            Socket socket = new Socket(host, 80);
            long endTime = System.currentTimeMillis();
            return (endTime - startTime) / 1000.0;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取本机的网络IP地址
     */
    @SneakyThrows
    public static String getIPAddresses() {
        InetAddress localhost = InetAddress.getLocalHost();
        return localhost.getHostAddress();
    }

    /**
     * 获取对方host的网络IP地址
     */
    public static InetAddress[] getIPAddresses(String host) {
        try {
            return InetAddress.getAllByName(host);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 获取网络网卡
    public static String[] getInterfaces() {
        String[] ifaces = new String[0];
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                if (ni.isUp() && !ni.isLoopback()) {
                    String name = ni.getName();
                    String displayName = ni.getDisplayName();
                    String hardwareAddress = Arrays.toString(ni.getHardwareAddress());
                    StringBuilder sb = new StringBuilder();
                    sb.append(name).append(" (").append(displayName).append("), ");
                    sb.append("MAC: ").append(hardwareAddress).append(", ");
                    for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
                        InetAddress address = ia.getAddress();
                        if (address != null) {
                            sb.append("IP: ").append(address.getHostAddress()).append("/");
                            sb.append(ia.getNetworkPrefixLength()).append(", ");
                        }
                    }
                    ifaces = Arrays.copyOf(ifaces, ifaces.length + 1);
                    ifaces[ifaces.length - 1] = sb.toString().trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ifaces;
    }

    // 获取LocalHost
    @SneakyThrows
    public static String getLocalHost() {
        InetAddress localAddress = InetAddress.getLocalHost();
        return localAddress.getHostAddress();
    }

    public static HashMap<String, Object> getAll() {
        return new HashMap<String, Object>() {{
//            put("DownloadSpeed", getDownloadSpeed());
//            put("UploadSpeed", getUploadSpeed());
//            put("Latency", getLatency());
//            put("IPAddresses", getIPAddresses());
//            put("Interfaces", Arrays.toString(getInterfaces()));
            put("LocalHost", getLocalHost());
        }};
    }
}
