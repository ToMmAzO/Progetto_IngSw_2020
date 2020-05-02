package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.model.Board.Map;

public class Message_SetWorker extends Message{

    private final GameState gameState = GameState.SET_WORKER;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        Map.getInstance().print();
        System.out.println("Posizionamento lavoratori. Inserisci delle cordinate (x, y):");
    }

}
