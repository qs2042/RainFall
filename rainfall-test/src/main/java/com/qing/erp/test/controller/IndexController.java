package com.qing.erp.test.controller;

import com.qing.erp.common.data.R;
import com.qing.erp.common.io.IOUtils;
import com.qing.erp.test.feign.MemberFeignService;
import com.qing.erp.test.feign.SystemFeignService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("index")
public class IndexController {
    @Autowired
    ThreadPoolExecutor executor;

    @Autowired
    SystemFeignService systemFeignService;

    @Autowired
    MemberFeignService memberFeignService;

    @GetMapping("/list")
    public R list() {
        return systemFeignService.list("");
    }

    @SneakyThrows
    @GetMapping("/img")
    public void img(HttpServletRequest req, HttpServletResponse resp) {
        // 定义response输出类型为image/jpeg类型
        resp.setHeader("Cache-Control", "no-store");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");

        val image = systemFeignService.img();

        val is = image.getBody().getInputStream();

        // 使用response输出流输出图片的byte数组
        ServletOutputStream responseOutputStream = resp.getOutputStream();
        responseOutputStream.write(IOUtils.toByteArray(is));

        responseOutputStream.flush();
        responseOutputStream.close();
    }

    @RequestMapping("show")
    public R show() {
        return systemFeignService.show();
    }



    @Slf4j
    public static class TokenUtil {
        // 默认缓存时间
        public final Long TOKEN_EXPIRE_TIME = 60 * 30L;
        // token前缀
        public static final String IDEMPOTENT_TOKEN_PREFIX = "idempotent:token:";
        // token前缀
        public static final String DEFAULT_IDEMPOTENT_VALUE = "idempotent:value";
        // requisition_header_key
        public static final String IDEMPOTENT_TOKEN_HEADER_KEY = "Idempotent:Token";
        // requisition_parameter_key
        public static final String IDEMPOTENT_TOKEN_parameter_KEY = "uniqueToken";

        // 这里记得注入redis
        private StringRedisTemplate stringRedisTemplate;

        /**
         * 生成token
         */
        public String createToken() throws Exception {
            String sessionId = DEFAULT_IDEMPOTENT_VALUE;
            if (!StringUtils.hasText(sessionId)) {
                return null;
            }
            // 生成token
            String token = UUID.randomUUID().toString().replace("-", "");
            // 存入redis并设置有效期
            String key = IDEMPOTENT_TOKEN_PREFIX + token;
            stringRedisTemplate.opsForValue().set(key, sessionId, TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
            if (!StringUtils.hasText(stringRedisTemplate.opsForValue().get(key))) {
                throw new Exception("令牌创建失败");
            }
            return token;
        }

        /**
         * 校验token
         */
        public boolean verifyToken(String token) {
            // 设置 Lua 脚本，其中 KEYS[1] 是 key，KEYS[2] 是 value
            String script = "if redis.call('get', KEYS[1]) == KEYS[2] then return redis.call('del', KEYS[1]) else return 0 end";
            RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
            // 根据 Key 前缀拼接 Key
            String key = IDEMPOTENT_TOKEN_PREFIX + token;
            String value = DEFAULT_IDEMPOTENT_VALUE;
            // 执行 Lua 脚本
            Long result = stringRedisTemplate.execute(redisScript, Arrays.asList(key, value));
            // 根据返回结果判断是否成功成功匹配并删除 Redis 键值对，若果结果不为空和0，则验证通过
            if (result != null && result != 0L) {
                log.info("验证 token={},key={},value={} 成功", token, key, value);
                return true;
            }
            log.info("验证 token={},key={},value={} 失败", token, key, value);
            return false;
        }
    }

