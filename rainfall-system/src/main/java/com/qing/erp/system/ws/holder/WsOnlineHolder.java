package com.qing.erp.system.ws.holder;

import com.qing.erp.system.ws.pojo.UserEntity;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class WsOnlineHolder {
    // session
    public static final CopyOnWriteArrayList<UserEntity> sessions = new CopyOnWriteArrayList<>();

    // 在线人数
    public static final AtomicInteger onlineCount = new AtomicInteger(0);

    // 在线人数(巅峰)
    public static volatile int peakOnlineCount = 0;

    // 用户列表
    public static final CopyOnWriteArrayList<Integer> userIdList = new CopyOnWriteArrayList<>();
}
