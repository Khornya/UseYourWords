package com.github.khornya.useyourwords.model.message.game;

import com.github.khornya.useyourwords.model.message.MessageContent;
import com.github.khornya.useyourwords.model.Team;

public class GameStartMessageContent extends MessageContent {
    private Team[] teams;

    public GameStartMessageContent(Team[] teams) {
        this.teams = teams;
    }

    public Team[] getTeams() {
        return teams;
    }

    public void setTeams(Team[] teams) {
        this.teams = teams;
    }
}
