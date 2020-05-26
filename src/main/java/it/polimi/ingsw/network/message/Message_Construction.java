package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_Construction extends Message{

    private final GameState gameState = GameState.CONSTRUCTION;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.print("COSTRUZIONE:\nInserisci delle cordinate (x, y): ");
    }

}
