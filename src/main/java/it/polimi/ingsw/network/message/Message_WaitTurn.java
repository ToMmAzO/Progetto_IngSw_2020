package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_WaitTurn extends Message{

    private final GameState gameState = GameState.WAIT_TURN;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("Attendi il tuo turno.");
    }

}
