package com.qing.erp.system.ws.handler;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.qing.erp.common.web.WebUtil;
import com.qing.erp.system.ws.holder.WsOnlineHolder;
import com.qing.erp.system.ws.pojo.UserEntity;
import com.qing.erp.system.ws.util.WebSocketUtil;
import com.qing.erp.system.ws.constant.MessageType;
import com.qing.erp.system.ws.pojo.MessageEntity;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@Getter
public class WsOnlineHandler extends TextWebSocketHandler {
    String uri = "/onLine/{account}/{id}";

    // 处理客户端消息
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // payload
        String payload = message.getPayload();

        // 将JSON内容转为Map或者实体类
        MessageEntity msg = null;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(MessageType.class, new MessageEntity.MessageTypeAdapter())
                .create();
        try {
            msg = gson.fromJson(payload, MessageEntity.class);
        } catch(JsonSyntaxException e) {
            WebSocketUtil.sendMessage(
                    session, MessageEntity.build(MessageType.ERROR_MESSAGE, "消息的格式错误")
            );
            return;
        }

        // 使用switch, 根据消息类型进行一一处理
        MessageEntity res = null;
        switch (msg.getType()) {
            // 普通消息
            case NORMAL_MESSAGE:
                res = MessageEntity.build(MessageType.PRIVATE_CHAT, "hello");
                break;
            // 获取
            case GET_USER_ID_LIST:
                res = MessageEntity.build(MessageType.PRIVATE_CHAT, "hello");
                val list = new ArrayList<>(WsOnlineHolder.sessions);
                res.addData("list", list);
                break;
            default:
                res = MessageEntity.build(MessageType.ERROR_MESSAGE, "暂未完成");
        }
        WebSocketUtil.sendMessage(session, res);
    }

    // 处理WS连接建立事件
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // TODO: 写入日志
        log.info("[ws] session建立: {}", session);

        URI uri = session.getUri();

        Map<String, String> result = WebUtil.extractPathVariables(getUri(), uri.getPath());
        val account = Optional.of(result.get("account"));
        val id = Optional.of(result.get("id")).map(Integer::valueOf);
        if (!account.isPresent() || !id.isPresent()) {
            WebSocketUtil.sendMessage(session, MessageEntity.build(MessageType.PRIVATE_CHAT, "错误的格式"));
            session.close();
            return;
        }

        // 保存session TODO: 不确定要不要限制IP, 重复IP就不记录了
        WsOnlineHolder.sessions.add(UserEntity.build(id.get(), account.get(), session, null));

        // 更新在线人数
        int count = WsOnlineHolder.onlineCount.incrementAndGet();
        if (count > WsOnlineHolder.peakOnlineCount) {
            WsOnlineHolder.peakOnlineCount = count;
        }

        // 将最新消息发送给所有客户度
        val message = MessageEntity.build(MessageType.BROADCAST_MESSAGE, "有人加入");
        message.addData("peakOnlineCount", String.valueOf(WsOnlineHolder.peakOnlineCount));
        message.addData("onlineCount", String.valueOf(count));
        WebSocketUtil.sendMessageToAll(message);
    }

    // 处理WS连接关闭事件
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // TODO: 写入日志
        log.info("[ws] session销毁: {}", session);

        // 删除session
        val ou = WsOnlineHolder.sessions.stream()
                .filter(v -> session.getId().equals(v.getSession().getId()))
                .findFirst();
        if (ou.isPresent()) {
            // TODO: 不要真的删除, 整个逻辑删除就好了, 再加个今天登陆几次, 和总在线时长
            val isSuccess = WsOnlineHolder.sessions.remove(ou.get());
            System.out.println("删除是否成功: " + isSuccess);
        } else {
            System.out.println("特殊错误");
        }

        // 更新在线人数
        int count = WsOnlineHolder.onlineCount.decrementAndGet();

        // 将最新消息发送给所有客户度
        val message = MessageEntity.build(MessageType.BROADCAST_MESSAGE, "有人离开");
        message.addData("onlineCount", String.valueOf(count));
        WebSocketUtil.sendMessageToAll(message);
    }

    // 处理WS传输报错
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        // TODO: 写入日志
        log.error("[ws] session报错: {}", session, exception);

        // 将错误信息返回给客户端
        WebSocketUtil.sendMessage(
                session,
                MessageEntity.build(MessageType.NORMAL_MESSAGE, "出现错误")
        );
    }

}