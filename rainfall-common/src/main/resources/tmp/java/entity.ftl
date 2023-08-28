package ${packagePath}.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

// TODO: 如果字段名出现mysql关键词(例如key), 需要使用反引号将列名括起来, 所以这里需要传递进来数据库的列名称
//   案例: @Column(name = "`key`")
// import javax.persistence.Column;

<#list importList as import>
${import}
</#list>

/**
* ${comments}
*
* @author ${author}
* @email ${email}
* @date ${datetime}
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "${tableName}")
public class ${className}Entity {
    private static final long serialVersionUID = 1L;

    <#list fields as field>
    /**
    * ${field.remarks}
    */
    <#if field.name == "id">
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    </#if>
    private ${field.type} ${field.name};

    </#list>
}