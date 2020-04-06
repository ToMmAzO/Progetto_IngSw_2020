package it.polimi.ingsw.model.DivinityWorkers;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class WorkerDemeter extends Worker {

    public WorkerDemeter(Player p, int coordX, int coordY) {
        super(p, coordX, coordY);
    }

    @Override
    public void buildBlock(Map field, int buildX, int buildY) {
        super.buildBlock(field, buildX, buildY);  //ridefinisco metodo
    }

}
