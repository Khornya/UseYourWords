package com.github.khornya.useyourwords.controller;

import com.github.khornya.useyourwords.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import static java.lang.String.format;

@Controller
public class PlayerWebSocketController {

	@Autowired
	private PlayerService playerService;

	@MessageMapping("/join/{gameId}/{name}")
	public void join(@Header("simpSessionId") String sessionId, @DestinationVariable(value = "gameId") String gameId, @DestinationVariable(value = "name") String name) {
		System.out.println(format("Player %s wants to join game n°%s", name, gameId));
		if (gameId.equals("null")) {
			playerService.addPlayer(sessionId, name);
		} else {
			playerService.addPlayer(sessionId, gameId, name);
		}
	}

	@MessageMapping("/ready/{gameId}")
	public void ready(@Header("simpSessionId") String sessionId, @DestinationVariable("gameId") String gameId) {
		playerService.ready(sessionId, gameId);
	}

}
