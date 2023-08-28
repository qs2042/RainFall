package com.qing.erp.member.entity;

// lombok
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
// jpa
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;

/**
* 这是自动生成的类
*
* @author halfRain
* @email 2042136767@qq.com
* @date Thu Aug 03 16:55:00 CST 2023
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organization")
public class OrganizationEntity {
    private static final long serialVersionUID = 1L;

    /**
    * ID
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    private Integer id;

    /**
    * UUID
    */
    @Column(name = "`uuid`")
    private String uuid;

    /**
    * 名称
    */
    @Column(name = "`name`")
    private String name;

    /**
    * 信息
    */
    @Column(name = "`info`")
    private String info;

    /**
    * 备注
    */
    @Column(name = "`notes`")
    private String notes;

    /**
    * 上级机构
    */
    @Column(name = "`parent_id`")
    private Integer parentId;

}