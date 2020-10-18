package com.github.khornya.useyourwords.model.message.player;

import com.github.khornya.useyourwords.model.message.MessageContent;

public class ErrorMessageContent extends MessageContent {
    private String code;
    private String message;

    public ErrorMessageContent(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
