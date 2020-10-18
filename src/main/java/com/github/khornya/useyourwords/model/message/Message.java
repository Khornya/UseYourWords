package com.github.khornya.useyourwords.model.message;

public class Message {

    public enum MessageType {
        PLAYER_JOINED, PLAYER_LEFT, START, GAME_OVER, NEXT_ROUND, JOINED, ERROR
    }

    private MessageType type;
    private MessageContent content;

    public Message(MessageType type, MessageContent content) {
        this.type = type;
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public MessageContent getContent() {
        return content;
    }

    public void setContent(MessageContent content) {
        this.content = content;
    }
}
