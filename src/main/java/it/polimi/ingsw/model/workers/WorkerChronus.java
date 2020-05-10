package it.polimi.ingsw.model.workers;

public class WorkerChronus extends Worker {

    public WorkerChronus(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public void changePosition(int newX, int newY){
        super.changePosition(newX, newY);
    }

}
