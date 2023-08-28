package com.qing.erp.common.module.docker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.qing.erp.common.module.docker.pojo.*;
import lombok.SneakyThrows;
import lombok.val;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DockerClient {
    private static final String DOCKER_HOST = "localhost";
    private static final int DOCKER_PORT = 2375;
    private final String baseUrl;

    public static DockerClient buildHttp(String host, int port) {
        return new DockerClient("http", host, port);
    }

    public static DockerClient buildHttps(String host, int port) {
        return new DockerClient("https", host, port);
    }

    public DockerClient() {
        this("http", DOCKER_HOST, DOCKER_PORT);
    }

    public DockerClient(String scheme, String host, int port) {
        this.baseUrl = scheme + "://" + host + ":" + port;
    }

    public List<ContainerEntity> containerList() {
        String apiUrl = this.baseUrl + "/containers/json";
        Gson gson = new Gson();
        Type listType = new TypeToken<List<ContainerEntity>>(){}.getType();
        return gson.fromJson(request(apiUrl), listType);
    }

    public String containerInfo(String containerId) {
        String apiUrl = this.baseUrl + "/containers/" + containerId + "/json";
        return request(apiUrl);
    }

    public void containerRemove(String containerId) {
        String apiUrl = this.baseUrl + "/containers/" + containerId;
        request(apiUrl, "DELETE");
    }

    public void containerCreate(HashMap<String, Object> map) {
        String apiUrl = String.format("%s/containers/create", this.baseUrl);
        request(apiUrl, "POST");
    }

    public void containerStart(String containerId) {
        String apiUrl = String.format("%s/containers/%s/start", this.baseUrl, containerId);
        request(apiUrl, "POST");
    }

    public void containerStop(String containerId) {
        String apiUrl = String.format("%s/containers/%s/stop", this.baseUrl, containerId);
        request(apiUrl, "POST");
    }

    public void containerKill(String containerId) {
        String apiUrl = String.format("%s/containers/%s/kill", this.baseUrl, containerId);
        request(apiUrl, "POST");
    }

    public void containerPause(String containerId) {
        String apiUrl = String.format("%s/containers/%s/pause", this.baseUrl, containerId);
        request(apiUrl, "POST");
    }

    /**
     * 容器(恢复暂停)
     */
    public void containerUnPause(String containerId) {
        String apiUrl = String.format("%s/containers/%s/unpause", this.baseUrl, containerId);
        request(apiUrl, "POST");
    }

    /**
     * 容器(日志)
     * <p>
     * TODO:
     */
    public void containerLogs(String containerId) {
        String apiUrl = String.format("%s/containers/%s/logs", this.baseUrl, containerId);
        request(apiUrl);
    }

    /**
     * 容器(状态)
     * <p>
     * 容器的资源使用情况
     */
    public void containerStats(String containerId) {
        String apiUrl = String.format("%s/containers/%s/stats", this.baseUrl, containerId);
        request(apiUrl);
    }

    /**
     * 容器(执行命令)
     * <p>
     * 在一个容器中执行一个命令
     * <p>
     * TODO: 需要传入一个 JSON 格式的参数,包含要在容器中执行的命令、执行命令的用户等信息
     */
    public void containerExec(String containerId) {
        String apiUrl = String.format("%s/containers/%s/exec", this.baseUrl, containerId);
        request(apiUrl, "POST");
    }

    public VersionEntity getVersion() {
        String apiUrl = String.format("%s/version", this.baseUrl);
        return new GsonBuilder()
                .create()
                .fromJson(request(apiUrl), VersionEntity.class);
    }

    public InfoEntity getInfo() {
        String apiUrl = String.format("%s/info", this.baseUrl);
        return new GsonBuilder()
                .create()
                .fromJson(request(apiUrl), InfoEntity.class);
    }

    public List<ImageEntity> imageList() {
        String apiUrl = String.format("%s/images/json", this.baseUrl);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<ImageEntity>>(){}.getType();
        return gson.fromJson(request(apiUrl), listType);
    }

    public void imageCreate() {
        String apiUrl = String.format("%s/images/create", this.baseUrl);
        request(apiUrl, "POST");
    }

    public void imageSearch() {
        String apiUrl = String.format("%s/images/search", this.baseUrl);
        request(apiUrl);
    }

    public void imagePush(String name) {
        String apiUrl = String.format("%s/images/%s/push", this.baseUrl, name);
        request(apiUrl, "POST");
    }

    public void imageTag(String name) {
        String apiUrl = String.format("%s/images/%s/tag", this.baseUrl, name);
        request(apiUrl, "POST");
    }

    public void imageRemove(String name) {
        String apiUrl = String.format("%s/images/%s/remove", this.baseUrl, name);
        request(apiUrl, "DELETE");
    }

    public void build() {
        String apiUrl = String.format("%s/build/", this.baseUrl);
        request(apiUrl, "POST");
    }


    /**
     * 网络(创建)
     * TODO: 需要传入一个 JSON 格式的参数,包含网络的配置信息,例如网络的名称、网络的驱动程序、网络的 IP 范围等.
     */
    public void networkCreate() {
        String apiUrl = String.format("%s/networks/create", this.baseUrl);
        request(apiUrl, "POST");
    }

    /**
     * 获取 Docker 中所有网络的列表
     * @return
     */
    public ArrayList<NetworkEntity> networkList() {
        String apiUrl = String.format("%s/networks/", this.baseUrl);
        Type networkEntityType = new TypeToken<ArrayList<NetworkEntity>>(){}.getType();
        return new GsonBuilder()
                .create()
                .fromJson(request(apiUrl), networkEntityType);
    }

    /**
     * 将容器连接到指定的网络
     * TODO: 需要传入一个 JSON 格式的参数,包含要连接的容器的名称、IP 地址、连接的别名等信息.
     */
    public void networkConnect(String containerId) {
        String apiUrl = String.format("%s/networks/%s/connect", this.baseUrl, containerId);
        request(apiUrl, "POST");
    }

    /**
     * 将一个容器从一个网络中断开连接
     * <p>
     * TODO: 需要传入一个 JSON 格式的参数,包含要断开连接的容器的名称或 ID 等信息.
     */
    public void networkDisconnect(String containerId) {
        String apiUrl = String.format("%s/networks/%s/disconnect", this.baseUrl, containerId);
        request(apiUrl, "POST");
    }


    /**
     * 创建一个新的卷
     * TODO:需要传入一个 JSON 格式的参数,包含卷的配置信息,例如卷的名称、卷的驱动程序、卷的标签等.
     */
    public void volumesCreate() {
        String apiUrl = String.format("%s/volumes/create", this.baseUrl);
        request(apiUrl, "POST");
    }

    /**
     * 获取一个卷的详细信息
     */
    public void volumesInfo(String name) {
        String apiUrl = String.format("%s/volumes/%s", this.baseUrl, name);
        request(apiUrl);
    }

    /**
     * 删除一个卷
     * TODO:可以传入一个查询参数 force=true,用于强制删除卷
     */
    public void volumesRemove(String name) {
        String apiUrl = String.format("%s/volumes/%s/remove", this.baseUrl, name);
        request(apiUrl, "DELETE");
    }


    private String request(String apiUrl) {
        return request(apiUrl, "GET");
    }

    @SneakyThrows
    private String request(String apiUrl, String method) {
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setReadTimeout(1000 * 3);
        con.setConnectTimeout(1000 * 3);
        con.setRequestMethod(method);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    public static void main(String[] args) {
        val dc = DockerClient.buildHttp("192.168.126.128", 2375);

        // container
        val containerList = dc.containerList();
        containerList.forEach(System.out::println);
        System.out.println();

        // image
        /*val imageList = dc.imageList();
        imageList.forEach(System.out::println);
        System.out.println();*/

        // network
        /*val networkList = dc.networkList();
        System.out.println(networkList.size());
        networkList.forEach(System.out::println);*/

        // info
        /*InfoEntity info = dc.getInfo();
        val plugins = info.getPlugins();
        val registryConfig = info.getRegistryConfig();
        val runtimes = info.getRuntimes();
        val swarm = info.getSwarm();
        val initCommit = info.getInitCommit();
        val runcCommit = info.getRuncCommit();
        val containerdCommit = info.getContainerdCommit();
        System.out.println(info);
        System.out.println(plugins);
        System.out.println(registryConfig);
        System.out.println(runtimes);
        System.out.println(swarm);
        System.out.println(initCommit);
        System.out.println(runcCommit);
        System.out.println(containerdCommit);*/

        // version
        /*val version = dc.getVersion();
        val platform = version.getPlatform();
        val components = version.getComponents();
        System.out.println(version);
        System.out.println(platform);
        System.out.println(components);
        System.out.println();*/
    }
}

