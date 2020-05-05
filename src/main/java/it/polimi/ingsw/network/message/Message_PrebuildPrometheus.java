package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.model.Board.Map;

public class Message_PrebuildPrometheus extends Message{

    private final GameState gameState = GameState.PREBUILD_PROMETHEUS;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("Inserisci delle cordinate (x, y).");
    }

}
