package com.qing.erp.system.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;



import java.util.Date;

/**
 * 这是自动生成的类
 *
 * @author halfRain
 * @email 2042136767@qq.com
 * @date Mon Jul 17 15:52:35 CST 2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dict")
public class DictEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 代码(唯一)
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

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