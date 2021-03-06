package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;

/**
 * If this Worker does not move up, it may build both before and after moving.
 */
public class WorkerPrometheus extends Worker {

    public WorkerPrometheus(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public boolean canMove() {
        for (int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {
            for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
                if (!(i == getCoordX() && j == getCoordY()) && ActionManager.getInstance().validCoords(i, j) && Map.getInstance().noWorkerHere(i, j) && Map.getInstance().getCellBlockType(i, j) != BlockType.CUPOLA){
                    if(TurnManager.getInstance().cannotGoUp() || TurnManager.getInstance().cannotGoUpPrometheus()){
                        if(Map.getInstance().getCellBlockType(i, j).getAbbreviation() <= getCoordZ()) {
                            return true;
                        }
                    }else{
                        if(Map.getInstance().getCellBlockType(i, j).getAbbreviation() <= getCoordZ() + 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
