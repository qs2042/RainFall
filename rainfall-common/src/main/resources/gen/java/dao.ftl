package ${packagePath}.dao;

// Java类
import java.io.Serializable;
// Spring类
import org.springframework.data.jpa.repository.JpaRepository;
<#if importList?has_content>
// 动态类
</#if>
<#list importList as import>
${import}
</#list>
// 项目类
import ${packagePath}.entity.${tableNameCamel}Entity;

/**
* ${comments}
*
* @author ${author}
* @email ${email}
* @date ${datetime}
*/
public interface ${tableNameCamel}Dao extends JpaRepository<${tableNameCamel}Entity, Integer>, Serializable {
    // 增
    // 删
    // 改
    // 查
}
