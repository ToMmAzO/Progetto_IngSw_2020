package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumerations.GameState;

public class Message_Wait extends Message{

    private final GameState gameState = GameState.WAIT;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("Attendi.");
    }

}
