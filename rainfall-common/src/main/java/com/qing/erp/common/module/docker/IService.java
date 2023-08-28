package com.qing.erp.common.module.docker;

import com.qing.erp.common.io.IOUtils;
import com.qing.erp.common.module.docker.pojo.*;
import lombok.SneakyThrows;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public interface IService<T> {
    @SneakyThrows
    static String get(String networkUrl) {
        URL url = new URL(networkUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setReadTimeout(1000 * 3);
        con.setConnectTimeout(1000 * 3);
        con.setRequestMethod("GET");
        return IOUtils.toText(con.getInputStream(), StandardCharsets.UTF_8);
    }

    @SneakyThrows
    static String post(String networkUrl, String params) {
        URL url = new URL(networkUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setReadTimeout(1000 * 3);
        con.setConnectTimeout(1000 * 3);
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        try (OutputStream outputStream = con.getOutputStream()) {
            // "param1=value1&param2=value2"
            byte[] input = params.getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        return IOUtils.toText(con.getInputStream(), StandardCharsets.UTF_8);
    }

    @SneakyThrows
    static String post(String networkUrl, File file) {
        URL url = new URL(networkUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setReadTimeout(1000 * 3);
        con.setConnectTimeout(1000 * 3);
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + getBoundary());

        try (OutputStream outputStream = con.getOutputStream();
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream))) {

            // 写入文件数据
            writer.append("--").append(getBoundary()).append("\r\n");
            writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n");
            writer.append("Content-Type: " + con.guessContentTypeFromName(file.getName()) + "\r\n");
            writer.append("\r\n");
            writer.flush();
        }

        return IOUtils.toText(con.getInputStream(), StandardCharsets.UTF_8);
    }

    @SneakyThrows
    static String post(String networkUrl, Object json) {
        URL url = new URL(networkUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setReadTimeout(1000 * 3);
        con.setConnectTimeout(1000 * 3);
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        con.setRequestProperty("Content-Type", "application/json");

        try (OutputStream outputStream = con.getOutputStream()) {
            byte[] input = "{\"param1\":\"value1\",\"param2\":\"value2\"}".getBytes(StandardCharsets.UTF_8);
            outputStream.write(input, 0, input.length);
        }

        return IOUtils.toText(con.getInputStream(), StandardCharsets.UTF_8);
    }

    static String getBoundary() {
        return "----Boundary" + System.currentTimeMillis() + UUID.randomUUID().toString();
    }


    // docker版本
    VersionEntity getVersion();

    // docker信息
    InfoEntity getInfo();

    // 获取所有镜像
    List<ImageEntity> getImages();

    // TODO: 创建镜像 /images/create?fromImage=[imageName]&tag=[tagName]
    ImageEntity createImage(String dockerFilePath, String imageName, String tagName);

    // 搜索 Docker Hub 的镜像 /images/search?term=[ImageName]&limit=[show]&filter=[]&starred=[]&official=[]
    List<ImageHubEntity> searchImage(String imageName);

    // TODO: 推送本地镜像到 Docker Hub /images/[ImageName]/push
    boolean pushImage(String dockerFilePath, String imageName, String tagName);

    // TODO: 设置本地镜像的标签 /images/[ImageName]/tag
    void setImageTag();

    // TODO: 删除本地镜像 /images/[ImageName]/remove (force=true,用于强制删除镜像
    boolean removeImage();

    // TODO: 从 Dockerfile 构建一个新的镜像 /build/
    void buildImage();

    // 获取所有容器
    List<ContainerEntity> getContainers();

    // 获取容器
    ContainerInfoEntity getContainer(String containerId);

    // TODO 删除容器 DELETE /containers/{containerId}
    boolean removeContainer(String containerId);

    // TODO 创建容器 POST containers/create 需要传入一个 JSON 格式的参数,包含容器的配置信息,例如容器镜像、容器名称、容器的环境变量、容器的端口映射等.
    ContainerEntity createContainer();

    // TODO 启动容器 POST containers/{containerId}/start
    boolean startContainer(String containerId);

    // TODO 停止容器 POST containers/{containerId}/stop
    boolean stopContainer(String containerId);

    // TODO 强制终止容器  POST containers/{containerId}/kill
    boolean killContainer(String containerId);

    // TODO 暂停容器  POST containers/{containerId}/pause
    boolean pauseContainer(String containerId);

    // TODO 容器恢复暂停  POST containers/{containerId}/unpause
    boolean unPauseContainer(String containerId);

    // TODO 容器状态  POST containers/{containerId}/status
    void getContainerStatus(String containerId);

    // TODO 容器日志  POST containers/{containerId}/logs  传入一些查询参数,如 stdout、stderr、since、until 等,用于指定获取日志的类型和时间范围等信息.
    void getContainerLog(String containerId);

    // 容器内部执行命令  POST containers/{containerId}/exec
    CommandExecutionEntity executeCommandWithinContainer(String containerId, String command);
}
