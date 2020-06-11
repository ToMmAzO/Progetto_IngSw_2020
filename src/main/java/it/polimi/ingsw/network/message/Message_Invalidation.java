package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_Invalidation extends Message{

    private final GameState gameState = GameState.INVALIDATION;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public String getMessage(){
        return "\nMATCH CANCELED! A player has left the game.You lose!\n";
    }

}
