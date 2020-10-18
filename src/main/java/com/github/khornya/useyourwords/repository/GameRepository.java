package com.github.khornya.useyourwords.repository;

import com.github.khornya.useyourwords.model.Game;
import com.github.khornya.useyourwords.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class GameRepository {

	/**
	 * map to store playing games
	 */
	@Autowired
	private Map<String, Game> gameMap;

	@Autowired
	private PlayerRepository playerRepository;

	/**
	 * get the playing game by gameId
	 * 
	 * @param gameId
	 *            gameId of that game
	 * @return game or null if the gameId not exists
	 */
	public Game getGame(String gameId) {
		return gameMap.containsKey(gameId) ? gameMap.get(gameId) : null;
	}

	/**
	 * get the map store all playing games
	 * 
	 * @return map store all playing games
	 */
	public Map<String, Game> getGameMap() {
		return gameMap;
	}

	/**
	 * remove the playing game, also update the player and game pair stored in
	 * playerRepository
	 * 
	 * @param gameId
	 *            gameId of playing game
	 * @return game object or null if the game not exists
	 */
	public Game removeGame(String gameId) {
		Game game = gameMap.remove(gameId);
		if (game != null)
			playerRepository.removePlayers(Stream.of(game.getPlayers()).filter(Objects::nonNull).map(Player::getSessionId).collect(Collectors.toList()));
		return game;
	}

	/**
	 * take a game object and store it to playing game map
	 * 
	 * @param gameId
	 *            gameId of the game
	 * @param game
	 *            object extend abstractGame
	 * @return object added to playing game map
	 */
	public Game addGame(String gameId, Game game) {
		gameMap.put(gameId, game);
		return game;
	}

}
