package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_Construction extends Message{

    private final GameState gameState = GameState.CONSTRUCTION;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public String getMessage(){
        return "CONSTRUCTION. Type coordinates (x, y): ";
    }

}
