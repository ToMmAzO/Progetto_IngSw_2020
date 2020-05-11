package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.TurnManager;

public class WorkerHera extends Worker {

    public WorkerHera(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
        TurnManager.getInstance().setAllowWin(false);
    }

}