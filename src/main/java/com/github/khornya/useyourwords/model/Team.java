package com.github.khornya.useyourwords.model;

import org.springframework.beans.factory.annotation.Value;

public class Team {
    /**
     * max number of player of each team. Change by
     * websocket.gameroom.config.numof.player.per.team properties.
     */
    @Value(value = "${websocket.gameroom.config.numof.player.per.team}")
    protected int defaultNumOfPlayers;
    private int maxNumOfPlayers;
    private Player[] players;
    private int score = 0;

    public Team() {
        this.maxNumOfPlayers = defaultNumOfPlayers;
        this.players = new Player[defaultNumOfPlayers];
    }

    public Team(int numOfPlayers) {
        this.maxNumOfPlayers = numOfPlayers;
        this.players = new Player[numOfPlayers];
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMaxNumOfPlayers() {
        return maxNumOfPlayers;
    }

    public void setMaxNumOfPlayers(int maxNumOfPlayers) {
        this.maxNumOfPlayers = maxNumOfPlayers;
    }

    public int getCurrentNumOfPlayers() {
        int count = 0;
        for (Player player : players) {
            if (player != null) {
                count++;
            }
        }
        return count;
    }

    public void addPlayer(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = player;
                break;
            }
        }
    }
}
