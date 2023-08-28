package com.qing.erp.system.aop.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)         // 该注解只能放在方法上
@Retention(RetentionPolicy.RUNTIME) // 该注解在运行阶段执行
public @interface RLog {
    // 作者
    String author() default "R";

    // 笔记
    String notes() default "暂无介绍";

    // 模块
    String module() default "null";

    // 是否打印日志
    boolean isPrint() default false;

    // 是否保存日志
    boolean isSave() default true;
}