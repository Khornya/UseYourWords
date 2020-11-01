package com.github.khornya.useyourwords.model;

import java.util.ArrayList;

public class Answer {
    private ArrayList<String> answers;

    private int playerIndex;
    private ElementType type;

    public Answer() {
    }

    public Answer(ArrayList<String> answers, int playerIndex, ElementType type) {
        this.answers = answers;
        this.playerIndex = playerIndex;
        this.type = type;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }
}
