package it.polimi.ingsw.model.DivinityWorkers;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class WorkerPrometheus extends Worker {

    public WorkerPrometheus(Color c, int coordX, int coordY) {
        super(c, coordX, coordY);
    }

    @Override
    public boolean canMove() {
        for(int i = getCoordX() - 1; i < getCoordX() + 1; i++){
            for(int j = getCoordY() - 1; j < getCoordY() + 1; j++) {
                if ((i == getCoordX() && j == getCoordY()) || !Map.noWorkerHere(i, j) || ((GameManager.cannotGoUp() || !GameManager.getAllowHeightPrometheus()) && Map.getCellBlockType(i, j).getAbbreviation() > getCoordZ()) || Map.getCellBlockType(i, j).getAbbreviation() >= getCoordY() + 2 || Map.getCellBlockType(i, j) == BlockType.CUPOLA) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void changePosition(int newX, int newY){         //classe inutile XD
        super.changePosition(newX, newY);
    }
}
