package it.polimi.ingsw.model.DivinityWorkers;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class WorkerDemeter extends Worker {

    public WorkerDemeter(Player p, int coordX, int coordY) {
        super(p, coordX, coordY);
    }

    @Override
    public void buildBlock(int buildX, int buildY) {         //classe inutile XD
        super.buildBlock(buildX, buildY);
    }

}
