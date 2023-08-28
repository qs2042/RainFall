package ${packagePath}.service;

// Java类
import java.util.Arrays;
// 第三方类
import lombok.val;
<#if importList?has_content>
// 动态类
</#if>
<#list importList as import>
${import}
</#list>
// Spring类
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
// 项目类
import com.qing.erp.common.data.R;
import ${packagePath}.dao.${tableNameCamel}Dao;
import ${packagePath}.entity.${tableNameCamel}Entity;

/**
* ${comments}
*
* @author ${author}
* @email ${email}
* @date ${datetime}
*/
@Service
public class ${tableNameCamel}Impl implements I${tableNameCamel}Service {
    @Autowired
    private ${tableNameCamel}Dao dao;

    @Override
    public ${tableNameCamel}Entity add(${tableNameCamel}Entity entity) {
        entity.setId(null);
        return dao.save(entity);
    }

    @Override
    public void remove(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public void removeList(Integer[] ids) {
        dao.deleteAllById(Arrays.asList(ids));
    }

    @Override
    public ${tableNameCamel}Entity update(${tableNameCamel}Entity entity) {
        if (entity.getId() == null) {
            return null;
        }
        return dao.save(entity);
    }

    @Override
    public ${tableNameCamel}Entity queryOne(Integer id) {
        return id == null ? null : dao.findById(id).get();
    }

    @Override
    public Page<${tableNameCamel}Entity> queryPage(Integer page, Integer show) {
        if (page == null || show == null) {
            return null;
        }
        return dao.findAll(PageRequest.of(page, show));
    }
}