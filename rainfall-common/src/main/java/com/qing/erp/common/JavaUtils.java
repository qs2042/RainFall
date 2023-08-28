package com.qing.erp.common;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class JavaUtils {
    public static <E> List<E> convertEnumerationToList(Enumeration<E> enumeration) {
        List<E> list = new ArrayList<>();
        while (enumeration.hasMoreElements()) {
            list.add(enumeration.nextElement());
        }
        return list;
    }
}
