package com.github.khornya.useyourwords.config;

import com.github.khornya.useyourwords.model.Game;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class GameRoomConfig {

	@Bean(name = "playerGameMap")
	public Map<String, String> getPlayerGameMap() {
		return new ConcurrentHashMap<>();
	}

	@Bean(name = "gameMap")
	public Map<String, Game> getGameMap() {
		return new ConcurrentHashMap<>();
	}
}
