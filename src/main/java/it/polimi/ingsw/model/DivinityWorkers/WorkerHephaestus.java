package it.polimi.ingsw.model.DivinityWorkers;

import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class WorkerHephaestus extends Worker {

    public WorkerHephaestus(Player p, int coordX, int coordY) {
        super(p, coordX, coordY);
    }

    @Override
    public boolean canBuild(boolean buildAgain) {
        for(int i = getCoordX() - 1; i < getCoordX() + 1; i++){
            for(int j = getCoordY() - 1; j < getCoordY() + 1; j++) {
                if ((i == getCoordX() && j == getCoordY()) || !Map.noWorkerHere(i, j) || (buildAgain &&  Map.getCellBlockType(i, j) == BlockType.BLOCK2) || (buildAgain &&  Map.getCellBlockType(i, j) == BlockType.BLOCK3) || Map.getCellBlockType(i, j) == BlockType.CUPOLA) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void buildBlock(boolean buildAgain, int buildX, int buildY) {
        if(buildAgain){
            if(Map.getCellBlockType(buildX, buildY) == BlockType.GROUND){
                Map.setCellBlockType(buildX, buildY, BlockType.BLOCK2);
            }else if(Map.getCellBlockType(buildX, buildY) == BlockType.BLOCK1){
                Map.setCellBlockType(buildX, buildY, BlockType.BLOCK3);
            }
        } else {
            if(Map.getCellBlockType(buildX, buildY) == BlockType.BLOCK2){
                Map.setCellBlockType(buildX, buildY, BlockType.BLOCK3);
            }else if(Map.getCellBlockType(buildX, buildY) == BlockType.BLOCK3) {
                Map.setCellBlockType(buildX, buildY, BlockType.CUPOLA);
            }
        }
    }

}
