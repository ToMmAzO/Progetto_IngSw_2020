package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_SecondConstructionHestia extends Message{

    private final GameState gameState = GameState.SECOND_CONSTRUCTION_HESTIA;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public String getMessage(){
        return "Type coordinates (x, y): ";
    }

}
