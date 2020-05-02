package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.model.Board.Map;

public class Message_Construction extends Message{

    private final GameState gameState = GameState.CONSTRUCTION;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        Map.getInstance().print();
        System.out.println("COSTRUZIONE: \nInserisci delle cordinate (x, y):");
    }
}
