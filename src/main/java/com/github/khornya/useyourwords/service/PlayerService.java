package com.github.khornya.useyourwords.service;

import com.github.khornya.useyourwords.model.*;
import com.github.khornya.useyourwords.model.message.Message;
import com.github.khornya.useyourwords.model.message.game.PlayerJoinedMessageContent;
import com.github.khornya.useyourwords.model.message.player.ErrorCode;
import com.github.khornya.useyourwords.model.message.player.ErrorMessageContent;
import com.github.khornya.useyourwords.model.message.player.JoinedMessageContent;
import com.github.khornya.useyourwords.repository.GameRepository;
import com.github.khornya.useyourwords.repository.PlayerRepository;
import com.github.khornya.useyourwords.utils.GameUtils;
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
    private WebSocketService webSocketService;

    @Autowired
    private GameUtils gameUtils;

    @Value(value = "${websocket.gameroom.config.numof.player}")
    private int maxNumOfPlayers;

    public void addPlayer(String sessionId, String gameId, String name) {
        logger.info("addPlayer, sessionId: ", sessionId, ", gameId: ", gameId, ", name: ", name);
        Game game = gameRepository.getGame(gameId);
        if (game != null && game.getJoinCount().incrementAndGet() <= game.getPlayers().length)
            addPlayer(new Player(name, sessionId), game);
        else {
            ErrorMessageContent errorMessageContent = new ErrorMessageContent(ErrorCode.JOIN_INVALID_GAMEID.toString(), "Unable to join this game");
            Message message = new Message(Message.MessageType.ERROR, errorMessageContent);
            webSocketService.replyToUser(sessionId, message);
        }
    }

    private void addPlayer(Player player, Game game) {

        logger.info("addPlayer, player: ", player, ", game: ", game);
        int i = game.addPlayer(player);
        Team[] teams = game.getTeams();
        for (Team team : teams) {
                if (team == null) {
                    team = new Team();
                }
                if (team.getCurrentNumOfPlayers() < team.getMaxNumOfPlayers()) {
                    team.addPlayer(player);
                    break;
                }
        }
        String gameId = game.getId();
        String name = player.getName();
        playerRepository.addPlayer(player.getSessionId(), gameId);

        PlayerJoinedMessageContent playerJoinedMessageContent = new PlayerJoinedMessageContent(name, i);
        Message gameRoomMessage = new Message(Message.MessageType.PLAYER_JOINED, playerJoinedMessageContent);
        webSocketService.sendToRoom(gameId, gameRoomMessage);
        JoinedMessageContent joinedMessageContent = new JoinedMessageContent(name, i, gameId);
        Message playerMessage = new Message(Message.MessageType.JOINED, joinedMessageContent);
        webSocketService.replyToUser(player.getSessionId(), playerMessage);
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
    }

    public void ready(String sessionId, String gameId) {
        logger.info("ready, sessionId: ", sessionId, " , gameId: ", gameId);
        Game game = gameRepository.getGame(gameId);
        if (game == null)
            return;
        synchronized (game) {
            game.addReadyPlayer(sessionId);
            if (game.getReadyPlayersSize() == game.getPlayers().length)
                gameService.start(gameId);
        }
    }

}
