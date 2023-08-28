package com.qing.erp.module.base.config;

import com.qing.erp.common.io.IOUtils;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // http://patorjk.com/software/taag/#p=display&f=Bloody&t=success
        // https://www.bootschool.net/ascii
        Resource resource = resourceLoader.getResource("classpath:success.txt");
        InputStream inputStream = resource.getInputStream();
        val text = IOUtils.toText(inputStream, StandardCharsets.UTF_8);
        inputStream.close();
        System.out.println("\u001B[31m" + text + "\u001B[0m");
    }
}
