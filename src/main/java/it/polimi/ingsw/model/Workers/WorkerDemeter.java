package it.polimi.ingsw.model.Workers;

public class WorkerDemeter extends Worker {

    public WorkerDemeter(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public void buildBlock(int buildX, int buildY) {         //classe inutile XD
        super.buildBlock(buildX, buildY);
    }

}
