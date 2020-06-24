package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.TurnManager;

/**
 * An opponent cannot win by moving into a perimeter space.
 */
public class WorkerHera extends Worker {

    public WorkerHera(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
        TurnManager.getInstance().setAllowWinHera(false);
    }

}