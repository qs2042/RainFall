package com.qing.erp.system.ws.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WsTestHandler extends TextWebSocketHandler {
    // 新连接加入
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("加入: " + session);
    }

    // 连接断开
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("离开: " + session);
    }

    // 处理客户端发送的消息
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    }
}