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

	/**
	 * send message to path /${applicationDestationPrefix}/${gameId}/${path}, used
	 * to broadcast message to the users in that game who subscribed that path
	 * 
	 * @param path
	 *            the destination path
	 * @param gameId
	 *            the gameId of the game
	 * @param key
	 *            key of the message payload
	 * @param value
	 *            value of the message payload
	 */
	public void send(String path, String gameId, String key, Object value) {
		send(path, gameId, new String[] { key }, new Object[] { value });
	}

	/**
	 * send message to path /${applicationDestationPrefix}/${gameId}/${path}, used
	 * to broadcast message to the users in that game who subscribed that path
	 * 
	 * @param path
	 *            the destination path
	 * @param gameId
	 *            the gameId of the game
	 * @param keys
	 *            keys of the message payload
	 * @param values
	 *            values of the message payload
	 */
	public void send(String path, String gameId, String[] keys, Object[] values) {
		send(gameId + "/" + path, keys, values);
	}

	/**
	 * send message to path
	 * /${applicationDestationPrefix}/${gameId}/${path}-${sessionId}, used to send
	 * message to the specific user in that game who subscribed that path
	 * 
	 * @param path
	 *            the destination path
	 * @param sessionId
	 *            the sessionId of the user
	 * @param gameId
	 *            the gameId of the game
	 * @param key
	 *            key of the message payload
	 * @param value
	 *            value of the message payload
	 */
	public void sendTo(String path, String sessionId, String gameId, String key, Object value) {
		sendTo(path, sessionId, gameId, new String[] { key }, new Object[] { value });
	}

	/**
	 * send message to path
	 * /${applicationDestationPrefix}/${gameId}/${path}-${sessionId}, used to send
	 * message to the specific user in that game who subscribed that path
	 * 
	 * @param path
	 *            the destination path
	 * @param sessionId
	 *            the sessionId of the user
	 * @param gameId
	 *            the gameId of the game
	 * @param keys
	 *            key of the message payload
	 * @param values
	 *            value of the message payload
	 */
	public void sendTo(String path, String sessionId, String gameId, String[] keys, Object[] values) {
		send(gameId + "/" + path + "-" + sessionId, keys, values);
	}

	/**
	 * send message to path
	 * /${applicationDestationPrefix}/${gameId}/${path}-${sessionId}, used to send
	 * message to the specific user who subscribed that path
	 * 
	 * @param path
	 *            the destination path
	 * @param sessionId
	 *            the sessionId of the user
	 * @param keys
	 *            key of the message payload
	 * @param values
	 *            value of the message payload
	 */
	public void sendTo(String path, String sessionId, String[] keys, Object[] values) {
		send(path + "-" + sessionId, keys, values);
	}

	/**
	 * send message to path /${applicationDestationPrefix}/${path}-${sessionId},
	 * used to send message to the specific user who subscribed that path
	 * 
	 * @param path
	 *            the destination path
	 * @param sessionId
	 *            the gameId of the game
	 * @param key
	 *            key of the message payload
	 * @param value
	 *            value of the message payload
	 */
	public void sendTo(String path, String sessionId, String key, Object value) {
		sendTo(path, sessionId, new String[] { key }, new Object[] { value });
	}

	private void send(String path, String[] keys, Object[] values) {
		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < keys.length; i++) {
			map.put(keys[i], values[i]);
		}
		logger.info("send to path: ", path, ", data: ", map);
		simpMessagingTemplate.convertAndSend(brokerDestinationPrefix + "/" + path, map);
	}

}
