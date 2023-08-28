package com.qing.erp.system.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu")
public class MenuEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 自身ID
     */
    private Integer parentId;

    /**
     * 标题
     */
    private String title;

    /**
     * 图标
     */
    private String icon;

    /**
     * 组件路径(虚拟字段, 由java自动生成, URL+View.vue)
     */
    private String url;

    /**
     * 是否为重定向
     */
    private Boolean isRedirect;

    /**
     * 介绍
     */
    private String content;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 逻辑删除
     */
    private Boolean flag;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;

}
