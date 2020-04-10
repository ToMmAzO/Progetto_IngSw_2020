package it.polimi.ingsw.model.DivinityWorkers;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Worker;

public class WorkerDemeter extends Worker {

    public WorkerDemeter(Color c, int coordX, int coordY) {
        super(c, coordX, coordY);
    }

    @Override
    public void buildBlock(int buildX, int buildY) {         //classe inutile XD
        super.buildBlock(buildX, buildY);
    }

}
