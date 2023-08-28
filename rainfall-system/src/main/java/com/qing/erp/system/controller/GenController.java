package com.qing.erp.system.controller;

import com.qing.erp.common.data.R;
import com.qing.erp.common.module.db_gen.GenJpa;
import com.qing.erp.common.module.db_gen.pojo.GenData;
import com.qing.erp.common.module.db_gen.pojo.GenResult;
import com.qing.erp.common.module.mysql.MySQLClient;
import com.qing.erp.common.module.mysql.constant.DbType;
import com.qing.erp.common.module.mysql.pojo.Gen;
import com.qing.erp.common.str.StrUtil;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("gen")
@RestController
public class GenController {
    private static MySQLClient mc = null;

    public GenController() {
        mc = new MySQLClient("jdbc:mysql://localhost:3306/erp_system", "root", "zjr.1199");
    }

    @PreDestroy
    public void close() {
        mc.close();
    }

    // 获取数据库列表
    @GetMapping("/getDatabases")
    public R getDatabases() {
        return R.ok().dataAdd("list", mc.getDatabases());
    }

    // 获取表格列表
    @GetMapping("/getTableNames")
    public R getTableNames(String dbName) {
        return R.ok().dataAdd("list", mc.getTableNames(dbName));
    }

    // 获取表格代码
    @GetMapping("/getTableCode")
    public R getTableCode(
            @RequestParam(value="dbName") String dbName,
            @RequestParam(value="tableName") String tableName,
            @RequestParam(value="packagePath", required = false, defaultValue = "com.qing.erp.test") String packagePath,
            @RequestParam(value="comments", required = false, defaultValue = "暂无介绍") String comments,
            @RequestParam(value="author", required = false, defaultValue = "halfRain") String author,
            @RequestParam(value="email", required = false, defaultValue = "2042136767@qq.com") String email
    ) {
        //
        val gd = GenData.build(
                packagePath,
                dbName,
                tableName
        );
        // 获取表格元数据, 并转为Field类型
        val meta = mc.getColumnMetaData(
                dbName, tableName
        );
        val field = meta.stream().map(v -> {
            val f = new com.qing.erp.common.module.db_gen.pojo.GenData.Field();
            f.setName(v.getName());
            f.setType(v.getType());
            f.setRemarks(v.getRemarks());

            f.setJavaType(DbType.getJavaType(v.getType(), gd.getImportList()));
            f.setNameCamel(StrUtil.underscoreToCamel(v.getName()));
            f.setNameCamelCase(StrUtil.underscoreToCamelCase(v.getName()));
            return f;
        }).collect(Collectors.toList());

        // 数据去重
        gd.setImportList(gd.getImportList().stream().distinct().collect(Collectors.toList()));

        // 设置表格元数据
        gd.setFields(field);

        GenJpa gj = new GenJpa();

        val list = new ArrayList<Object>() {{
            add(gj.getEntity(gd));
            add(gj.getDao(gd));
            add(gj.getService(gd));
            add(gj.getServiceImpl(gd));
            add(gj.getController(gd));
            add(gj.getGateway(gd));
            add(gj.getVue(gd));
            add(gj.getVueApi(gd));
        }};
        return R.ok().dataAdd("list", list);
    }
    // 获取表格代码
    @GetMapping("/getTableAllCode")
    public R getTableAllCode(
            @RequestParam(value="dbName") String dbName,
            @RequestParam(value="packagePath", required = false, defaultValue = "com.qing.erp.test") String packagePath,
            @RequestParam(value="comments", required = false, defaultValue = "暂无介绍") String comments,
            @RequestParam(value="author", required = false, defaultValue = "halfRain") String author,
            @RequestParam(value="email", required = false, defaultValue = "2042136767@qq.com") String email
    ) {
        // 获取数据库的表
        List<String> tableNames = mc.getTableNames(dbName);

        // 提前准备好数据
        val map = new HashMap<String, List<GenResult>>();
        val mapKeys = Arrays.asList(
                "dao","entity", "service", "serviceImpl",
                "controller", "vue", "vueApi", "gateway"
        );
        mapKeys.forEach(v -> map.put(v, new ArrayList<GenResult>()));

        // 循环数据库的表
        for (String tableName : tableNames) {
            // ...
            val data = GenData.build(packagePath, comments, author, email, dbName, tableName);

            //
            val meta = mc.getColumnMetaData(dbName, tableName);

            //
            val field = meta.stream().map(v -> {
                val f = new com.qing.erp.common.module.db_gen.pojo.GenData.Field();
                f.setName(v.getName());
                f.setType(v.getType());
                f.setRemarks(v.getRemarks());

                f.setJavaType(DbType.getJavaType(v.getType(), data.getImportList()));
                f.setNameCamel(StrUtil.underscoreToCamel(v.getName()));
                f.setNameCamelCase(StrUtil.underscoreToCamelCase(v.getName()));
                return f;
            }).collect(Collectors.toList());

            // 数据去重
            data.setImportList(data.getImportList().stream().distinct().collect(Collectors.toList()));

            // 设置表格元数据
            data.setFields(field);

            val gj = new GenJpa();

            map.get("dao").add(gj.getDao(data));
            map.get("entity").add(gj.getEntity(data));

            map.get("service").add(gj.getService(data));
            map.get("serviceImpl").add(gj.getServiceImpl(data));

            map.get("controller").add(gj.getController(data));
            map.get("gateway").add(gj.getGateway(data));

            map.get("vue").add(gj.getVue(data));
            map.get("vueApi").add(gj.getVueApi(data));
        }

        return R.ok().dataAdd("map", map);
    }

    // 获取表格列表(raw)
    @GetMapping("/getTableMetaData")
    public R getTableMetaData(String dbName) {
        return R.ok().dataAdd("list", mc.getTableMetaData(dbName));
    }
}
