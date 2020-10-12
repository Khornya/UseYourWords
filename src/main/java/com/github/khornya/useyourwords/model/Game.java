package com.github.khornya.useyourwords.model;

import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {

    /**
     * max number of player of each room. Change by
     * websocket.gameroom.config.numof.rounds property.
     */
    @Value(value = "${websocket.gameroom.config.numof.rounds}")
    protected int defaultNumOfRounds;
    /**
     * max number of player of each room. Change by
     * websocket.gameroom.config.numof.player property.
     */
    @Value(value = "${websocket.gameroom.config.numof.player}")
    protected int defaultNumOfPlayers;

    private String id;
    private Player[] players;
    private Team[] teams;

    private Set<String> readyPlayers = new HashSet<>();
    private AtomicInteger joinCount = new AtomicInteger(0);

    public Game() {
        this.id = UUID.randomUUID().toString();
        this.players = new Player[defaultNumOfPlayers];
    }

    public Game(String id, int numOfPlayers, int numOfTeams, int numOfRounds) {
        this.id = id;
        this.players = new Player[numOfPlayers];
        this.teams = new Team[numOfTeams];
        this.defaultNumOfRounds = numOfRounds;
    }

    public int getDefaultNumOfRounds() {
        return defaultNumOfRounds;
    }

    public void setDefaultNumOfRounds(int defaultNumOfRounds) {
        this.defaultNumOfRounds = defaultNumOfRounds;
    }

    public int getDefaultNumOfPlayers() {
        return defaultNumOfPlayers;
    }

    public void setDefaultNumOfPlayers(int defaultNumOfPlayers) {
        this.defaultNumOfPlayers = defaultNumOfPlayers;
    }

    public Team[] getTeams() {
        return teams;
    }

    public void setTeams(Team[] teams) {
        this.teams = teams;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public AtomicInteger getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(AtomicInteger joinCount) {
        this.joinCount = joinCount;
    }

    public void addReadyPlayer(String sessionId) {
        readyPlayers.add(sessionId);
    }

    public void removeReadyPlayer(String sessionId) {
        readyPlayers.remove(sessionId);
    }

    public int getReadyPlayersSize() {
        return readyPlayers.size();
    }

    public Set<String> getReadyPlayers() {
        return readyPlayers;
    }

    public void setReadyPlayers(Set<String> readyPlayers) {
        this.readyPlayers = readyPlayers;
    }

    @Override
    public String toString() {
        return "Game [id=" + id + ", players=" + Arrays.toString(players) + ", readyPlayerSet=" + readyPlayers + ", joinCount=" + joinCount + "]";
    }
}
