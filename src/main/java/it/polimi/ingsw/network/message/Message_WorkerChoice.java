package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_WorkerChoice extends Message{

    private final GameState gameState = GameState.WORKER_CHOICE;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public String getMessage(){
        return "Choose which of your Workers you want to use, type 1 or 2: ";
    }

}
