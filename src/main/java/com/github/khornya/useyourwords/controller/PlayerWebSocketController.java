package com.github.khornya.useyourwords.controller;

import com.github.khornya.useyourwords.model.Game;
import com.github.khornya.useyourwords.model.GameCreationMessage;
import com.github.khornya.useyourwords.service.GameService;
import com.github.khornya.useyourwords.service.PlayerService;
import com.github.khornya.useyourwords.service.WebSocketService;
import com.github.khornya.useyourwords.utils.GameIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class PlayerWebSocketController {

	@Autowired
	private PlayerService playerService;

	@Autowired
	private GameService gameService;

	@Autowired
	private GameIdGenerator gameIdGenerator;

	@Autowired
	private WebSocketService webSocketService;

	@Value(value = "${websocket.destination.prefix.broker}")
	private String brokerDestinationPrefix;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/join/{gameId}/{name}")
	public void join(@Header("simpSessionId") String sessionId, @DestinationVariable(value = "gameId") String gameId, @DestinationVariable(value = "name") String name) {
		playerService.addPlayer(sessionId, gameId, name);
	}

	@MessageMapping("/create")
	public void create(@Header("simpSessionId") String sessionId, @Payload GameCreationMessage message) {
		String gameId;
		do {
			gameId = gameIdGenerator.get();
		} while (gameService.getGameById(gameId) != null);
		Game game = new Game(gameId, message.getNumOfPlayers(), message.getNumOfTeams(), message.getNumOfRounds());
		gameService.addGame(gameId, game);
		playerService.addPlayer(sessionId, gameId, message.getName());
	}
}
