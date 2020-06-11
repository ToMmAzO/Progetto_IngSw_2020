package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_Win extends Message{

    private final GameState gameState = GameState.WIN;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public String getMessage(){
        return "Congratulations, you win the game!\n";
    }

}
