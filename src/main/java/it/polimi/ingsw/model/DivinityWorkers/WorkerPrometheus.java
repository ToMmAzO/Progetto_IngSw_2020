package it.polimi.ingsw.model.DivinityWorkers;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class WorkerPrometheus extends Worker {

    public WorkerPrometheus(Player p, int coordX, int coordY) {
        super(p, coordX, coordY);
    }

    @Override
    public void buildBlock(boolean buildFirst, int buildX, int buildY) {
        if(buildFirst){
            super.buildBlock(buildX, buildY);
            GameManager.setAllowHeight(false);       //true quando?
        }else{
            super.buildBlock(buildX, buildY);
        }

    }

}
