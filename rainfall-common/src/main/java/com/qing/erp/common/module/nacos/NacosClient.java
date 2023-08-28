package com.qing.erp.common.module.nacos;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qing.erp.common.module.docker.pojo.ContainerEntity;
import com.qing.erp.common.module.nacos.pojo.InstanceEntity;
import com.qing.erp.common.module.nacos.pojo.ServiceEntity;
import lombok.SneakyThrows;
import lombok.val;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class NacosClient {
    private String url;

    public static NacosClient build(String addr) {
        val nacosClient = new NacosClient();

        addr = !addr.startsWith("http://") ? "http://" + addr : addr;
        addr = !addr.endsWith("/") ? addr + "/" : addr;

        nacosClient.url = addr;
        return nacosClient;
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

    public InstanceEntity getInstanceList(String serviceName) {
        val r = get(url + "nacos/v1/ns/instance/list?serviceName=" + serviceName);
        return new Gson().fromJson(r, InstanceEntity.class);
    }

    public ServiceEntity getServiceList() {
        val r = get(url + "nacos/v1/ns/catalog/services?pageNo=1&pageSize=" + Integer.MAX_VALUE);
        return new Gson().fromJson(r, ServiceEntity.class);
    }

    public static void main(String[] args) {
        val nc = NacosClient.build("localhost:8848");
        System.out.println(nc.getInstanceList("erp-system"));
        System.out.println(nc.getServiceList());
    }
}

