package it.polimi.ingsw.model.Workers;

public class WorkerArtemis extends Worker {

    public WorkerArtemis(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public void changePosition(int newX, int newY){         //classe inutile XD
        super.changePosition(newX, newY);
    }

}
