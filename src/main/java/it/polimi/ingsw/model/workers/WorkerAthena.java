package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;

/**
 * If one of this Workers moved up on their last turn, opponent Workers cannot move up this turn.
 */
public class WorkerAthena extends Worker {

    public WorkerAthena(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public boolean canMove() {
        for (int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {
            for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
                if (!(i == getCoordX() && j == getCoordY()) && ActionManager.getInstance().validCoords(i, j) && Map.getInstance().noWorkerHere(i, j) && Map.getInstance().getCellBlockType(i, j) != BlockType.CUPOLA){
                    if(Map.getInstance().getCellBlockType(i, j).getAbbreviation() <= getCoordZ() + 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void changePosition(int newX, int newY){
        TurnManager.getInstance().setAllowHeightAthena(true);
        if(Map.getInstance().getCellBlockType(newX, newY).getAbbreviation() > getCoordZ()){
            TurnManager.getInstance().setAllowHeightAthena(false);
        }
        super.changePosition(newX, newY);
    }
}
