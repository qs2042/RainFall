/*
package com.qing.erp.member.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ValidConfig {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setBeforeExistingAdvisors(true);
        postProcessor.setProxyTargetClass(true);
        postProcessor.setValidator(validator());
        return postProcessor;
    }

    */
/**
     * 开启快速失败模式, 一旦失败立即抛出异常, 不再检验后续字段
     *//*

    @Bean
    public Validator validator() {
        return Validation
                .byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory()
                .getValidator();
    }
}

*/
