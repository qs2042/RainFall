package com.qing.erp.test.config;

import com.qing.erp.common.module.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class RedisConfig {
    @Autowired
    RedisClient rc;
    //    @Autowired
    RedisTemplate<String, Object> redisTemplate;
//    @Autowired
//    RedissonClient redissonClient;


    /**
     * 调用这个方法需要使用锁
     * <p>
     * 数据一致性问题
     * 1.仅加过期时间即可（首先考虑业务造成脏数据的概率，例如用户维度数据（订单数据、用户数据）并发几率很小，每过一段时间触发读的主动更新）
     * 2.canal订阅binlog的方式（菜单、商品介绍等基础数据）【完美解决】
     * 3.加读写锁
     * 4.实时性、一致性要求高的数据，应该直接查数据库
     * <p>
     * 最终方案
     * 1.所有数据加上过期时间
     * 2.读写数据加分布式读写锁（经常写的数据不要放在缓存里）
     */
    public String testRedis() {
        // 从缓存中读取
//        String qq = rc.getValue("qq");
        String qq = (String) redisTemplate.opsForValue().get("qq");

        // 从数据库中读取, 并存入缓存
        if (qq == null) {
            qq = "2042136767";
//            rc.set("qq", qq);
            redisTemplate.opsForValue().set("qq", "2042136767", 1, TimeUnit.DAYS);
        }

        return qq;
    }

    public void testRedisLock() {
        // 缓存同步(无锁)
        // 获取缓存, 如果缓存中没有, 就从数据库中提取再放入缓存
        testRedis();

        // 缓存同步(本地锁) synchronized，JUC(lock)，在分布式0情况下，需要使用分布式锁
        synchronized (this) {
            // 得到锁以后还要检查一次，double check
            testRedis();
        }

        // 缓存同步(redisTemplate)
        /*// 抢占分布式锁，同时设置过期时间
        String uuid = UUID.randomUUID().toString();
        // 使用setnx占锁（setIfAbsent）
        Boolean isLock = redisTemplate.opsForValue().setIfAbsent("qq", uuid, 300, TimeUnit.SECONDS);
        if (isLock) {
            // 抢占成功
            try {
                // 查询DB, TODO 业务续期（锁过期）【不应该添加业务续期代码】
                redisTemplate.opsForValue().get("qq");
                redisTemplate.opsForValue().set("qq", "2042136767", 1, TimeUnit.DAYS);
            } finally {
                // 查询UUID是否是自己，是自己的lock就删除
                // 封装lua脚本（原子操作解锁）
                // 查询+删除（当前值与目标值是否相等，相等执行删除，不等返回0）
                String luaScript = "if redis.call('get',KEYS[1]) == ARGV[1]\n" +
                        "then\n" +
                        "    return redis.call('del',KEYS[1])\n" +
                        "else\n" +
                        "    return 0\n" +
                        "end";
                // 删除锁
                redisTemplate.execute(new DefaultRedisScript<Long>(luaScript, Long.class), Arrays.asList("qq"), uuid);
            }
        } else {
            // 加锁失败，自旋重试
            // TODO 不应该使用递归，使用while，且固定次数
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/

        // 缓存同步(redisson分布式锁: 解决击穿+雪崩+穿透null值)
        /*// 1.抢占分布式锁，同时设置过期时间
        RLock lock=redissonClient.getLock("qq");
        lock.lock(30, TimeUnit.SECONDS);
        try{
            // 2.查询DB
            redisTemplate.opsForValue().get("qq");
            redisTemplate.opsForValue().set("qq", "2042136767", 1, TimeUnit.DAYS);
        }finally{
            // 3.释放锁
            lock.unlock();
        }*/
    }


    /*
     SpringCache
     springcache使用本地锁【只有@Cacheable可以同步】
     springcache可以解决击穿、雪崩、穿透【采用了本地锁，不是完全解决击穿，可以自己使用分布式锁（没必要）】
     springcache未解决数据一致性问题

     // 缓存策略：失效模式，方法执行完删除缓存
     @CacheEvict(value = {"category"}, allEntries = true)
     @Transactional
     public void update(){}

     @Cacheable(value = {"category"}, key = "'getCategorys'")
     public List<CategoryEntity> getCategorys(){

     @Cacheable(value = {"category"}, key = "'getCatalogJson'")
     public Map<String, List<Catalog2VO>>getCatalogJsonWithSpringCache(){
     */

}


