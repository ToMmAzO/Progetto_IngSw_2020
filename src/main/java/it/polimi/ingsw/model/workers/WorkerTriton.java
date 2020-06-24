package it.polimi.ingsw.model.workers;

/**
 * Each time this Worker moves into a perimeter space, it may immediately move again.
 */
public class WorkerTriton extends Worker {

    public WorkerTriton(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

}