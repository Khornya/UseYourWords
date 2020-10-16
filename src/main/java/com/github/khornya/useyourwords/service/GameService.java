package com.github.khornya.useyourwords.service;

import com.github.khornya.useyourwords.model.Element;
import com.github.khornya.useyourwords.model.ElementType;
import com.github.khornya.useyourwords.model.Game;
import com.github.khornya.useyourwords.model.message.game.GameRoundMessageContent;
import com.github.khornya.useyourwords.model.message.game.GameStartMessageContent;
import com.github.khornya.useyourwords.model.message.Message;
import com.github.khornya.useyourwords.model.message.game.TimerMessageContent;
import com.github.khornya.useyourwords.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private WebSocketService webSocketService;

	public void addGame(String gameId, Game game) {
		gameRepository.addGame(gameId, game);
	}

	public Game getGameById(String gameId) {
		return gameRepository.getGame(gameId);
	}

	/**
	 * handle a player has won, and the game is ended.
	 *
	 * @param gameId      gameId of the game
	 * @param playerIndex index of the player who won the game
	 */
	public void playerWin(String gameId, int playerIndex) {
	}

	/**
	 * handle a player leaved the game
	 *
	 * @param game object extends abstractGame
	 * @param playerIndex    index of the player who leaved the game
	 */
	public void playerLeaved(Game game, int playerIndex) {

	}

	/**
	 * handle the game is start
	 *
	 * @param gameId gameId of the game needed to start
	 */
	public void start(String gameId) {
		Game game = getGameById(gameId);
		GameStartMessageContent gameStartMessageContent = new GameStartMessageContent(game.getTeams());
		Message gameRoomMessage = new Message(Message.MessageType.START, gameStartMessageContent);
		webSocketService.sendToRoom(gameId, gameRoomMessage);
		nextRound(game);
	}

	private void nextRound(Game game) {
		String gameId = game.getId();
		Element element = game.nextRound();
		GameRoundMessageContent gameRoundMessageContent = new GameRoundMessageContent(game.getCurrentRoundNumber(), element);
		Message gameRoomMessage = new Message(Message.MessageType.NEXT_ROUND, gameRoundMessageContent);
		webSocketService.sendToRoom(gameId, gameRoomMessage);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimerMessageContent timerMessageContent = new TimerMessageContent(10);
				Message gameRoomMessage = new Message(Message.MessageType.TIMER, timerMessageContent);
				webSocketService.sendToRoom(gameId, gameRoomMessage);
			}
		}, 50 * 1000);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Message gameRoomMessage = new Message(Message.MessageType.END_ROUND, null);
				webSocketService.sendToRoom(gameId, gameRoomMessage);
				askForVotes(game);
			}
		}, 60 * 1000);
	}

	private void askForVotes(Game game) {

	}


}
