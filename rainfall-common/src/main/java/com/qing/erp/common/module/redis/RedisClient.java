package com.qing.erp.common.module.redis;

import com.qing.erp.common.module.redis.pojo.RedisInfoEntity;
import lombok.SneakyThrows;
import lombok.val;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * * 表示数组的数量
 * $ 字符的长度
 * #
 * + 值类型
 * : 布尔值
 */
public class RedisClient {
    public boolean isConnection = true;
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;

    private void init(String host, int port) {
        try {
            socket = new Socket(host, port);
            socket.setSoTimeout(1000 * 4);
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
        } catch (Exception e) {
            isConnection = false;
        }
    }

    public RedisClient(String host, int port) {
        init(host, port);
    }

    public String buildCommandRaw(String... args) {
        return String.join("\r\n", args) + "\r\n";
    }

    public String buildCommand(String... args) {
        val s = new StringBuilder(String.format("*%d\r\n", args.length));
        for (String arg : args) {
            s.append(String.format("$%s\r\n%s\r\n", arg.length(), arg));
        }
        return s.toString();
    }

    @SneakyThrows
    public String executeCommand(String command) {
        // 输出命令(需要把字符串转为字节)
        outputStream.write(command.getBytes());

        // 获取结果
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        char[] buffer = new char[1024];
        int len;
        while ((len = reader.read(buffer)) != -1) {
            response.append(buffer, 0, len);
            if (response.toString().endsWith("\r\n")) {
                break;
            }
        }
        return response.toString().trim();
    }

    /**
     * 获取redis信息(废弃)
     */
    public ArrayList<RedisInfoEntity> getInfoToPojo() {
        // 获取信息
        val res = executeCommand("*1\r\n$4\r\nINFO\r\n");

        // 解析 Redis 服务器响应
        ArrayList<RedisInfoEntity> redisList = new ArrayList<>();
        RedisInfoEntity currentRedis = new RedisInfoEntity(); // 当前的 RedisInfoEntity 对象

        // redis是以\r\n结尾的, 如果这里用\n就会导致程序出很严重的错误, 因为只剩下\r的话, 控制台会删除当前行内容
        for (String line : res.split("\r\n")) {
            String type = null;
            String k = null;
            String v = null;

            // 跳过
            if (line.startsWith("$")) {
                continue;
            }

            // 获取type, kv
            if (line.startsWith("#")) {
                type = line.substring(2);
            }
            if (type == null) {
                // 不知道为什么split有BUG, 分出来的结果是: ]
                // String[] parts = line.split(":");
                val index = line.indexOf(":");
                if (index != -1) {
                    k = line.substring(0, index);
                    v = line.substring(index + 1);
                }
            }

            // 如果type出现了
            if (type != null) {
                // 保存旧的redis
                if (currentRedis.getType() != null) {
                    redisList.add(currentRedis);
                }

                // 设置新的redis
                currentRedis = new RedisInfoEntity();
                currentRedis.setType(type);
            }

            // 设置kv
            if (type == null) {
                currentRedis.addValue(k, v);
            }
        }

        return redisList;
    }

