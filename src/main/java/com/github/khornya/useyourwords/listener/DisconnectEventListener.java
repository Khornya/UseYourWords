package com.github.khornya.useyourwords.listener;

import com.github.khornya.useyourwords.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class DisconnectEventListener {
    @Autowired
    private PlayerService playerService;

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        playerService.removePlayer(event.getSessionId());
    }
}
