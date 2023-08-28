package com.qing.erp.module.monitor.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "qing.server")
@Component
public class ServerProperties implements BeanClassLoaderAware, InitializingBean {
    private ClassLoader classLoader;
    private EmbeddedDatabaseConnection embeddedDatabaseConnection;

    private String repoUrl;
    private String nginxUrl;

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
