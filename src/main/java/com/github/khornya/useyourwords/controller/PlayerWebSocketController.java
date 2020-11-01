package com.github.khornya.useyourwords.controller;

import com.github.khornya.useyourwords.exception.ElementNotFoundException;
import com.github.khornya.useyourwords.model.Answer;
import com.github.khornya.useyourwords.model.Game;
import com.github.khornya.useyourwords.model.message.Message;
import com.github.khornya.useyourwords.model.message.player.ErrorCode;
import com.github.khornya.useyourwords.model.message.player.ErrorMessageContent;
import com.github.khornya.useyourwords.model.message.player.GameCreationMessage;
import com.github.khornya.useyourwords.model.message.player.PlayerReadyMessage;
import com.github.khornya.useyourwords.service.ElementService;
import com.github.khornya.useyourwords.service.GameService;
import com.github.khornya.useyourwords.service.PlayerService;
import com.github.khornya.useyourwords.service.WebSocketService;
import com.github.khornya.useyourwords.utils.GameIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class PlayerWebSocketController {

	@Autowired
	private PlayerService playerService;

	@Autowired
	private GameService gameService;

	@Autowired
	private ElementService elementService;

	@Autowired
	WebSocketService webSocketService;

	@Autowired
	private GameIdGenerator gameIdGenerator;

	@MessageMapping("/join/{gameId}/{name}")
	public void join(@Header("simpSessionId") String sessionId, @DestinationVariable(value = "gameId") String gameId, @DestinationVariable(value = "name") String name) {
		playerService.addPlayer(sessionId, gameId, HtmlUtils.htmlEscape(name));
	}

	@MessageMapping("/create")
	public void create(@Header("simpSessionId") String sessionId, @Payload GameCreationMessage message) {
		String gameId;
		do {
			gameId = gameIdGenerator.get();
		} while (gameService.getGameById(gameId) != null);
		Game game = null;
		try {
			game = new Game(elementService, gameId, message.getNumOfPlayers(), message.getNumOfTeams(), message.getNumOfRounds());
		} catch (ElementNotFoundException e) {
			webSocketService.replyToUser(sessionId, new Message(Message.MessageType.ERROR, new ErrorMessageContent(ErrorCode.DATABASE_ERROR.toString(), "Pas assez d'éléments pour jouer")));
			return;
		}
		gameService.addGame(gameId, game);
		playerService.addPlayer(sessionId, gameId, HtmlUtils.htmlEscape(message.getName()));
	}

	@MessageMapping("/ready")
	public void ready(@Header("simpSessionId") String sessionId, @Payload PlayerReadyMessage message) {
		playerService.ready(sessionId, message.getGameId());
	}

	@MessageMapping("/answer/{gameId}")
	public void answer(@DestinationVariable(value = "gameId") String gameId, @Payload Answer answer) {
		gameService.addAnswer(gameId, answer);
	}

	@MessageMapping("/vote/{gameId}/{answerIndex}")
	public void vote(@Header("simpSessionId") String sessionId, @DestinationVariable(value = "gameId") String gameId, @DestinationVariable(value = "answerIndex") int answerIndex) {
		gameService.addVote(gameId, answerIndex, sessionId);
	}
}
