package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumerations.GameState;

public abstract class Message {

    public GameState getGameState(){
        return null;
    }

    public void printMessage(){}

}
