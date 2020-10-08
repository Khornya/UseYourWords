package com.github.khornya.useyourwords.service;

import com.github.khornya.useyourwords.model.Game;
import com.github.khornya.useyourwords.model.Player;
import com.github.khornya.useyourwords.repository.GameRepository;
import com.github.khornya.useyourwords.repository.PlayerRepository;
import com.github.khornya.useyourwords.utils.PlayerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlayerService extends WebSocketService {

	private Logger logger = LoggerFactory.getLogger(PlayerService.class);

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private GameService gameService;

	@Autowired
	private PlayerUtils playerUtils;

	@Value(value = "${websocket.gameroom.config.numof.player}")
	private int numOfPlayer;

	public String addPlayer(String sessionId, String name) {
		logger.info("addPlayer, sessionId: ", sessionId);
		Map<String, Game> waitingGameMap = gameRepository.getWaitingGameMap();
		Game game = null;
		game = waitingGameMap.values().stream().filter(g -> g.getJoinCount().getAndIncrement() <= numOfPlayer).findFirst().orElse(null);
		// create new game, if no available waiting game exists
		if (game == null) {
			game = gameService.newGame();
			waitingGameMap.put(game.getId(), game);
		}
		return addPlayer(new Player(name, sessionId), game);
	}

	public String addPlayer(String sessionId, String gameId, String name) {
		logger.info("addPlayer, sessionId: ", sessionId, ", gameId: ", gameId, ", name: ", name);
		Game game = gameRepository.getWaitingGame(gameId);
		if (game != null && game.getJoinCount().incrementAndGet() <= numOfPlayer)
			synchronized (game) {
				return addPlayer(new Player(name, sessionId), game);
			}
		else
			sendTo("joined", sessionId, "error", true);
		return null;
	}

	private String addPlayer(Player player, Game game) {
		String gameId = null;
		int i = 0;
		logger.info("addPlayer, player: ", player, ", game: ", game);
		if (game == null)
			return null;
		Player[] players = game.getPlayers();

		for (; i < players.length; i++) {
			if (players[i] == null) {
				players[i] = player;
				break;
			}
		}
		gameId = game.getId();
		playerRepository.addPlayer(player.getSessionId(), gameId);
		sendTo("joined", player.getSessionId(), new String[] { "error", "game-id", "index" }, new Object[] { false, gameId, i });
		return gameId;
	}

	public void removePlayer(String sessionId) {
		logger.info("removePlayer, sessionId: ", sessionId);
		Map<String, String> playerGameMap = playerRepository.getPlayerGameMap();
		if (!playerGameMap.containsKey(sessionId))
			return;
		String gameId = playerGameMap.get(sessionId);
		Game game = gameRepository.getWaitingGame(gameId);
		boolean isWaiting = true;
		if (game == null) {
			game = gameRepository.getPlayingGame(gameId);
			isWaiting = false;
		}
		synchronized (game) {
			removePlayer(sessionId, isWaiting, game);
		}
	}

	private void removePlayer(String sessionId, Boolean isWaiting, Game game) {
		logger.info("removePlayer, sessionId: ", sessionId, ", isWaiting: ", isWaiting, ", abstractGame: ", game);
		Player[] players;
		String gameId = game.getId();
		playerRepository.removePlayer(sessionId);
		players = game.getPlayers();
		int i;
		for (i = 0; i < players.length; i++) {
			if (players[i] != null && players[i].getSessionId().equals(sessionId)) {
				players[i] = null;
				break;
			}
		}
		game.removeReadyPlayer(sessionId);
		if (!isWaiting) {
			if (game.getReadyPlayersSize() == 1) {
				gameService.playerWin(gameId, playerUtils.getLastPlayerIndex(players));
				return;
			}
			gameService.playerLeaved(game, i);
		}
		send("player-list", gameId, "players", players);
	}

	public void ready(String sessionId, String gameId) {
		logger.info("ready, sessionId: ", sessionId, " , gameId: ", gameId);
		Game game = gameRepository.getWaitingGame(gameId);
		if (game == null)
			return;
		send("player-list", gameId, "players", game.getPlayers());
		synchronized (game) {
			game.addReadyPlayer(sessionId);
			if (game.getReadyPlayersSize() == numOfPlayer)
				gameService.start(gameId);
		}
	}

}
