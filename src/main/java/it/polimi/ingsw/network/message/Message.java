package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

import java.io.Serializable;

/**
 * This is the generic class from which all messages (sent by server to client) derive.
 * Each of them has an attribute that identifies the type of message by GameSate.
 */
public abstract class Message implements Serializable {

    public GameState getGameState(){
        return null;
    }

    /**
     * @return the content of message as String.
     */
    public String getMessage(){
        return null;
    }

}
