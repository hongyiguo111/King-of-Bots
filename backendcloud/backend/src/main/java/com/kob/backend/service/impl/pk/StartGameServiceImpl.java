package com.kob.backend.service.impl.pk;

import com.kob.backend.consumer.WebSocketServer;
import com.kob.backend.service.pk.StartGameService;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
public class StartGameServiceImpl implements StartGameService {
    private final WebSocketServer webSocketServer;

    public StartGameServiceImpl(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    @Override
    public String startGame(Integer aId, Integer bId) {
        System.out.println("startGame: " + aId + ", " + bId);
        WebSocketServer.startGame(aId, bId);
        return "start game success";
    }
}
