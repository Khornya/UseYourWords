package com.github.khornya.useyourwords.model;

public class JoinedMessageContent extends MessageContent {
    private String name;
    private int index;
    private String gameId;

    public JoinedMessageContent(String name, int index, String gameId) {
        this.name = name;
        this.index = index;
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
