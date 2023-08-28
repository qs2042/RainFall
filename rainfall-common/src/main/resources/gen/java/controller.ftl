package ${packagePath}.controller;

// Spring类
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
// 项目类
import com.qing.erp.common.data.R;
import ${packagePath}.entity.${tableNameCamel}Entity;
import ${packagePath}.service.${tableNameCamel}Impl;
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
@RequestMapping("/${tableNameCamelCase}")
@RestController
public class ${tableNameCamel}Controller {
    @Autowired
    private ${tableNameCamel}Impl impl;

    @PostMapping("/add")
    public R add(${tableNameCamel}Entity entity) {
        return R.ok().dataAdd("map", impl.add(entity));
    }

    @PostMapping("/remove")
    public R remove(Integer id) {
        impl.remove(id);
        return R.ok();
    }

    @PostMapping("/removeList")
    public R removeList(Integer[] ids) {
        impl.removeList(ids);
        return R.ok();
    }

    @PostMapping("/update")
    public R update(${tableNameCamel}Entity entity) {
        return R.ok().dataAdd("map", impl.update(entity));
    }

    @GetMapping("/queryOne")
    public R queryOne(@RequestParam(value = "id") Integer id) {
        return R.ok().dataAdd("page", impl.queryOne(id));
    }

    @GetMapping("/queryPage")
    public R queryPage(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "show", required = false, defaultValue = "8") Integer show) {
        return R.ok().dataAdd("page", impl.queryPage(page, show));
    }
}