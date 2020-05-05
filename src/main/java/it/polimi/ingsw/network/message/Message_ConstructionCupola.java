package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.model.Board.Map;

public class Message_ConstructionCupola extends Message{

    private final GameState gameState = GameState.CONSTRUCTION_CUPOLA;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("Inserisci delle cordinate (x, y).");
    }

}
