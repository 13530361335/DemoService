package com.joker.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Joker Jing
 * @date 2019/7/29
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{username}")
public class WebSocketService {

    private static final String ALL_USERS = "all";

    private static Map<String, WebSocketService> clients = new ConcurrentHashMap<>();
    private Session session;
    private String username;

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) {
        this.username = username;
        this.session = session;
        clients.put(username, this);
        log.info(username + "加入连接,在线人数" + clients.size());
        Set<String> online = getClients().keySet();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("online", online);
        sendAll(jsonObject.toJSONString());
    }

    @OnMessage
    public void onMessage(String message) {
        JSONObject json = JSONObject.parseObject(message);
        String to = json.getString("to");
        String data = json.getString("data");
        if (ALL_USERS.equals(to)) {
            sendAll(data);
        } else {
            sendOne(to, data);
        }
    }

    @OnClose
    public void onClose() {
        clients.remove(username);
        log.info(username + "断开连接,在线人数" + clients.size());
    }

    @OnError
    public void onError(Throwable error) {
        clients.remove(username);
        log.error(error.getMessage(), error);
        log.info(username + "断开连接,在线人数" + clients.size());
    }

    public void sendOne(String to, String message) {
        WebSocketService webSocketService = clients.get(to);
        if (webSocketService == null) {
            log.error(to + "不在线");
        } else {
            webSocketService.session.getAsyncRemote().sendText(message);
        }
    }

    public void sendAll(String message) {
        clients.forEach((k, v) ->
                v.session.getAsyncRemote().sendText(message)
        );
    }

    public static synchronized Map<String, WebSocketService> getClients() {
        return clients;
    }
}