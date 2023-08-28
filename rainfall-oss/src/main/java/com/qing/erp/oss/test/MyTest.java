package com.qing.erp.oss.test;

import lombok.SneakyThrows;
import lombok.val;

import javax.imageio.ImageIO;
import javax.imageio.metadata.IIOMetadata;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MyTest {
    @SneakyThrows
    public void test(String[] args) {
        val url = "https://i1.hdslb.com/bfs/face/58fb58115d3e4f2e0f435b7c08b778e189ebe151.jpg@240w_240h_1c_1s_!web-avatar-nav.webp";
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        val contentType = conn.getHeaderField("content-type");
        val r = contentType.split("/");
        System.out.println(r[0]);
        System.out.println(r[1]);

        // 将输入流转换为图像对象
        BufferedImage image = ImageIO.read(conn.getInputStream());

        // 获取图像元数据
        IIOMetadata metadata = ImageIO.getImageReaders(image).next().getImageMetadata(0);

        System.out.println(metadata);
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入数字字符串: ");
        String input = scanner.nextLine();

        int min = Integer.MAX_VALUE;
        int index = -1;

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (Character.isDigit(ch)) {
                int num = ch - '0';
                if (num < min) {
                    min = num;
                    index = i;
                }
            }
        }

        StringBuilder sb = new StringBuilder(input);
        sb.insert(index + 1, "A");
        String result = sb.toString();

        System.out.println("最小数字为: " + min);
        System.out.println("添加A后的字符串为: " + result);
    }
}
