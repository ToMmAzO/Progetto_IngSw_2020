package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;

public class WorkerHephaestus extends Worker {

    public WorkerHephaestus(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public boolean canBuild(boolean buildTwoTimes) {
        if(buildTwoTimes) {
            for (int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {
                for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
                    if (!(i == getCoordX() && j == getCoordY()) && ActionManager.validCoords(i, j) && Map.noWorkerHere(i, j) && Map.getCellBlockType(i, j).getAbbreviation() < 2) {     //!= da BLOCK2, BLOCK3 e CUPOLA
                        return true;
                    }
                }
            }
            return false;
        }else{
            return super.canBuild();
        }
    }

    @Override
    public void buildBlock(boolean buildTwoTimes, int buildX, int buildY) {
        if(buildTwoTimes){
            if(Map.getCellBlockType(buildX, buildY) == BlockType.GROUND){
                Map.setCellBlockType(buildX, buildY, BlockType.BLOCK2);
            }else{      // per forza = a BLOCK1 per il controllo della canBuild
                Map.setCellBlockType(buildX, buildY, BlockType.BLOCK3);
            }
        } else{
            super.buildBlock(buildX, buildY);
        }
    }

}
