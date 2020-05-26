package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_Movement extends Message{

    private final GameState gameState = GameState.MOVEMENT;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.print("MOVIMENTO:\nInserisci delle cordinate (x, y): ");
    }

}
