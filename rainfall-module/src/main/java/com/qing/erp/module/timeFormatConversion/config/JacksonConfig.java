package com.qing.erp.module.timeFormatConversion.config;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    // 代替yml配置
    /*
    jackson:
      time-zone: GMT+8
      date-format: yyyy-MM-dd HH:mm:ss
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeJackson() {
        return builder -> builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
