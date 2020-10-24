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

	@Autowired
	private Map<String, Game> gameMap;

	@Autowired
	private PlayerRepository playerRepository;

	public Game getGame(String gameId) {
		return gameMap.containsKey(gameId) ? gameMap.get(gameId) : null;
	}

	public Map<String, Game> getGameMap() {
		return gameMap;
	}

	public Game removeGame(String gameId) {
		Game game = gameMap.remove(gameId);
		if (game != null)
			playerRepository.removePlayers(Stream.of(game.getPlayers()).filter(Objects::nonNull).map(Player::getSessionId).collect(Collectors.toList()));
		return game;
	}

	public Game addGame(String gameId, Game game) {
		gameMap.put(gameId, game);
		return game;
	}

}
