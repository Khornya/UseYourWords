package com.github.khornya.useyourwords.service;

import com.github.khornya.useyourwords.model.*;
import com.github.khornya.useyourwords.model.message.game.GameRoundMessageContent;
import com.github.khornya.useyourwords.model.message.game.GameStartMessageContent;
import com.github.khornya.useyourwords.model.message.Message;
import com.github.khornya.useyourwords.model.message.game.TimerMessageContent;
import com.github.khornya.useyourwords.model.message.game.VoteMessageContent;
import com.github.khornya.useyourwords.model.message.player.HideAnswerMessageContent;
import com.github.khornya.useyourwords.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GameService {

	private Logger logger = LoggerFactory.getLogger(GameService.class);

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private WebSocketService webSocketService;

	@Autowired
	private ScoreService scoreService;

	@Autowired ElementService elementService;

	public void addGame(String gameId, Game game) {
		gameRepository.addGame(gameId, game);
	}

	public Game getGameById(String gameId) {
		return gameRepository.getGame(gameId);
	}

	public void start(String gameId) {
		logger.info("GAME START - gameId: {}", gameId);
		Game game = getGameById(gameId);
		GameStartMessageContent gameStartMessageContent = new GameStartMessageContent(game.getTeams());
		Message gameRoomMessage = new Message(Message.MessageType.START, gameStartMessageContent);
		webSocketService.sendToRoom(gameId, gameRoomMessage);
		nextRound(game);
	}

	private void nextRound(Game game) {
		logger.info("NEXT ROUND - game: {}", game);
		String gameId = game.getId();
		if (game.getCurrentRoundNumber() == game.getNumOfRounds()) {
			endGame(game);
		} else {
			game.nextRound();
			GameRoundMessageContent gameRoundMessageContent = new GameRoundMessageContent(game.getCurrentRoundNumber(), game.getCurrentElement());
			Message gameRoomMessage = new Message(Message.MessageType.NEXT_ROUND, gameRoundMessageContent);
			webSocketService.sendToRoom(gameId, gameRoomMessage);
			game.setAcceptAnswers(true);
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					TimerMessageContent timerMessageContent = new TimerMessageContent(10);
					Message gameRoomMessage = new Message(Message.MessageType.TIMER, timerMessageContent);
					webSocketService.sendToRoom(gameId, gameRoomMessage);
				}
			}, 50 * 1000);
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					endRound(game);
				}
			}, 60 * 1000);
			game.setTimer(timer);
		}
	}

	public void addAnswer(String gameId, Answer answer) {
		logger.info("ADD ANSWER - gameId: {}, answer: {}", gameId, answer);
		Game game = getGameById(gameId);
		if (game.isAcceptAnswers()) {
			game.addAnswer(answer);
			if (game.getAnswers().size() == game.getPlayers().length + 1) {
				game.cancelTimer();
				endRound(game);
			}
		}
	}

	private void endRound(Game game) {
		logger.info("END ROUND - game: {}", game);
		game.shuffleAnswers();
		game.setAcceptAnswers(false);
		VoteMessageContent voteMessageContent = new VoteMessageContent(game.getTransformedAnswers());
		Message gameRoomMessage = new Message(Message.MessageType.END_ROUND, voteMessageContent);
		webSocketService.sendToRoom(game.getId(), gameRoomMessage);
		ArrayList<Answer> answers = game.getAnswers();
		for (int i = 0; i < answers.size(); i++) {
			int playerIndex = answers.get(i).getPlayerIndex();
			if (playerIndex != -1) {
				String sessionId = game.getPlayers()[playerIndex].getSessionId();
				HideAnswerMessageContent hideAnswerMessageContent = new HideAnswerMessageContent(i);
				Message playerMessage = new Message(Message.MessageType.HIDE_ANSWER, hideAnswerMessageContent);
				webSocketService.replyToUser(sessionId, playerMessage);
			}
		}
	}

	public void endGame(Game game) {
		logger.info("END GAME - game: {}", game);
		for (Team team : game.getTeams()) {
			for (Player player : team.getPlayers()) {
				scoreService.add(new Score(player.getName(), team.getScore(), LocalDateTime.now()));
			}
		}
		GameStartMessageContent gameStartMessageContent = new GameStartMessageContent(game.getTeams());
		Message gameRoomMessage = new Message(Message.MessageType.GAME_OVER, gameStartMessageContent);
		webSocketService.sendToRoom(game.getId(), gameRoomMessage);
		gameRepository.removeGame(game.getId());
	}

	public void addVote(String gameId, int answerIndex, String sessionId) {
		logger.info("ADD ANSWER - gameId: {}, answerIndex: {}, sessionId: {}", gameId, answerIndex, sessionId);
		Game game = getGameById(gameId);
		game.addVote(answerIndex, sessionId);
		if (game.getVotes().size() == game.getPlayers().length) {
			Team[] result = game.processVotes();
			GameStartMessageContent gameStartMessageContent = new GameStartMessageContent(result);
			Message gameRoomMessage = new Message(Message.MessageType.VOTES, gameStartMessageContent);
			webSocketService.sendToRoom(game.getId(), gameRoomMessage);
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					nextRound(game);
				}
			}, 10 * 1000);
		}
	}
}
