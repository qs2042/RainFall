package com.qing.erp.module.monitor.config;

import com.qing.erp.common.module.docker.DockerClient;
import com.qing.erp.common.module.redis.RedisClient;
import com.qing.erp.module.monitor.properties.DockerProperties;
import com.qing.erp.module.monitor.properties.RedisProperties;
import lombok.val;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class ServerConfig {
    @Autowired
    DockerProperties dp;

    @Autowired
    RedisProperties rp;

    @Bean
    public RedisClient redisClient() {
        return new RedisClient(rp.getIp(), rp.getPort());
    }

    @Bean
    public DockerClient dockerClient() {
        return DockerClient.buildHttp(dp.getIp(), dp.getPort());
    }

    // 配置Redis连接工厂
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(rp.getIp());
        redisConfig.setPort(rp.getPort());
        redisConfig.setPassword(RedisPassword.of(""));

        return new LettuceConnectionFactory(redisConfig);
    }

    // 配置RedisTemplate TODO: StringRedisTemplate
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();

        // 配置Redis连接工厂
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(rp.getIp());
//        redisStandaloneConfiguration.setPort(rp.getPort());
//        redisStandaloneConfiguration.setPassword(RedisPassword.of(""));

        // 创建Redis连接工厂
//        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
//        lettuceConnectionFactory.afterPropertiesSet();

        // 设置Redis连接工厂
//        template.setConnectionFactory(lettuceConnectionFactory);

        return template;
    }

    // 配置RedissonClient
    /*@Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        // 单节点模式
        config.useSingleServer().setAddress("redis://" + rp.getIp() + ":" + rp.getPort());
//        config.useSingleServer().setAddress("rediss://" + host + ":" + port);// 使用安全连接
//        config.useClusterServers().addNodeAddress("127.0.0.1:7004", "127.0.0.1:7001");// 集群模式

        return Redisson.create(config);
    }*/
}
