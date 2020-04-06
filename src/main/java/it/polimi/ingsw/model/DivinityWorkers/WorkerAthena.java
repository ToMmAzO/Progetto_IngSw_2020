package it.polimi.ingsw.model.DivinityWorkers;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class WorkerAthena extends Worker {

    public WorkerAthena(Player p, int coordX, int coordY) {
        super(p, coordX, coordY);
    }

    @Override
    public void changePosition(int newX, int newY){
        super.changePosition(newX, newY);  //ridefinisco metodo
    }
}
