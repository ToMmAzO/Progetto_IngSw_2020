package it.polimi.ingsw.model.workers;

/**
 * This Worker may build a block under itself.
 */
public class WorkerZeus extends Worker {

    public WorkerZeus(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public void buildBlock(int buildX, int buildY){
        if(buildX == getCoordX() && buildY == getCoordY()){
            setCoordZ(getCoordZ() + 1);
        }
        super.buildBlock(buildX, buildY);
    }

}