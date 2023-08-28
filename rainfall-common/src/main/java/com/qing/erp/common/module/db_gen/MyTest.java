package com.qing.erp.common.module.db_gen;

import com.qing.erp.common.json.JsonUtil;
import com.qing.erp.common.module.db_gen.pojo.GenData;
import com.qing.erp.common.module.mysql.MySQLClient;
import com.qing.erp.common.module.mysql.constant.DbType;
import com.qing.erp.common.str.StrUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import lombok.val;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MyTest {
    private static Configuration config = null;
    private static MySQLClient mc = null;

    static {
        config = new Configuration(Configuration.VERSION_2_3_31);
        config.setClassForTemplateLoading(GenJpa.class, "/gen");
        mc = new MySQLClient("jdbc:mysql://localhost:3306/erp_system", "root", "zjr.1199");
    }

    @SneakyThrows
    public static String gen(GenData gd, String filePath) {
        Template template = config.getTemplate(filePath);
        StringWriter writer = new StringWriter();
        template.process(gd, writer);
        return writer.toString();
    }

    @SneakyThrows
    public static void main(String[] args) {
        val gd = GenData.build(
                "com.qing.erp.system",
                "erp_system",
                "friend_link"
        );

        // 获取表格元数据, 并转为Field类型
        val meta = mc.getColumnMetaData("erp_system", "friend_link");
        val field = meta.stream().map(v -> {
            val f = new GenData.Field();
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

        val gj = new GenJpa();

        val serviceImpl = gj.getService(gd);
        val service = gj.getServiceImpl(gd);
        val dao = gj.getDao(gd);
        val controller = gj.getController(gd);
        val entity = gj.getEntity(gd);
        val api = gj.getVueApi(gd);
        val vue3 = gj.getVue(gd);

//        System.out.println(serviceImpl);
//        System.out.println(service);
//        System.out.println(dao);
//        System.out.println(controller);
//        System.out.println(entity);
//        System.out.println(api);
//        System.out.println(vue3);
    }
}

