package com.github.khornya.useyourwords.model.message.game;

import com.github.khornya.useyourwords.model.Element;
import com.github.khornya.useyourwords.model.message.MessageContent;

public class GameRoundMessageContent extends MessageContent {
    private int roundNumber;
    private Element element;

    public GameRoundMessageContent(int roundNumber, Element element) {
        this.roundNumber = roundNumber;
        this.element = element;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }
}
