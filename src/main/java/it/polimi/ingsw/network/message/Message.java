package it.polimi.ingsw.network.message;

import java.io.Serializable;

public abstract class Message implements Serializable {

    public GameState getGameState(){
        return null;
    }

    public void printMessage(){}

}
