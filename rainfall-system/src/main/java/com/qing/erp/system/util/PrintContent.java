package com.qing.erp.system.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Slf4j
public class PrintContent {
    private static Integer n = 0;
    public static void method() {
        log.info(String.format("[%d] %s", n, "我是monitor的测试类: method"));
//        System.out.println(String.format("[%d] %s", n, "我是monitor的测试类"));
        n++;
    }
    public static void test() {
        log.info(String.format("%s", "我是monitor的测试类: test"));
    }

    @SneakyThrows
    public static void main(String[] args) {

        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        val date = dateFormat.parse("Mon, 17 Jul 2023 12:37:51 GMT");
        System.out.println(date);

        val r = "Mon, 17 Jul 2023 12:58:4 GMT".matches("^(?i)\\w{3},\\s\\d{1,2}\\s\\w{3}\\s\\d{4}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\sGMT$");
        System.out.println(r);
    }
}
