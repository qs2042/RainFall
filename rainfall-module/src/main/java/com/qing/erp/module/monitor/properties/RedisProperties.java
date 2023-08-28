package com.qing.erp.module.monitor.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "qing.redis")
@Component
public class RedisProperties implements BeanClassLoaderAware, InitializingBean {
    private ClassLoader classLoader;
    private EmbeddedDatabaseConnection embeddedDatabaseConnection;

    private String ip;
    private Integer port;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.embeddedDatabaseConnection == null) {
            this.embeddedDatabaseConnection = EmbeddedDatabaseConnection.get(this.classLoader);
        }
    }
}
