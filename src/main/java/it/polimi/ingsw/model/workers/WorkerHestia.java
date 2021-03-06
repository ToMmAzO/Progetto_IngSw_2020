package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;

/**
 * This Worker may build one additional time, but this cannot be on a perimeter space.
 */
public class WorkerHestia extends Worker {

    public WorkerHestia(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public boolean canBuild() {
        for (int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {
            for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
                if(!(i == 0 ||  i == 4 || j == 0 || j == 4)) {
                    if (!(i == getCoordX() && j == getCoordY()) && ActionManager.getInstance().validCoords(i, j) && Map.getInstance().noWorkerHere(i, j) && Map.getInstance().getCellBlockType(i, j) != BlockType.CUPOLA) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}