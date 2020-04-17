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
                    if (!(i == getCoordX() && j == getCoordY()) && ActionManager.validCoords(i, j) && Map.noWorkerHere(i, j) && (buildTwoTimes && Map.getCellBlockType(i, j) != BlockType.BLOCK2) && (buildTwoTimes && Map.getCellBlockType(i, j) != BlockType.BLOCK3) && Map.getCellBlockType(i, j) != BlockType.CUPOLA) {
                        return true;
                    }
                }
            }
            return true;
        }else{
            return super.canBuild();
        }
    }

    @Override
    public void buildBlock(boolean buildTwoTimes, int buildX, int buildY) {
        if(buildTwoTimes){
            if(Map.getCellBlockType(buildX, buildY) == BlockType.GROUND){
                Map.setCellBlockType(buildX, buildY, BlockType.BLOCK2);
            }else if(Map.getCellBlockType(buildX, buildY) == BlockType.BLOCK1){
                Map.setCellBlockType(buildX, buildY, BlockType.BLOCK3);
            }
        } else {
            super.buildBlock(buildX, buildY);
        }
    }

}
