package com.github.khornya.useyourwords.model;

import org.springframework.beans.factory.annotation.Value;

public class Team {
    /**
     * max number of player of each room. Change by
     * websocket.gameroom.config.numof.player.per.team properties.
     */
    @Value(value = "${websocket.gameroom.config.numof.player.per.team}")
    protected int defaultNumOfPlayers;
    private Player[] players;
    private int score = 0;

    public Team() {
        this.players = new Player[defaultNumOfPlayers];
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
}
