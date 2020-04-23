package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;

public class WorkerDemeter extends Worker {

    public WorkerDemeter(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public boolean canBuild(int x, int y){
        for (int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {
            for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
                if (!(i == getCoordX() && j == getCoordY()) && !(i == x && j == y) && ActionManager.validCoords(i, j) && Map.noWorkerHere(i, j) && Map.getCellBlockType(i, j) != BlockType.CUPOLA) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void buildBlock(int buildX, int buildY) {         //classe inutile XD
        super.buildBlock(buildX, buildY);
    }

}
