package com.qing.erp.oss;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// web容器中进行部署
/*
maven记得改成war包, 并且内置的tomcat也不要打包出去了, 那些web容器会提供服务器的
<packaging>war</packaging>

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-tomcat</artifactId>
	<scope>provided</scope>
</dependency>

 */
public class OssServletApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(OssApplication.class);
    }
}
