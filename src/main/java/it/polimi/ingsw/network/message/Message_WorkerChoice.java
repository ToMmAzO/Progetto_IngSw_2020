package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumerations.GameState;

public class Message_WorkerChoice extends Message{

    private final GameState gameState = GameState.WORKER_CHOICE;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.print("Scegli quale dei tuoi worker usare, scrivi 1 oppure 2.");
    }

}
