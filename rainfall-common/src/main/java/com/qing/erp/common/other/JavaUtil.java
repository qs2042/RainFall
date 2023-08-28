package com.qing.erp.common.other;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;

public class JavaUtil {
    //
    public static <E> E conversionType(Object obj) {
        return (E) obj;
    }

    //
    public static LinkedList<Object> enumerationToList(Enumeration enumeration) {
        LinkedList<Object> list = new LinkedList<>();
        while (enumeration.hasMoreElements()) {
            list.add(enumeration.nextElement());
        }
        return list;
    }

    // 获取类型
    public static String getType(Object o){
        return o.getClass().toString();
    }

    // object数组 转 其他数组
    public static <E> E convertArray(Object[] objects, Class<E> arrayClass){
        // 其他方法: Arrays.stream(objects).toArray(String[]::new);

        Class<?> componentType = arrayClass.getComponentType();

        // 假设clazz = String[].class
        // String[] es = (String[]) Array.newInstance(componentType, 0);
        E[] es = (E[]) Array.newInstance(componentType, 0);

        return (E) Arrays.asList(objects).toArray(es);
    }

    public static void main(String[] args) {
        Object[] objects = Arrays.asList(1, 2, 3).toArray();

        String[] strings = convertArray(objects, String[].class);
        Integer[] integers = convertArray(objects, Integer[].class);

        System.out.println(Arrays.toString(objects));
        System.out.println(Arrays.toString(strings));
        System.out.println(Arrays.toString(integers));
    }

}
