package it.polimi.ingsw.model.DivinityWorkers;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class WorkerPrometheus extends Worker {

    public WorkerPrometheus(Player p, int coordX, int coordY) {
        super(p, coordX, coordY);
    }

    @Override
    public void changePosition(Map field, int newX, int newY){
        super.changePosition(field, newX, newY);  //ridefinisco metodo
    }

    @Override
    public void buildBlock(Map field, int buildX, int buildY) {
        super.buildBlock(field, buildX, buildY);  //ridefinisco metodo
    }

}