    @SneakyThrows
    @RequestMapping("many")
    public R many() {
        val r = R.ok();

        // 异步获取不到请求对象
        /*问题: 实际案例中, 这些异步请求都是调用微服务的
        本来是: 先调用A服务, 再调用B服务, 这俩个服务都可以从ThreadLocal获取RequestAttributes
        异步后: 非同一线程无法取到RequestContextHolder, 获取controller请求对象时会出现空指针异常
        解决方法: 获取主线程ServletRequestAttributes，给每个异步线程复制一份*/

        // 获取当前线程上下文环境器
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        // system, member
        CompletableFuture<R> systemFuture = CompletableFuture.supplyAsync(() -> {
            // 同步上下文环境器，解决异步的feign请求无法从ThreadLocal获取RequestAttributes
            RequestContextHolder.setRequestAttributes(requestAttributes);

            return systemFeignService.maven(false);
        }, executor);
        CompletableFuture<R> memberFuture = CompletableFuture.supplyAsync(() -> {
            // 同步上下文环境器，解决异步的feign请求无法从ThreadLocal获取RequestAttributes
            RequestContextHolder.setRequestAttributes(requestAttributes);

            return memberFeignService.maven(false);
        }, executor);

        CompletableFuture<Void> systemMavenName = systemFuture.thenAcceptAsync(v -> {
            System.out.println(v);
            r.dataAdd("systemMavenName", v.get("tree"));
        }, executor);

        CompletableFuture<Void> memberMavenName = memberFuture.thenAcceptAsync(v -> {
            System.out.println(v);
            r.dataAdd("memberMavenName", v.get("tree"));
        }, executor);

        // 防重令牌
        /*
        哪些情况需要防止：
        用户多次点击按钮
        用户页面回退再次提交
        微服务互相调用，由于网络问题，导致请求失败。feign触发重试机制
        其他业务情况
        例如update tab1 set col1=col1+1 where col2 = 2，每次执行结果不一样

        天然幂等性：
        1.查询接口
        2.更新接口update tab1 set col1=1 where col2=2
        3.delete from user where userId = 1
        4.insert user(userId, name) values(1, 'wan')，其中userId为主键
         */
        /*
        [token机制]
        服务器存储了一个令牌，页面请求时要带上令牌，服务器接收请求后会匹配令牌，匹配成功则删除令牌
        （再次提交则匹配失败，服务器已删除令牌。但是F5刷新的话就不一样了，会有新的token产生）
        注意：
        1.删除令牌要在执行业务代码之前
        2.获取redis令牌、令牌匹配、令牌删除要保证原子性（lua脚本）

        [各种锁机制]
        1.数据库悲观锁
        使用select* from xxx where id = 1 for update;查询的时候锁定该条数据
        注意：
        悲观锁使用时一般伴随事务一起使用，数据锁定时间可能会很长，需要根据实际情况选用。
        id字段一定是主键或者唯一索引，不然可能造成锁表的结果，处理起来会非常麻烦。

        2.数据库乐观锁【带上版本号】
        这种方法适合在更新的场景中
        update t_goods set count = count-1, version =version + 1 where good_id=2 and version = 1
        根据version版本，也就是在操作库存前先获取当前商品的version版本号，然后操作的时候带上此version号。
        第一次操作库存时，得到version为1，调用库存服务version变成了2﹔但返回给订单服务出现了问题，订单服务又一次发起调用库存服务，当订单服务传的version还是1，再执行上面的sal语句时，就不会执行﹔因为version已经变为2了，where条件就不成立。这样就保证了不管调用几次，只会真正的处理一次。
        乐观锁主要使用于处理读多写少的问题

        3.分布式锁：
        例如集群下多个定时器处理相同的数据，可以加分布式锁，锁定此数据，处理完成后释放锁。获取到锁的必须先判断这个数据是否被处理过（double check）

        [各种唯一约束]
        1.数据库唯一约束 order_sn字段【数据库层面】

        2.redis set防重【百度网盘妙传功能】
        需要处理的数据 计算MD5放入redis的set，每次处理数据，先看MD5是否存在，存在就不处理

        [防重表]
        数据库创建防重表，插入成功才可以操作【不采用，DB慢】
        使用订单号orderNo作为去重表唯一索引，然后将数据插入去重表+业务操作 放在同一事物中，如果插入失败事物回滚导致业务操作也同时回滚，（如果业务操作失败也会导致插入去重表回滚）保证了数据一致性

        [全局唯一id]
        调用接口时，生成一个唯一ID，redis将数据保存到集合中（去重），存在即处理过
        情景1：feign调用
        生成一个请求唯一ID，A调用B时带上唯一ID，B处理feign请求时判断此唯一ID是否已处理（feign重试时会带上相同ID）

        情景2：页面请求
        可以使用nginx设置每一个请求的唯一id，proxy_set_header X-Request-ld $request_id; 【链路追踪】
        但是没办法保证请求幂等性，因为每次请求nginx都会生成一个新的ID
         */
        /*
        [购物车页面]
        提交: items
        处理: 创建令牌锁
        按钮: 跳转到订单页面

        [订单页面]
        提交: items, 优惠券, 服务费, 运费
        处理: 检查有没有锁
              如果有锁那就解锁然后执行业务代码.
              没锁就说明是多次提交的请求, 检查下订单有没有成功创建, 如果有那就带用户去新页面, 如果没有那就是非法请求
        按钮: 提交订单, 跳转到订单支付页面

        [订单支付页面]

         */
        /*String token = tokenUtil.createToken();
        result.setUniqueToken(token);*/

        // 等待所有任务都完成
        CompletableFuture.allOf(systemFuture, memberFuture, systemMavenName, memberMavenName).get();

        return r;
    }


}