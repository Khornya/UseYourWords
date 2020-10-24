package com.github.khornya.useyourwords.model.message.game;

import com.github.khornya.useyourwords.model.message.MessageContent;

import java.util.ArrayList;

public class VoteMessageContent extends MessageContent {
    ArrayList<String> answers;

    public VoteMessageContent(ArrayList<String> answers) {
        this.answers = answers;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }
}
