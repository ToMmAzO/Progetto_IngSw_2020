package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_Error extends Message{

    private final GameState gameState = GameState.ERROR;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public String getMessage(){
        return "This server is not yet ready to accept new players, please try later.\n";
    }

}
