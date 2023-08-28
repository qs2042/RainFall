package ${packagePath}.entity;

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
<#if importList?has_content>
// 动态类
</#if>
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
public class ${tableNameCamel}Entity {
    private static final long serialVersionUID = 1L;

    <#list fields as field>
    /**
    * ${field.remarks}
    */
    <#if field.name == "id">
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    </#if>
    @Column(name = "`${field.name}`")
    private ${field.javaType} ${field.nameCamelCase};

    </#list>
}