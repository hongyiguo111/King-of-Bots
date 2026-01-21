package com.kob.backend.consumer;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.consumer.utils.Game;
import com.kob.backend.consumer.utils.JwtAuthentication;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    private static final ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>(); // 存所有链接, userId映射到WebSocketServer的实例
    private static final CopyOnWriteArraySet<User> matchpool = new CopyOnWriteArraySet<User>(); // 这个是啥

    private User user;
    private Session session = null;

    private static UserMapper userMapper;
    private Game game = null;

    @Autowired
    public void setUserMapper(UserMapper userMapper) { // 因为WebSocketServer不是单例模式，所以用这种方式注入
        WebSocketServer.userMapper = userMapper; // 静态变量用类名访问
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        // 建立连接
        this.session = session;
        System.out.println("connected!");
        Integer userId = JwtAuthentication.getUserId(token);
        this.user = userMapper.selectById(userId);

        if(this.user != null) {
            users.put(userId, this);
            System.out.println(user);
        } else  {
           this.session.close();
        }
        System.out.println(users);
    }

    @OnClose
    public void onClose() {
        // 关闭链接
        System.out.println("disconnected!");
        if (this.user != null) {
            users.remove(this.user.getId());
            matchpool.remove(this.user);
        }
    }

    private void startMatching() {
        System.out.println("start Matching");
        matchpool.add(this.user);

        while (matchpool.size() >= 2) {
            Iterator<User> iterator = matchpool.iterator();
            User a  = iterator.next();
            User b = iterator.next();
            matchpool.remove(a);
            matchpool.remove(b);

            Game game = new Game(13, 14, 20);
            game.createMap();

            JSONObject respA = new JSONObject();
            respA.put("event", "start-matching");
            respA.put("opponent_username", b.getUsername());
            respA.put("opponent_photo", b.getPhoto());
            respA.put("gamemap", game.getG());
            users.get(a.getId()).sendMessage(respA.toJSONString());

            JSONObject respB = new JSONObject();
            respB.put("event", "start-matching");
            respB.put("opponent_username", a.getUsername());
            respB.put("opponent_photo", a.getPhoto());
            respB.put("gamemap", game.getG());
            users.get(b.getId()).sendMessage(respB.toJSONString());

        }
    }

    private void stopMatching() {
        System.out.println("stop Matching");
        matchpool.remove(this.user);
    }

    @OnMessage
    public void onMessage(String message, Session session) { // 把onMessage当作路由
        // 从Client接收消息
        System.out.println("received message: " + message);
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event"); // 取出前端传入的event
        if("start-matching".equals(event)) {
            startMatching();
        } else if("stop-matching".equals(event)) {
            stopMatching();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    // 后端向前端发消息
    public void sendMessage(String message) {
        synchronized (this.session) {
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}