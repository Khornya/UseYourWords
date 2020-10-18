package com.github.khornya.useyourwords.model.message.player;

public class PlayerReadyMessage {
    private String gameId;
    private int playerIndex;

    public PlayerReadyMessage() {
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
