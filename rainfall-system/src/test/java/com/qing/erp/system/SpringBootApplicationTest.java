package com.qing.erp.system;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// @RunWith(SpringRunner.class) 使用spring驱动方式
@SpringBootTest
public class SpringBootApplicationTest {

    @Test
    void test() {
        System.out.println("test");
    }
}
