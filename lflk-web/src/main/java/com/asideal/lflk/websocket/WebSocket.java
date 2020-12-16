package com.asideal.lflk.websocket;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@ServerEndpoint("/websocket/{userName}")
@Log4j2
//此注解相当于设置访问URL
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();
    private static Map<String, Session> sessionPool = new HashMap<String, Session>();
    private Queue<Map<String, Object>> queueCpu = new LinkedList<>();
    private Queue<Map<String, Object>> queueMemory = new LinkedList<>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userName") String userName) {
        this.session = session;
        webSockets.add(this);
        sessionPool.put(userName, session);
        log.info(userName + "【websocket消息】有新的连接，总数为:" + webSockets.size());
        //sendOneMessage(userName, ""StrUtil.nullToDefault(lastMessage,"")"");
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
        log.info("【websocket消息】连接断开，总数为:" + webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端消息:" + message);
    }

    // 此为广播消息
    public void sendAllMessage(String message) {
        for (WebSocket webSocket : webSockets) {
            log.info("【websocket消息】广播消息:" + message);
            try {
                webSocket.session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息
    public void sendOneMessage(String userName, String message) {
        log.info("【websocket消息】单点消息:" + message);
        Session session = sessionPool.get(userName);
        if (session != null) {
            try {
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void queueCpuOffer(Map<String, Object> map) {
        if (queueCpu.size() >= 10) {
            queueCpu.poll();
        }
        queueCpu.offer(map);
    }

    public void queueMemoryOffer(Map<String, Object> map) {
        if (queueMemory.size() >= 10) {
            queueMemory.poll();
        }
        queueMemory.offer(map);
    }

    public Queue<Map<String, Object>> getQueueCpu() {
        return queueCpu;
    }

    public Queue<Map<String, Object>> getQueueMemory() {
        return queueMemory;
    }
}