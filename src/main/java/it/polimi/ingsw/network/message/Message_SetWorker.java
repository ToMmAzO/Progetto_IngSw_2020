package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_SetWorker extends Message{

    private final GameState gameState = GameState.SET_WORKER;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.print("Posizionamento lavoratore. Inserisci delle cordinate (x, y): ");
    }

}
