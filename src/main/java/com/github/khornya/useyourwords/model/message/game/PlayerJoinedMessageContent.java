package com.github.khornya.useyourwords.model.message.game;

import com.github.khornya.useyourwords.model.message.MessageContent;

public class PlayerJoinedMessageContent extends MessageContent {
    private String name;
    private int index;

    public PlayerJoinedMessageContent(String name, int index) {
        this.name = name;
        this.index = index;
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
}
