package com.qing.erp.common.module.docker.pojo;

import com.qing.erp.common.module.docker.BaseJsonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity extends BaseJsonEntity {
    private long Containers;         // 容器数量,通常为-1
    private long Created;            // 镜像创建时间戳,以秒为单位
    private String Id;               // 镜像ID,通常为sha256算法的哈希值
    private Map<String, String> Labels; // 镜像的标签,键值对的集合
    private String ParentId;         // 镜像的父镜像ID
    private List<String> RepoDigests; // 镜像的摘要信息,可能包含多个值
    private List<String> RepoTags;   // 镜像的标签信息,可能包含多个值
    private long SharedSize;         // 共享大小
    private long Size;               // 镜像的大小
    private long VirtualSize;        // 虚拟大小
}
