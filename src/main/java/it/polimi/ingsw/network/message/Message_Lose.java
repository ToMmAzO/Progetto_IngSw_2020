package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_Lose extends Message{

    private final GameState gameState = GameState.LOSE;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public String getMessage(){
        return "You lose!\n";
    }

}
