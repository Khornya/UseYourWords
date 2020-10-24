package com.github.khornya.useyourwords.model.message.game;

import com.github.khornya.useyourwords.model.message.MessageContent;

public class TimerMessageContent extends MessageContent {
    private int secondsLeft;

    public TimerMessageContent(int secondsLeft) {
        this.secondsLeft = secondsLeft;
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }

    public void setSecondsLeft(int secondsLeft) {
        this.secondsLeft = secondsLeft;
    }
}
