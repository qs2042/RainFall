package com.qing.erp.common.module.docker.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageHubEntity {
    /**
     * Docker 镜像的星级数目
     */
    private int star_count;

    /**
     * Docker 镜像是否官方构建
     */
    private boolean is_official;

    /**
     * Docker 镜像名称
     */
    private String name;

    /**
     * Docker 镜像是否自动化构建
     */
    private boolean is_automated;

    /**
     * Docker 镜像描述
     */
    private String description;
}
