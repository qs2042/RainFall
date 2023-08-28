package com.qing.erp.common.module.docker;

public class DockerTest {
    public static final DockerClientPro dc = DockerClientPro.buildHttp("192.168.126.128", 2375);

    public static void main(String[] args) {
//        System.out.println(dc.getVersion().toJson());
//        System.out.println("==========================");
//        System.out.println(dc.getInfo().toJson());
//        System.out.println("==========================");
//        dc.getImages().forEach(v -> System.out.println(v.toJson()));
//        System.out.println("==========================");
//        System.out.println(dc.searchImage("python"));


//        System.out.println(dc.getContainers());
//        System.out.println("==========================");
        System.out.println(dc.getContainer("e26f1fad62ffc26d37c54fac02d9bd2aff3d43236b71b725f1d0a2f8b98aa1a7"));

    }
}
