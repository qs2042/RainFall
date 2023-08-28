package com.qing.erp.common.module.db_gen;

import com.qing.erp.common.module.db_gen.pojo.GenData;
import com.qing.erp.common.module.db_gen.pojo.GenResult;
import com.qing.erp.common.module.mysql.MySQLClient;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import lombok.val;

import java.io.StringWriter;

public class GenJpa implements IMethod {
    private static Configuration config = null;

    static {
        config = new Configuration(Configuration.VERSION_2_3_31);
        config.setClassForTemplateLoading(GenJpa.class, "/gen");
    }

    @SneakyThrows
    public static String gen(GenData gd, String filePath) {
        Template template = config.getTemplate(filePath);
        StringWriter writer = new StringWriter();
        template.process(gd, writer);
        return writer.toString();
    }

    @Override
    public GenResult getEntity(GenData gd) {
        val entity = gen(gd, "java/entity.ftl");
        return new GenResult(gd.getTableNameCamel()+"Entity", "entity", "java", entity);
    }

    @Override
    public GenResult getDao(GenData gd) {
        val dao = gen(gd, "java/dao.ftl");
        return new GenResult(gd.getTableNameCamel()+"Dao", "dao", "java", dao);
    }

    @Override
    public GenResult getService(GenData gd) {
        val service = gen(gd, "java/service.ftl");
        return new GenResult("I" + gd.getTableNameCamel()+"Service", "service", "java", service);
    }

    @Override
    public GenResult getServiceImpl(GenData gd) {
        val serviceImpl = gen(gd, "java/serviceImpl.ftl");
        return new GenResult(gd.getTableNameCamel()+"Impl", "serviceImpl", "java", serviceImpl);
    }

    @Override
    public GenResult getController(GenData gd) {
        val controller = gen(gd, "java/controller.ftl");
        return new GenResult(gd.getTableNameCamel()+"Controller", "controller", "java", controller);
    }

    @Override
    public GenResult getGateway(GenData gd) {
        return new GenResult("application.yml", "application", "yml", "");
    }

    @Override
    public GenResult getVue(GenData gd) {
        val code = gen(gd, "vue/vue3.ftl");
        return new GenResult(gd.getTableNameCamel()+"View", "view", "vue", code);
    }

    @Override
    public GenResult getVueApi(GenData gd) {
        val code = gen(gd, "vue/api.ftl");
        return new GenResult(gd.getTableNameCamel(), "api", "ts", code);
    }

    public static void main(String[] args) {
        final val genJpa = new GenJpa();
    }
}
