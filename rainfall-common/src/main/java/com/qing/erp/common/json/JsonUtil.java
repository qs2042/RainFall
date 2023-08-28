package com.qing.erp.common.json;

import java.lang.reflect.Field;
import java.util.HashMap;

public class JsonUtil {
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
