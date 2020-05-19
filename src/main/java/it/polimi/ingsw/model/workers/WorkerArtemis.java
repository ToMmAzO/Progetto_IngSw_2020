package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;

public class WorkerArtemis extends Worker {

    public WorkerArtemis(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public boolean canDoAgain(int x, int y) {
        for (int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {
            for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
                if (!(i == getCoordX() && j == getCoordY()) && !(i == x && j == y) && ActionManager.getInstance().validCoords(i, j) &&
                        Map.getInstance().noWorkerHere(i, j) && Map.getInstance().getCellBlockType(i, j) != BlockType.CUPOLA){
                    if(TurnManager.getInstance().cannotGoUp()){
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
