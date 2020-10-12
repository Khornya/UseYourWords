package com.github.khornya.useyourwords.service;

import com.github.khornya.useyourwords.model.Game;
import com.github.khornya.useyourwords.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

	@Autowired
	private GameRepository gameRepository;

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
	 * @param i    index of the player who leaved the game
	 */
	public void playerLeaved(Game game, int i) {

	}

	/**
	 * handle the game is start
	 *
	 * @param gameId gameId of the game needed to start
	 */
	public void start(String gameId) {

	}

}
