package com.qing.erp.system.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

// 谷歌验证码配置文件
@Configuration
public class KaptchaConfig {
    @Bean(name = "defaultKaptcha")
    public DefaultKaptcha getKaptchaBean(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();

        Properties properties = new Properties();

        // 是否有边框 默认true 也可以自己设置yes,no
        properties.setProperty("kaptcha.border", "no");

        // 验证码文本字符颜色 默认为Color.BLACK
        properties.setProperty("kaptcha.textproducer.font.color", "black");

        // 验证码图片宽度 默认为200
        properties.setProperty("kaptcha.image.width", "160");

        // 验证码图片高度 默认为50
        properties.setProperty("kaptcha.image.height", "60");

        // 验证码文本字符大小 默认为40
        properties.setProperty("kaptcha.textproducer.font.size", "38");

        // 存储在session中值的key
        properties.setProperty("kaptcha.session.key", "kaptchaCode");

        // 验证码文本字符长度 默认为5
        properties.setProperty("kaptcha.textproducer.char.length", "4");

        // 验证码文本字体样式 默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
        properties.setProperty("kaptcha.textproducer.font.names", "Arial,Courier");

        // 图片样式 水纹com.google.code.kaptcha.impl.WaterRipple 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy 阴影com.google.code.kaptcha.impl.ShadowGimpy
        properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    @Bean(name = "defaultKaptchaMath")
    public DefaultKaptcha getKaptchaBeanMath(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();

        Properties properties = new Properties();

        //是否有边框 默认true 也可以自己设置yes,no
        properties.setProperty("kaptcha.border", "yes");

        //边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");

        //验证码文本字符颜色 默认为Color.BLACK
        properties.setProperty("kaptcha.textproducer.font.color", "black");

        // 验证码图片宽度 默认为200
        properties.setProperty("kaptcha.image.width", "160");

        // 验证码图片高度 默认为50
        properties.setProperty("kaptcha.image.height", "60");

        // 验证码文本字符大小 默认为40
        properties.setProperty("kaptcha.textproducer.font.size", "38");

        //存储在session中值的key
        properties.setProperty("kaptcha.session.key", "kaptchaCodeMath");

        //验证码文本生成器
        properties.setProperty("kaptcha.textproducer.impl","com.tonglei.config.KaptchaTextCreator");

        // 验证码文本字符间距 默认为2
        properties.setProperty("kaptcha.textproducer.char.space", "3");

        // 验证码文本字符长度 默认为5
        properties.setProperty("kaptcha.textproducer.char.length", "4");

        // 验证码文本字体样式 默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
        properties.setProperty("kaptcha.textproducer.font.names", "Arial,Courier");

        // 图片样式 水纹com.google.code.kaptcha.impl.WaterRipple 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy 阴影com.google.code.kaptcha.impl.ShadowGimpy
        properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");

        // 验证码噪点颜色 默认为Color.BLACK
        properties.setProperty("kaptcha.noise.color", "black");

        // 干扰实现类  DefaultNoise\NoNoise
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.DefaultNoise");

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;

    }
}


