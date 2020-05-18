package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;

public class WorkerZeus extends Worker {

    public WorkerZeus(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

   /*
    @Override
    public boolean canBuild(){
        for (int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {
            for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
                if (ActionManager.getInstance().validCoords(i, j) && Map.getInstance().getCellBlockType(i, j) != BlockType.CUPOLA) {
                    if(Map.getInstance().noWorkerHere(i, j)) {
                        return true;
                    }else{
                        if(i == getCoordX() && j == getCoordY()){
                            if(Map.getInstance().getCellBlockType(i, j).getAbbreviation() < 3) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    */

    @Override
    public void buildBlock(int buildX, int buildY){
        if(buildX == getCoordX() && buildY == getCoordY()){
            setCoordZ(getCoordZ() + 1);
        }
        super.buildBlock(buildX, buildY);
    }

}