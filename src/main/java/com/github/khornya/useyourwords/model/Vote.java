package com.github.khornya.useyourwords.model;

public class Vote {
    private int answerIndex;
    private int teamIndex;

    public Vote(int answerIndex, int teamIndex) {
        this.answerIndex = answerIndex;
        this.teamIndex = teamIndex;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }

    public int getTeamIndex() {
        return teamIndex;
    }

    public void setTeamIndex(int teamIndex) {
        this.teamIndex = teamIndex;
    }
}
