package com.qing.erp.module.base;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;


public class EnableBaseCondition implements Condition {
    @Override
    public boolean matches(@NotNull ConditionContext context, @NotNull AnnotatedTypeMetadata metadata) {
        // 返回 true 表示开启模块，返回 false 表示不开启
        // 可以根据配置文件、系统属性、运行时环境等进行条件判断
        // 示例：判断是否存在某个配置项来决定是否开启基础模块
        // return context.getEnvironment().getProperty("base.module.enabled", Boolean.class, false);
        return true;
    }
}
