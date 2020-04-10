package it.polimi.ingsw.model.DivinityWorkers;

import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Worker;

public class WorkerHephaestus extends Worker {

    public WorkerHephaestus(Color c, int coordX, int coordY) {
        super(c, coordX, coordY);
    }

    @Override
    public boolean canBuild(boolean buildTwoTimes) {
        for(int i = getCoordX() - 1; i < getCoordX() + 1; i++){
            for(int j = getCoordY() - 1; j < getCoordY() + 1; j++) {
                if ((i == getCoordX() && j == getCoordY()) || !Map.noWorkerHere(i, j) || (buildTwoTimes &&  Map.getCellBlockType(i, j) == BlockType.BLOCK2) || (buildTwoTimes &&  Map.getCellBlockType(i, j) == BlockType.BLOCK3) || Map.getCellBlockType(i, j) == BlockType.CUPOLA) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean canBuild() {
        return super.canBuild();
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
