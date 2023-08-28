package com.qing.erp.common.system;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class JedisWrapper {
    private final Jedis jedis;

    public JedisWrapper(Jedis jedis) {
        this.jedis = jedis;
    }

    public void printRedisInfo() {
        String info = jedis.info();
        System.out.println("Redis Version: " + getInfoValue(info, "redis_version"));
        System.out.println("Redis Mode: " + getInfoValue(info, "redis_mode"));
        System.out.println("Redis Port: " + getInfoValue(info, "tcp_port"));
        System.out.println("Redis Client Count: " + getInfoValue(info, "connected_clients"));
        System.out.println("Redis Uptime: " + getInfoValue(info, "uptime_in_seconds") + " seconds");
        System.out.println("Redis Memory Used: " + getInfoValue(info, "used_memory_human"));
        System.out.println("Redis Memory Peak: " + getInfoValue(info, "used_memory_peak_human"));
        System.out.println("Redis CPU Usage: " + getInfoValue(info, "used_cpu_sys") + " sys, " + getInfoValue(info, "used_cpu_user") + " user");
        System.out.println("Redis AOF Enabled: " + getInfoValue(info, "aof_enabled"));
        System.out.println("Redis RDB Last Save: " + getInfoValue(info, "rdb_last_save_time"));
        System.out.println("Redis Key Count: " + getInfoValue(info, "db0:keys"));
        System.out.println("Redis Network Input: " + getInfoValue(info, "total_net_input_bytes_human"));
        System.out.println("Redis Network Output: " + getInfoValue(info, "total_net_output_bytes_human"));
    }

    private String getInfoValue(String info, String key) {
        String[] lines = info.split("\r\n");
        for (String line : lines) {
            if (line.startsWith(key + ":")) {
                return line.substring(key.length() + 1).trim();
            }
        }
        return null;
    }

    public Set<String> getCacheKeys() {
        return jedis.keys("cache_*");
    }

    public Set<String> getKeys() {
        return jedis.keys("*");
    }
    public String getInfo() {
        return jedis.info();
    }

    public void close() {
        jedis.close();
    }

}