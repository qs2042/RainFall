package com.qing.erp.common.module.docker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.qing.erp.common.module.docker.pojo.*;
import lombok.AllArgsConstructor;
import lombok.val;

import java.lang.reflect.Type;
import java.util.List;

@AllArgsConstructor
public class DockerClientPro implements IService<DockerClientPro> {
    private final String host;
    private final int port;
    private final String baseUrl;

    public DockerClientPro(String scheme, String host, int port) {
        this.host = host;
        this.port = port;
        this.baseUrl = String.format("%s://%s:%s", scheme, host, port);
    }

    public static DockerClientPro buildHttp() {
        return new DockerClientPro("http", "localhost", 2375);
    }

    public static DockerClientPro buildHttp(String host, int port) {
        return new DockerClientPro("http", host, port);
    }

    public static DockerClientPro buildHttps(String host, int port) {
        return new DockerClientPro("https", host, port);
    }

    @Override
    public VersionEntity getVersion() {
        val url = String.join("/", baseUrl, "version");
        val res = IService.get(url);
        return new GsonBuilder().create()
                .fromJson(res, VersionEntity.class);
    }

    @Override
    public InfoEntity getInfo() {
        val url = String.join("/", baseUrl, "info");
        val res = IService.get(url);
        return new GsonBuilder().create().fromJson(res, InfoEntity.class);
    }

    @Override
    public List<ImageEntity> getImages() {
        val url = String.join("/", baseUrl, "images/json");
        val res = IService.get(url);
        Type listType = new TypeToken<List<ImageEntity>>(){}.getType();
        return new GsonBuilder().create().fromJson(res, listType);
    }

    @Override
    public ImageEntity createImage(String dockerFilePath, String imageName, String tagName) {
        return null;
    }

    @Override
    public List<ImageHubEntity> searchImage(String imageName) {
        val url = String.join("/", baseUrl, "images/search?term=" + imageName);
        val res = IService.get(url);
        Type listType = new TypeToken<List<ImageHubEntity>>(){}.getType();
        return new GsonBuilder().create().fromJson(res, listType);

    }

    @Override
    public boolean pushImage(String dockerFilePath, String imageName, String tagName) {
        return false;
    }

    @Override
    public void setImageTag() {

    }

    @Override
    public boolean removeImage() {
        return false;
    }

    @Override
    public void buildImage() {

    }

    @Override
    public List<ContainerEntity> getContainers() {
        val url = String.join("/", baseUrl, "containers/json");
        val res = IService.get(url);
        Type listType = new TypeToken<List<ContainerEntity>>(){}.getType();
        return new Gson().fromJson(res, listType);
    }

    @Override
    public ContainerInfoEntity getContainer(String containerId) {
        val url = String.join("/", baseUrl, "containers", containerId, "json");
        val res = IService.get(url);
        return new GsonBuilder()
                .create()
                .fromJson(res, ContainerInfoEntity.class);
    }

    @Override
    public boolean removeContainer(String containerId) {
        return false;
    }

    @Override
    public ContainerEntity createContainer() {
        return null;
    }

    @Override
    public boolean startContainer(String containerId) {
        return false;
    }

    @Override
    public boolean stopContainer(String containerId) {
        return false;
    }

    @Override
    public boolean killContainer(String containerId) {
        return false;
    }

    @Override
    public boolean pauseContainer(String containerId) {
        return false;
    }

    @Override
    public boolean unPauseContainer(String containerId) {
        return false;
    }

    @Override
    public void getContainerStatus(String containerId) {

    }

    @Override
    public void getContainerLog(String containerId) {

    }

    @Override
    public CommandExecutionEntity executeCommandWithinContainer(String containerId, String command) {
        return null;
    }
}
