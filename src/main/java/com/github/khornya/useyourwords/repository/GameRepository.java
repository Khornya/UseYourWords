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
	 * map to store waiting games
	 */
	@Autowired
	private Map<String, Game> waitingGameMap;

	/**
	 * map to store playing games
	 */
	@Autowired
	private Map<String, Game> playingGameMap;

	@Autowired
	private PlayerRepository playerRepository;

	/**
	 * get the playing game by gameId
	 * 
	 * @param gameId
	 *            gameId of that game
	 * @return game or null if the gameId not exists
	 */
	public Game getPlayingGame(String gameId) {
		return playingGameMap.containsKey(gameId) ? playingGameMap.get(gameId) : null;
	}

	/**
	 * get the waiting game by gameId
	 * 
	 * @param gameId
	 *            gameId of that game
	 * @return game or null if the gameId not exists
	 */
	public Game getWaitingGame(String gameId) {
		return waitingGameMap.containsKey(gameId) ? waitingGameMap.get(gameId) : null;
	}

	/**
	 * get the map store all waiting games
	 * 
	 * @return map store all waiting games
	 */
	public Map<String, Game> getWaitingGameMap() {
		return waitingGameMap;
	}

	/**
	 * get the map store all playing games
	 * 
	 * @return map store all playing games
	 */
	public Map<String, Game> getPlayingGameMap() {
		return playingGameMap;
	}

	/**
	 * remove the playing game, also update the player and game pair stored in
	 * playerRepository
	 * 
	 * @param gameId
	 *            gameId of playing game
	 * @return game object or null if the game not exists
	 */
	public Game removePlayingGame(String gameId) {
		Game t = playingGameMap.remove(gameId);
		if (t != null)
			playerRepository.removePlayer(Stream.of(t.getPlayers()).filter(Objects::nonNull).map(Player::getSessionId).collect(Collectors.toList()));
		return t;
	}

	/**
	 * remove the waiting game in the map
	 * 
	 * @param gameId
	 *            gameId of the game
	 * @return game object or null if the game not exists
	 */
	public Game removeWaitingGame(String gameId) {
		return waitingGameMap.remove(gameId);
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
	public Game addPlayingGame(String gameId, Game game) {
		playingGameMap.put(gameId, game);
		return game;
	}

	/**
	 * take a game object and store it to waiting game map
	 * 
	 * @param gameId
	 *            gameId of the game
	 * @param game
	 *            object extend abstractGame
	 * @return object added to waiting game map
	 */
	public Game addWaitingGame(String gameId, Game game) {
		waitingGameMap.put(gameId, game);
		return game;
	}

}
