package it.polimi.ingsw.model.DivinityWorkers;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class WorkerAthena extends Worker {

    public WorkerAthena(Color c, int coordX, int coordY) {
        super(c, coordX, coordY);
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
        GameManager.setAllowHeight(true);               //può andare
        if(Map.getCellBlockType(newX, newY).getAbbreviation() > getCoordZ()){
            GameManager.setAllowHeight(false);
        }
        super.changePosition(newX, newY);
    }
}
