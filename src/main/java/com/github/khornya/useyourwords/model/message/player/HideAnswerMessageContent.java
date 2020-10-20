package com.github.khornya.useyourwords.model.message.player;

import com.github.khornya.useyourwords.model.message.MessageContent;

public class HideAnswerMessageContent extends MessageContent {
    private int answerIndex;

    public HideAnswerMessageContent(int answerIndex) {
        this.answerIndex = answerIndex;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }
}
