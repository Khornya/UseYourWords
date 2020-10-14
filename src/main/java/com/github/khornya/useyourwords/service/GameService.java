package com.github.khornya.useyourwords.service;

import com.github.khornya.useyourwords.model.Game;
import com.github.khornya.useyourwords.model.message.game.GameStartMessageContent;
import com.github.khornya.useyourwords.model.message.Message;
import com.github.khornya.useyourwords.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		GameStartMessageContent gameStartMessageContent = new GameStartMessageContent(getGameById(gameId).getTeams());
		Message gameRoomMessage = new Message(Message.MessageType.START, gameStartMessageContent);
		webSocketService.sendToRoom(gameId, gameRoomMessage);
	}

}
