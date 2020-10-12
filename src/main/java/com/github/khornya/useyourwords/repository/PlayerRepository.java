package com.github.khornya.useyourwords.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PlayerRepository {

	// TODO move to redis ?
	@Autowired
	private Map<String, String> playerGameMap;

	public Map<String, String> getPlayerGameMap() {
		return playerGameMap;
	}

	public String removePlayer(String sessionId) {
		return playerGameMap.remove(sessionId);
	}

	public void removePlayers(List<String> sessionIds) {
		playerGameMap.keySet().removeAll(sessionIds);
	}

	public void addPlayer(String sessionId, String gameId) {
		playerGameMap.put(sessionId, gameId);
	}

}
