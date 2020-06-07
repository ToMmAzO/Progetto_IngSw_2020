package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

import java.io.Serializable;

public abstract class Message implements Serializable {

    public GameState getGameState(){
        return null;
    }

    public String getMessage(){
        return null;
    }

}
