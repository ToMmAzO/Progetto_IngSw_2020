package it.polimi.ingsw.network.massage_prova;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String senderUsername;
    private final MessageType content;

    Message(String senderUsername, MessageType content) {
        this.senderUsername = senderUsername;
        this.content = content;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public MessageType getContent() {
        return content;
    }


}
