package com.qing.erp.common.module.mysql.gen.jpa;

import com.qing.erp.common.module.mysql.constant.DbType;
import com.qing.erp.common.module.mysql.gen.GenData;
import com.qing.erp.common.module.mysql.pojo.ColumnMetaData;
import com.qing.erp.common.module.mysql.pojo.Gen;
import com.qing.erp.common.module.mysql.pojo.TableMetaData;
import com.qing.erp.common.str.StrUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import lombok.val;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

public class GenJpa {
    private static Configuration config = null;

    static {
        config = new Configuration(Configuration.VERSION_2_3_31);
        config.setClassForTemplateLoading(GenJpa.class, "/tmp");
    }

    public static Gen getGateway(GenData data, List<ColumnMetaData> meta) {
        return new Gen("application", "config", "yml", null);
    }

    public GenJpa build() {
        return null;
    }

    @SneakyThrows
    public static Gen getTableEntity(DatabaseMetaData metaData, String databaseName, String tableName) {
        String tableNameJava = StrUtil.underscoreToCamelCase(tableName);

        // 获取列名, 类型, 注释
        ResultSet rs = metaData.getColumns(null, databaseName, tableName, null);
        ArrayList<String> columnNames = new ArrayList<>();
        ArrayList<String> columnTypes = new ArrayList<>();
        ArrayList<Integer> columnTypeValues = new ArrayList<>();
        ArrayList<String> columnComments = new ArrayList<>();
        while (rs.next()) {
            columnNames.add(rs.getString("COLUMN_NAME"));
            columnTypes.add(rs.getString("TYPE_NAME"));
            columnTypeValues.add(rs.getInt("COLUMN_SIZE"));
            columnComments.add(rs.getString("REMARKS"));
        }

        // 要添加的库
        ArrayList<String> imports = new ArrayList<>();
        imports.add("import lombok.Data;");
        imports.add("import lombok.NoArgsConstructor;");
        imports.add("import lombok.AllArgsConstructor;");

        // 创建Java Bean类名
        String className = StrUtil.underscoreToCamelCase(tableName);
        className = className.substring(0, 1).toUpperCase() + className.substring(1);

        // 创建Java Bean类
        StringBuilder sb = new StringBuilder();
        // 注解
        sb.append("@Data").append("\n");
        sb.append("@NoArgsConstructor").append("\n");
        sb.append("@AllArgsConstructor").append("\n");
        // 类
        sb.append("public class ").append(className).append(" {\n");
        for (int i = 0; i < columnNames.size(); i++) {
            // 字段名称,字段类型,字段注释
            val name = columnNames.get(i);
            val type = columnTypes.get(i);
            val comment = columnComments.get(i);

            // java字段类型
            String javaType = DbType.getJavaType(type, imports);

            sb
                    // 注释
                    .append("    /**\n")
                    .append("     * ").append(comment).append("\n")
                    .append("     */\n")
                    // 字段
                    .append("    private ").append(javaType).append(" ")
                    .append(StrUtil.underscoreToCamelCase(name)).append(";\n");
        }
        sb.append("}\n");

        val sbStart = new StringBuilder();
        imports.stream().distinct().forEach(v -> sbStart.append(v).append("\n"));
        sbStart.append("\n").append(sb.toString());
        return new Gen(tableNameJava + "Entity", "entity", "java", sbStart.toString());

    }

    public static Gen getEntity(GenData data, List<ColumnMetaData> list) {
        List<String> imports = new ArrayList<>();

        // 处理数据
        for (ColumnMetaData meta : list) {
            // 将数据库type转为javaType
            val type = meta.getType();
            String javaType = DbType.getJavaType(type, imports);
            meta.setType(javaType);

            // 将数据库name转为java小驼峰
            val name = meta.getName();
            String javaName = StrUtil.underscoreToCamelCase(name);
            meta.setName(javaName);
        }

        // 数据去重
        imports = imports.stream().distinct().collect(Collectors.toList());
        data.setImportList(imports);

        val map = convertBeanToMap(data);
        map.put("fields", list);

        val code = gen("/java/entity.ftl", map, config);
        return new Gen(data.getClassName() + "Entity", "entity", "java", code);
    }

    public static Gen getDao(GenData data) {
        val map = convertBeanToMap(data);
        val code = gen("/java/dao.ftl", map, config);

        return new Gen(data.getClassName() + "Dao", "dao", "java", code);
    }

    public static Gen getServiceImpl(GenData data) {
        val map = convertBeanToMap(data);
        val code = gen("/java/serviceImpl.ftl", map, config);

        return new Gen(data.getClassName() + "Impl", "serviceImpl", "java", code);
    }

    public static Gen getService(GenData data) {
        val map = convertBeanToMap(data);
        val code = gen("/java/service.ftl", map, config);

        return new Gen("I" + data.getClassName() + "Service", "service", "java", code);
    }

    public static Gen getController(GenData data) {
        val map = convertBeanToMap(data);
        val code = gen("/java/controller.ftl", map, config);
        return new Gen(data.getClassName() + "Controller", "controller", "java", code);
    }

    public static Gen getVue(GenData data, List<ColumnMetaData> list) {
        val map = convertBeanToMap(data);
        map.put("testList", list);

        // 处理数据
        val editFields = new ArrayList<>();
        for (ColumnMetaData meta : list) {
            val type = meta.getType();

            // 如果是需要被编辑的字段
            if ("String".equals(type) || "Integer".equals(type)) {
                editFields.add(meta.getName());
            }
        }
        map.put("fields", list);

        map.put("editFields", editFields.stream()
                .map(n -> "'" + n + "'")
                .collect(Collectors.joining(",")));

        val code = gen("/html/vue3.ftl", map, config);
        return new Gen(data.getClassName() + "View", "view", "vue", code);
    }

    public static Gen getVueApi(GenData data, List<ColumnMetaData> list) {
        val map = convertBeanToMap(data);
        map.put("testList", list);

        // 处理数据
        for (ColumnMetaData meta : list) {
            // 将数据库name转为java小驼峰
            val name = meta.getName();
            String javaName = StrUtil.underscoreToCamelCase(name);
            meta.setName(javaName);
        }
        map.put("fields", list);

        val code = gen("/html/api.ftl", map, config);
        return new Gen(data.getClassName(), "api", "ts", code);
    }

    @SneakyThrows
    private static String gen(String path, HashMap data, Configuration config) {
        Template template = config.getTemplate(path);
        StringWriter writer = new StringWriter();
        template.process(data, writer);
        return writer.toString();
    }

    public static <T> HashMap<String, Object> convertBeanToMap(T bean) {
        HashMap<String, Object> map = new HashMap<>();
        Class<?> clazz = bean.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            try {
                Object fieldValue = field.get(bean);
                map.put(fieldName, fieldValue);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot convert bean to map", e);
            }
        }
        return map;
    }

}
