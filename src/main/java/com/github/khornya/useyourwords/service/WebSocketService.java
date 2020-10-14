package com.github.khornya.useyourwords.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.khornya.useyourwords.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebSocketService {

	private Logger logger = LoggerFactory.getLogger(WebSocketService.class);

	@Value(value = "${websocket.destination.prefix.broker}")
	private String brokerDestinationPrefix;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	public void replyToUser(String sessionId, Message message) {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> playerPayload = objectMapper.convertValue(message, Map.class);
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        simpMessagingTemplate.convertAndSendToUser(sessionId, "/queue/reply", playerPayload, headerAccessor.getMessageHeaders());
    }

    public void sendToRoom(String gameId, Message message) {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String,Object> gameRoomPayload = objectMapper.convertValue(message, Map.class);
		simpMessagingTemplate.convertAndSend(String.format("%s/%s",brokerDestinationPrefix, gameId), gameRoomPayload);
	}

}
