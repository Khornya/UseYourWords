package com.github.khornya.useyourwords.service;

import com.github.khornya.useyourwords.model.Game;
import com.github.khornya.useyourwords.model.Message;
import com.github.khornya.useyourwords.model.Player;
import com.github.khornya.useyourwords.repository.GameRepository;
import com.github.khornya.useyourwords.repository.PlayerRepository;
import com.github.khornya.useyourwords.utils.GameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    private WebSocketService webSocketService;

    @Autowired
    private GameUtils gameUtils;

    @Value(value = "${websocket.gameroom.config.numof.player}")
    private int numOfPlayer;

    public void addPlayer(String sessionId, String gameId, String name) {
        logger.info("addPlayer, sessionId: ", sessionId, ", gameId: ", gameId, ", name: ", name);
        Game game = gameRepository.getGame(gameId);
        if (game != null && game.getJoinCount().incrementAndGet() <= game.getPlayers().length)
            addPlayer(new Player(name, sessionId), game);
        else {
            Map<String, Object> playerPayload = Map.of("type", Message.MessageType.ERROR, "message", "Unable to join this game");
            webSocketService.replyToUser(sessionId, playerPayload);
        }
    }

    private void addPlayer(Player player, Game game) {
        String gameId = null;
        int i = 0;
        logger.info("addPlayer, player: ", player, ", game: ", game);
        Player[] players = game.getPlayers();

        for (; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = player;
                break;
            }
        }
        gameId = game.getId();
        playerRepository.addPlayer(player.getSessionId(), gameId);
        // prévenir la room de l'arrivée d'un joueur
        Map<String, Object> gameRoomPayload = new HashMap<>();
        gameRoomPayload.put("type", Message.MessageType.PLAYER_JOINED);
        gameRoomPayload.put("name", player.getName());
        webSocketService.sendToRoom(gameId, gameRoomPayload);
        // prévenir le joueur qu'il rejoint une partie
        Map<String, Object> playerPayload = Map.of("type", Message.MessageType.JOINED, "gameId", gameId, "name", player.getName(), "index", i);
        webSocketService.replyToUser(player.getSessionId(), playerPayload);
    }

    public void removePlayer(String sessionId) {
        logger.info("removePlayer, sessionId: ", sessionId);
        Map<String, String> playerGameMap = playerRepository.getPlayerGameMap();
        if (!playerGameMap.containsKey(sessionId))
            return;
        String gameId = playerGameMap.get(sessionId);
        Game game = gameRepository.getGame(gameId);
        boolean isWaiting = true;
        if (game == null) {
            game = gameRepository.getGame(gameId);
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
                gameService.playerWin(gameId, gameUtils.getLastPlayerIndex(players));
                return;
            }
            gameService.playerLeaved(game, i);
        }
        send("player-list", gameId, "players", players);
    }

    public void ready(String sessionId, String gameId) {
        logger.info("ready, sessionId: ", sessionId, " , gameId: ", gameId);
        Game game = gameRepository.getGame(gameId);
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
