package it.polimi.ingsw.model.workers;

public class WorkerHera extends Worker {

    public WorkerHera(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public void changePosition(int newX, int newY){
        super.changePosition(newX, newY);
    }

}