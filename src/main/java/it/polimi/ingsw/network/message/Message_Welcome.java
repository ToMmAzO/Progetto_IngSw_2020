package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_Welcome extends Message{

    private final GameState gameState = GameState.WELCOME;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public String getMessage(){
        return "This nickname has already been chosen. Insert another one: ";
    }

}
