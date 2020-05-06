package it.polimi.ingsw.model.workers;

public class WorkerPan extends Worker {

    public WorkerPan(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public void changePosition(int newX, int newY){
        super.changePosition(newX, newY);
    }
}
