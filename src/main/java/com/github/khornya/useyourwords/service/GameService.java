package com.github.khornya.useyourwords.service;

import com.github.khornya.useyourwords.model.Game;
import org.springframework.stereotype.Service;

@Service
public class GameService {

	/**
	 * return the new object extends abstractGame.
	 *
	 * @return object extends abstractGame
	 */
	Game newGame() {
		return new Game();
	}

	/**
	 * handle a player has won, and the game is ended.
	 *
	 * @param gameId      gameId of the game
	 * @param playerIndex index of the player who won the game
	 */
	void playerWin(String gameId, int playerIndex) {
	}

	/**
	 * handle a player leaved the game
	 *
	 * @param game object extends abstractGame
	 * @param i    index of the player who leaved the game
	 */
	void playerLeaved(Game game, int i) {

	}

	/**
	 * handle the game is start
	 *
	 * @param gameId gameId of the game needed to start
	 */
	void start(String gameId) {

	}

}
