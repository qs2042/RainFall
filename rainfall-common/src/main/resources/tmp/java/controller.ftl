package ${packagePath}.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

import ${packagePath}.entity.${className}Entity;
import ${packagePath}.service.${className}Impl;

<#list importList as import>
//${import}
</#list>

import com.qing.erp.common.data.R;

/**
* ${comments}
*
* @author ${author}
* @email ${email}
* @date ${datetime}
*/
@RequestMapping("/${camelCase}")
@RestController
public class ${className}Controller {
    @Autowired
    private ${className}Impl impl;

    @PostMapping("/add")
    public R add(${className}Entity entity) {
        return impl.add(entity);
    }

    @PostMapping("/remove")
    public R remove(Integer id) {
        return impl.remove(id);
    }

    @PostMapping("/removeList")
    public R removeList(Integer[] ids) {
        return impl.removeList(ids);
    }

    @PostMapping("/update")
    public R update(${className}Entity entity) {
        return impl.update(entity);
    }

    @GetMapping("/queryPage")
    public R queryPage(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "show", required = false, defaultValue = "8") Integer show) {
        return impl.queryPage(page, show);
    }
}