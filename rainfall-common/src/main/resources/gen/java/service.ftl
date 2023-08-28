package ${packagePath}.service;

// Java类
import java.util.List;
<#if importList?has_content>
// 动态类
</#if>
<#list importList as import>
${import}
</#list>
// Spring类
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import ${packagePath}.entity.${tableNameCamel}Entity;

/**
* ${comments}
*
* @author ${author}
* @email ${email}
* @date ${datetime}
*/
public interface I${tableNameCamel}Service {
    // 增
    ${tableNameCamel}Entity add(${tableNameCamel}Entity entity);
    // List<${tableNameCamel}Entity> addList(List<${tableNameCamel}Entity> entity);

    // 删
    void remove(Integer id);
    void removeList(Integer[] ids);

    // 改
    ${tableNameCamel}Entity update(${tableNameCamel}Entity entity);
    // List<${tableNameCamel}Entity> updateList(List<${tableNameCamel}Entity> entity);

    // 查
    ${tableNameCamel}Entity queryOne(Integer id);
    Page<${tableNameCamel}Entity> queryPage(Integer page, Integer show);
}
