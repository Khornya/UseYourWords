package com.github.khornya.useyourwords.service;

import com.github.khornya.useyourwords.model.*;
import com.github.khornya.useyourwords.model.message.Message;
import com.github.khornya.useyourwords.model.message.game.PlayerJoinedMessageContent;
import com.github.khornya.useyourwords.model.message.player.ErrorCode;
import com.github.khornya.useyourwords.model.message.player.ErrorMessageContent;
import com.github.khornya.useyourwords.model.message.player.JoinedMessageContent;
import com.github.khornya.useyourwords.repository.GameRepository;
import com.github.khornya.useyourwords.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void addPlayer(String sessionId, String gameId, String name) {
        logger.info("ADD PLAYER , sessionId: {}, gameId: {}, name: {}", sessionId, gameId, name);
        Game game = gameRepository.getGame(gameId);
        if (game != null && game.getJoinCount().incrementAndGet() <= game.getPlayers().length)
            addPlayer(new Player(name, sessionId), game);
        else {
            ErrorMessageContent errorMessageContent = new ErrorMessageContent(ErrorCode.JOIN_INVALID_GAMEID.toString(), "Impossible de rejoindre cette partie");
            Message message = new Message(Message.MessageType.ERROR, errorMessageContent);
            webSocketService.replyToUser(sessionId, message);
        }
    }

    private void addPlayer(Player player, Game game) {
        logger.info("ADD PLAYER - player: {}, game: {}", player, game);
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
        logger.info("REMOVE PLAYER - sessionId: {}", sessionId);
        Map<String, String> playerGameMap = playerRepository.getPlayerGameMap();
        if (!playerGameMap.containsKey(sessionId))
            return;
        String gameId = playerGameMap.get(sessionId);
        Game game = gameRepository.getGame(gameId);
        removePlayer(sessionId, game);
    }

    private void removePlayer(String sessionId, Game game) {
        logger.info("REMOVE PLAYER - sessionId: {}, game: {}", sessionId, game);
        Player[] players;
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
        if (game.getReadyPlayersSize() == 1) {
            gameService.endGame(game);
        }
    }

    public void ready(String sessionId, String gameId) {
        logger.info("PLAYER READY - sessionId: {}, gameId: {}", sessionId, gameId);
        Game game = gameRepository.getGame(gameId);
        game.addReadyPlayer(sessionId);
        if (game.getReadyPlayersSize() == game.getPlayers().length)
            gameService.start(gameId);
    }

}
