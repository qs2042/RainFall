package ${packagePath}.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import java.util.Arrays;

<#list importList as import>
//${import}
</#list>

import com.qing.erp.common.data.R;
import ${packagePath}.dao.${className}Dao;
import ${packagePath}.entity.${className}Entity;

/**
* ${comments}
*
* @author ${author}
* @email ${email}
* @date ${datetime}
*/
@Service
public class ${className}Impl implements I${className}Service {
    @Autowired
    private ${className}Dao dao;

    @Override
    public R add(${className}Entity entity) {
        entity.setId(null);
        dao.save(entity);
        return R.ok();
    }

    @Override
    public R remove(Integer id) {
        if (id == null) {
            return R.error();
        }
        dao.deleteById(id);
        return R.ok();
    }
    @Override
    public R removeList(Integer[] ids) {
        if (ids == null) {
            return R.error();
        }
        dao.deleteAllById(Arrays.asList(ids));
        return R.ok();
    }

    @Override
    public R update(${className}Entity entity) {
        if (entity.getId() == null) {
            return R.error();
        }
        dao.save(entity);
        return R.ok();
    }

    @Override
    public R queryPage(Integer page, Integer show) {
        if (page == null || show == null) {
            return R.error();
        }
        val r = dao.findAll(PageRequest.of(page, show));
        return R.ok().dataAdd("page", r);
    }
}