package com.qing.erp.system.ws.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;

@AllArgsConstructor
public enum MessageType {
    // 0-99 基础类型
    TEXT_MESSAGE(0, "文本消息"),
    BINARY_MESSAGE(1, "二进制消息"),
    PING_MESSAGE(2, "ping 消息"),
    PONG_MESSAGE(3, "pong 消息"),
    CLOSE_MESSAGE(4, "关闭消息"),
    SUBSCRIBE_MESSAGE(5, "订阅消息"),
    UNSUBSCRIBE_MESSAGE(6, "取消订阅消息"),
    ERROR_MESSAGE(7, "错误消息"),
    HEARTBEAT_MESSAGE(8, "心跳消息"),
    BROADCAST_MESSAGE(9, "广播消息"),
    RESPONSE_MESSAGE(10, "回信消息"),

    // 100-199 进阶类型
    GROUP_CHAT(100, "群聊消息"),
    PRIVATE_CHAT(101, "私聊消息"),
    NORMAL_MESSAGE(102, "普通消息"),
    ADMIN_MESSAGE(103, "管理员消息"),
    SYSTEM_MESSAGE(104, "系统消息"),
    SYSTEM_DATA(105, "系统数据"),
    EMAIL(106, "邮件消息"),
    SOCIAL_MEDIA(107, "社交媒体消息"),
    NOTIFICATION(108, "通知消息"),
    TRANSACTION(109, "交易消息"),
    GAME(110, "游戏消息"),
    IOT(111, "物联网消息"),
    FINANCE(112, "金融消息"),
    HEALTH_CARE(113, "医疗保健消息"),
    EDUCATION(114, "教育消息"),
    NEWS(115, "新闻消息"),

    // 200-299 功能类型
    GET_USER_ID_LIST(200, "获取用户列表"),
    LOGIN(299, "登录账号"),
    LOGOUT(299, "退出登录"),

    NULL(99999, "99999");

    private final int id;
    private final String desc;

    private static final HashMap<Integer, MessageType> lookup = new HashMap<>();

    static {
        for (MessageType messageType : MessageType.values()) {
            lookup.put(messageType.getId(), messageType);
        }
    }

    @JsonValue
    public int getId() {
        return id;
    }

    @JsonCreator
    public static MessageType fromValue(int value) {
        return lookup.get(value);
    }

    public static void main(String[] args) {
        System.out.println(MessageType.valueOf("GROUP_CHAT"));
        System.out.println(MessageType.fromValue(100));
    }
}
