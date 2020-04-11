package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;

public class WorkerAthena extends Worker {

    public WorkerAthena(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public boolean canMove() {
        for(int i = getCoordX() - 1; i < getCoordX() + 1; i++){
            for(int j = getCoordY() - 1; j < getCoordY() + 1; j++) {
                if ((i == getCoordX() && j == getCoordY()) || !Map.noWorkerHere(i, j) || Map.getCellBlockType(i, j).getAbbreviation() >= getCoordZ() + 2 || Map.getCellBlockType(i, j) == BlockType.CUPOLA) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void changePosition(int newX, int newY){
        TurnManager.setAllowHeight(true);               //può andare
        if(Map.getCellBlockType(newX, newY).getAbbreviation() > getCoordZ()){
            TurnManager.setAllowHeight(false);
        }
        super.changePosition(newX, newY);
    }
}