    /**
     * 获取redis信息
     */
    public Map<String, Map<String, Object>> getInfo() {
        // 获取信息
        //
        val res = executeCommand("*1\r\n$4\r\nINFO\r\n");
//        val res = executeCommand(buildCommand("INFO"));

        // 分割
        String[] lines = res.split("\\r?\\n");

        Map<String, Map<String, Object>> map = new HashMap<>();

        String type = null;
        for (String line : lines) {
            // 如果是type
            if (line.startsWith("#")) {
                type = line.substring(1).trim();
                map.put(type, new HashMap<>());
            }
            // 如果是当前type的值
            else if (type != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    map.get(type).put(key, value);
                }
            }
        }
        return map;
    }

    /**
     * 获取所有Key(带cache开头的)
     */
    public ArrayList<String> getCacheKeys() {
        // "*2\r\n$4\r\nKEYS\r\n$7\r\ncache_*\r\n"
        String res = executeCommand(buildCommand("KEYS", "cache_*"));

        int count = 0;
        boolean isField = false;
        ArrayList<String> arr = new ArrayList<>();
        for (String line : res.split("\r\n")) {
            // *{number} 数据有多少条
            // ${number} 当前这条数据有多少个字符, 下一行就是当前数据

            if (line.isEmpty()) {
                continue;
            }

            if (line.startsWith("*")) {
                count = Integer.parseInt(line.substring(1).trim());
            }

            // 其实这个地方我直接跳过$就好了, 剩下的都是字段, 但是还是遵守协议规则吧
            if (line.startsWith("$")) {
                isField = true;
                continue;
            }

            if (isField) {
                isField = false;
                arr.add(line);
            }
        }

        // 验证数据是否正确
        if (count != arr.size()) {
            System.out.println("数据不正确, 将不会返回错误数据");
            return null;
        }
        return arr;
    }

    /**
     * 获取所有Key
     */
    public ArrayList<String> getKeys() {
        // "*2\r\n$4\r\nKEYS\r\n$1\r\n*\r\n"
        String res = executeCommand(buildCommand("KEYS", "*"));

        int count = 0;
        ArrayList<String> arr = new ArrayList<>();
        for (String line : res.split("\r\n")) {
            // *{number} 数据有多少条
            // ${number} 当前这条数据有多少个字符, 下一行就是当前数据

            // item数量
            if (line.startsWith("*")) {
                count = Integer.parseInt(line.substring(1));
                continue;
            }

            // TODO: 如果是-1, 那存数据就为null
            if (line.startsWith("$")) {
                continue;
            }

            arr.add(line);
        }

        // 验证数据是否正确
        if (count != arr.size()) {
            System.out.println("数据不正确, 将不会返回错误数据");
            return null;
        }

        return arr;
    }

    /**
     * 获取多个Key的Value
     */
    public ArrayList<String> getValues(String... keys) {
        String[] args = new String[keys.length + 1];
        args[0] = "MGET";
        System.arraycopy(keys, 0, args, 1, keys.length);

        String res = executeCommand(buildCommand(args));

        int count = 0;
        boolean isField = true;
        val list = new ArrayList<String>();
        for (String line : res.split("\r\n")) {
            // 数量
            if (line.startsWith("*")) {
                count = Integer.parseInt(line.substring(1));
                continue;
            }

            // 字段的长度
            if (line.startsWith("$")) {
                // 如果-1那就是不存在
                if ("-1".equals(line.substring(1))) {
                    isField = false;
                    list.add(null);
                }
                continue;
            }

            if (isField) {
                list.add(line);
            }
            if (!isField) {
                isField = true;
            }

        }

        if (count != list.size()) {
            return null;
        }
        return list;
    }

    /**
     * 获取Key的Value
     */
    public String getValue(String key) {
        val list = getValues(key);
        return list == null ? null : list.get(0);
    }

    /**
     * 获取多个Key的Value类型
     */
    public ArrayList<String> getValuesType(String... keys) {
        val arr = new ArrayList<String>();

        for (String key : keys) {
            String type = getValueType(key);
            arr.add(type);
        }
        return arr;
    }

    /**
     * 获取Key的Value类型
     */
    public String getValueType(String key) {
        String res = executeCommand(buildCommand("TYPE", key));
        if (!res.startsWith("+")) {
            return null;
        }
        return res.substring(1);
    }

    /**
     * 判断Key是否存在
     */
    public boolean isKeyExists(String key) {
        String res = executeCommand(buildCommand("exists", key));
        if (!res.startsWith(":")) {
            System.out.println("似乎出错了");
            return false;
        }
        return !"0".equals(res.substring(1));
    }

    /**
     * 删除指定的Key和Value
     */
    public boolean delKey(String key) {
        String res = executeCommand(buildCommand("DEL", key));
        if (!res.startsWith(":")) {
            System.out.println("似乎出错了");
            return false;
        }
        return !"0".equals(res.substring(1));
    }

    /**
     * 删除指定的Key和Value(非阻塞)
     */
    public boolean delKeyByUnLink(String key) {
        String res = executeCommand(buildCommand("unlink", key));
        if (!res.startsWith(":")) {
            System.out.println("似乎出错了");
            return false;
        }
        return !"0".equals(res.substring(1));
    }

    /**
     * 设置过期时间
     */
    public boolean setKeyExpire(String key, Integer second) {
        String res = executeCommand(buildCommand("expire", key, String.valueOf(second)));
        if (!res.startsWith(":")) {
            System.out.println("似乎出错了");
            return false;
        }
        return !"0".equals(res.substring(1));
    }

    /**
     * 查看key过期时间
     *
     * @return 秒(- 1 = 永不过期, - 2 = key不存在)
     */
    public Integer ttlKey(String key) {
        String res = executeCommand(buildCommand("ttl", key));
        if (!res.startsWith(":")) {
            System.out.println("似乎出错了");
        }

        String time = res.substring(1);
        if ("-1".equals(time)) {
            return -1;
        } else if ("-2".equals(time)) {
            return -2;
        }
        return Integer.valueOf(time);
    }

    // 切换数据库
    public String selectDB(String db) {
        String res = executeCommand(buildCommand("select", db));
        return res;
    }

    // 查看当前数据库的key数量
    public String selectDBSize(String db) {
        String res = executeCommand(buildCommand("dbsize", db));
        return res;
    }

    // 清空当前库
    public String selectDBFlush(String db) {
        String res = executeCommand(buildCommand("flushdb", db));
        return res;
    }

    // 清空所有库
    public String selectDBFlushAll() {
        String res = executeCommand(buildCommand("flushall"));
        return res;
    }

    // 添加键值对(字符串)
    public String set(String key, String value) {
        String res = executeCommand(buildCommand("set", key, value));
        return res;
    }

    // 追加新值到原值末尾(字符串)
    public String append(String key, String value) {
        String res = executeCommand(buildCommand("append", key, value));
        return res;
    }

    // 向Key的键值中添加键值对(哈希)
    public String hset(String key, String field, String value) {
        String res = executeCommand(buildCommand("hset", key, field, value));
        return res;
    }

    // 向Key的键值中获取键值对(哈希)
    public String hget(String key, String field) {
        String res = executeCommand(buildCommand("hget", key, field));
        return res;
    }

    // TODO: 向Key的键值中批量添加键值对(哈希)
    public String hmset(String key, String field, HashMap<String, Object> map) {
        // hmset {key}{field}{value}{field}{value}
        String res = executeCommand(buildCommand("hmset", key, field));
        return res;
    }

    // TODO: 向Key的键值中批量添加键值对(哈希)
    public String hmset(String key, String field, Object... args) {
        // hmset {key}{field}{value}{field}{value}
        String res = executeCommand(buildCommand("hmset", key, field));
        return res;
    }

    // TODO: info commandstats  获取各个命令的执行次数、执行时间等统计信息的文本

    // TODO: 列表, 集合, 有序集合, Bitmaps, HyperLogLog, Gespatial
    // TODO: 获取/修改配置文件: Units, Includes, Network, General, Security, Limits
    // TODO: 发布/订阅, 事务/锁, 主从复制, 集群, 分布式锁,

    /**
     * 关闭
     */
    @SneakyThrows
    public void close() {
        socket.close();
    }

    public static void main(String[] args) {
        RedisClient rc = new RedisClient("192.168.126.128", 6379);

        System.out.println(rc.getInfo());

        System.out.println(rc.getCacheKeys());

        System.out.println(rc.getKeys());

        /*// 获取Key
        System.out.println(rc.getCacheKeys());
        System.out.println(rc.getKeys());
        System.out.println();

        // 获取Value
        System.out.println(rc.getValue("qq"));
        System.out.println(rc.getValue("bb"));
        System.out.println(rc.getValues("qq", "author"));
        System.out.println(rc.getValues("qq", "bbb"));
        System.out.println();

        // 获取Value类型
        System.out.println(rc.getValuesType("author", "qq", "test"));
        System.out.println(rc.getValueType("author"));
        System.out.println(rc.getValueType("test"));
        System.out.println();

        // key是否存在
        System.out.println(rc.isKeyExists("author"));
        System.out.println(rc.isKeyExists("aaa"));
        System.out.println();

        // 删除kv
        System.out.println(rc.delKey("t1"));
        System.out.println(rc.delKeyByUnLink("t1"));
        System.out.println();

        // 过期时间
        System.out.println(rc.setKeyExpire("t1", 20));
        System.out.println(rc.ttlKey("t1"));
        System.out.println();*/

        rc.close();
    }
}
