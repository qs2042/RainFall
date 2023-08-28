package com.qing.erp.system.ws.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qing.erp.system.ws.holder.WsOnlineHolder;
import com.qing.erp.system.ws.pojo.MessageEntity;
import com.qing.erp.system.ws.pojo.UserEntity;
import lombok.val;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WebSocketUtil {

    // 发送消息给指定客户端
    public static void sendMessage(WebSocketSession session, MessageEntity message) throws IOException {
        if (!session.isOpen()) {
            // TODO: 要发送消息, 但是session关闭了, 写到日志中
            return;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
    }

    // 发送消息给所有客户端
    public static void sendMessageToAll(MessageEntity message) throws Exception {
        for (UserEntity user : WsOnlineHolder.sessions) {
            val session = user.getSession();
            sendMessage(session, message);
        }
    }
}
