package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.model.Board.Map;

public class Message_SecondConstruction extends Message{

    private final GameState gameState = GameState.SECOND_CONSTRUCTION;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        Map.getInstance().print();
        System.out.println("Inserisci delle cordinate (x, y).");
    }

}
