package com.github.khornya.useyourwords.model;

public class Vote {
    private int answerIndex;
    private int playerIndex;

    public Vote(int answerIndex, int playerIndex) {
        this.answerIndex = answerIndex;
        this.playerIndex = playerIndex;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
