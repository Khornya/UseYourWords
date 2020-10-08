package com.github.khornya.useyourwords.config;

import com.github.khornya.useyourwords.model.Game;
import com.github.khornya.useyourwords.utils.PlayerUtils;
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

	@Bean(name = "playingGameMap")
	public Map<String, Game> getPlayingGameMap() {
		return new ConcurrentHashMap<>();
	}

	@Bean(name = "waitingGameMap")
	public Map<String, Game> getWaitingGameMap() {
		return new ConcurrentHashMap<>();
	}

	@Bean
	public PlayerUtils getPlayerUtils() {
		return new PlayerUtils();
	}

}
