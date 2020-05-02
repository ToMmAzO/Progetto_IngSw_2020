package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.model.Board.Map;

public class Message_WokerChoice extends Message{

    private final GameState gameState = GameState.WORKER_CHOICE;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        Map.getInstance().print();
        System.out.print("Scegli che worker usare: " + player.getWorkerSelected(1).getIdWorker() + " oppure " + player.getWorkerSelected(2).getIdWorker() + ". ");
    }
}
