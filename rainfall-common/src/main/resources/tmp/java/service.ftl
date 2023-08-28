package ${packagePath}.service;

<#list importList as import>
//${import}
</#list>
import com.qing.erp.common.data.R;
import ${packagePath}.entity.${className}Entity;

/**
* ${comments}
*
* @author ${author}
* @email ${email}
* @date ${datetime}
*/
public interface I${className}Service {
    // 增
    R add(${className}Entity entity);

    // 删
    R remove(Integer id);
    R removeList(Integer[] ids);

    // 改
    R update(${className}Entity entity);

    // 查
    R queryPage(Integer page, Integer show);
}
