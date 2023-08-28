package com.qing.erp.system.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;


/*
0. 创建订单(创建队列, 一直查看订单的状态)
0. 锁库存(创建队列, 一直查看订单的状态)
    如果因为超时导致订单失效, 或是订单取消, 那就将库存加回去
0. 扣减积分(创建队列, 一直查看订单的状态)
    如果因为超时导致订单失效, 或是订单取消, 那就将积分加回去

因为创建订单是高并发场景，不采用Seata（默认Seata是采用AT模式【2PC模式的变种】，性能低）
采用方案：【柔性事务】
保证AP，采用本地事务+延时队列+监听死信队列解锁库存 的方案实现最终一致性

可靠消息+最终一致性：
锁库存时，往队列发送一条库存解锁消息（在队列中设置超时时间而不是在消息中设置）
消息超时后经过死信路由到达延时队列，解锁库存service监听延时队列，查询订单状态判断是否需要解锁库存

订单模块一个延时队列+死信队列，用于30min关闭订单
库存模块一个延时队列+死信队列，用于40min解锁库存
优惠模块一个延时队列+死信队列，用于40min还原积分
 */

/**
 * 创建队列，交换机，延时队列，绑定关系 的configuration
 * 1.Broker中的Queue、Exchange、Binding不存在的情况下，会自动创建（在RabbitMQ），不会重复创建覆盖
 * 2.懒加载，只有第一次使用的时候才会创建（例如监听队列）
 */
//@Configuration
public class MyRabbitMQConfig {

    /**
     * 延时队列
     */
    @Bean
    public Queue orderDelayQueue() {
        /**
         * Queue(String name,  队列名字
         *       boolean durable,  是否持久化
         *       boolean exclusive,  是否排他
         *       boolean autoDelete, 是否自动删除
         *       Map<String, Object> arguments) 属性【TTL、死信路由、死信路由键】
         */
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "order-event-exchange");// 死信路由
        arguments.put("x-dead-letter-routing-key", "order.release.order");// 死信路由键
        arguments.put("x-message-ttl", 60000); // 消息过期时间 1分钟
        return new Queue("order.delay.queue", true, false, false, arguments);
    }

    /**
     * 交换机（死信路由）
     */
    @Bean
    public Exchange orderEventExchange() {
        return new TopicExchange("order-event-exchange", true, false);
    }

    /**
     * 死信队列
     */
    @Bean
    public Queue orderReleaseQueue() {
        return new Queue("order.release.order.queue", true, false, false);
    }

    /**
     * 绑定：交换机与订单解锁延迟队列
     */
    @Bean
    public Binding orderCreateBinding() {
        /**
         * String destination, 目的地（队列名或者交换机名字）
         * DestinationType destinationType, 目的地类型（Queue、Exhcange）
         * String exchange,
         * String routingKey,
         * Map<String, Object> arguments
         **/
        return new Binding("order.delay.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.create.order",
                null);
    }

    /**
     * 绑定：交换机与订单解锁死信队列
     */
    @Bean
    public Binding orderReleaseBinding() {
        return new Binding("order.release.order.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.release.order",
                null);
    }

    /**
     * 绑定：交换机与库存解锁
     */
    @Bean
    public Binding orderReleaseOtherBinding() {
        return new Binding("stock.release.stock.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.release.other.#",
                null);
    }

    /**
     * 秒杀订单队列（削峰）
     */
    @Bean
    public Queue orderSecKillOrderQueue() {
        Queue queue = new Queue("order.seckill.order.queue", true, false, false);
        return queue;
    }

    /**
     * 绑定：交换机与秒杀订单
     */
    @Bean
    public Binding orderSecKillOrderQueueBinding() {
        // 其他地方调用: rabbitTemplate.convertAndSend("order-event-exchange", "order.seckill.order", message);

        //String destination, DestinationType destinationType, String exchange, String routingKey
        Binding binding = new Binding(
                "order.seckill.order.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.seckill.order",
                null);
        return binding;
    }

}
