package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class TurnManager {

    private static Worker workerSelected;

    public static void startTurn(Player player){
        workerSelected = player.getWorkerSelected();
        selectAction();
        workerSelected = null;
    }

    public static void selectAction(){
        workerSelected.changePosition(100, 100);
        workerSelected.buildBlock(100, 100);
    }

}
