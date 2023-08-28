package com.qing.erp.oss.entity;

import com.qing.erp.common.str.FormatUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
public class ObjectMetaDataEntity {
    // 实体标签
    private String etag;
    // 桶名称
    private String bucket;
    // 文件名称
    private String name;
    // 最后修改时间
    private LocalDateTime lastModified;
    // 对象的所有者
    private Owner owner;
    // 文件大小
    private long size;
    private String sizeString;
    // 储存类型
    private String storageClass;
    // 是否为最新版本
    private boolean isLatest;
    // 当前版本号
    private String versionId;
    // 用户元数据
    private Map<String, String> userMetadata;
    // 是否为目录
    private boolean isDir = false;
    // 编码方式
    private String encodingType = null;

    // 对象保留策略 TODO: 之后改成枚举
    // GOVERNANCE: 表示对象保留策略的治理模式, 即在指定保留期限内, 对象仍然可以被删除或修改, 但必须符合指定的保留策略规则。
    // COMPLIANCE: 表示对象保留策略的合规性模式, 即在指定保留期限内, 对象不能被删除或修改, 必须保留在存储桶中。
    private String retentionMode;

    // 对象保留期限（如果启用了对象保留策略）
    private LocalDateTime retentionRetainUntilDate;

    // 对象是否处于法律保留状态
    private boolean legalHold;

    // 对象是否标记已删除
    private boolean deleteMarker;

    // 过期时间
//    private LocalDateTime expirationTime;
    // 访问权限
//    private List<HashMap> acl;
    // 加密方式
//    private String encryptionMethod;
    // 元数据校验
//    private String metadataChecksum;
    // 内容校验
//    private String contentChecksum;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Owner {
        String id;
        String displayName;
    }

    public void setSize(long size) {
        this.size = size;
        this.setSizeString(FormatUtil.convertFileSizeToString(size));
    }
}


// 过期时间
// 访问权限