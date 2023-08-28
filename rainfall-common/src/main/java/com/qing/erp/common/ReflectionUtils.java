package com.qing.erp.common;

import lombok.val;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectionUtils {
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
    public static Map<String, Object> beanFieldsToHashMap(Object bean) {
        val r = new HashMap<String, Object>();
        Class<?> beanClass = bean.getClass();
        Field[] fields = beanClass.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String capitalizedFieldName = capitalize(fieldName);
            field.setAccessible(true);
            try {
                String getterMethodName = "get" + capitalizedFieldName;
                Method getterMethod = beanClass.getMethod(getterMethodName);
                Object value = getterMethod.invoke(bean);
                r.put(fieldName, value);
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }

        return r;
    }
}
