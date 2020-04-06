package it.polimi.ingsw.model.DivinityWorkers;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class WorkerHephaestus extends Worker {

    public WorkerHephaestus(Player p, int coordX, int coordY) {
        super(p, coordX, coordY);
    }

    @Override
    public void buildBlock(Map field, int buildX, int buildY) {
        super.buildBlock(field, buildX, buildY);  //ridefinisco metodo
    }

}
