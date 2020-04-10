package it.polimi.ingsw.model.DivinityWorkers;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Worker;

public class WorkerArtemis extends Worker {

    public WorkerArtemis(Color c, int coordX, int coordY) {
        super(c, coordX, coordY);
    }

    @Override
    public void changePosition(int newX, int newY){         //classe inutile XD
        super.changePosition(newX, newY);
    }

}
