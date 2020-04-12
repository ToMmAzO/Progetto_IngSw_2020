package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;

public class WorkerApollo extends Worker {

    public WorkerApollo(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public boolean canMove() {
        boolean canMove = false;

        for(int i = getCoordX() - 1; i <= getCoordX() + 1 || canMove; i++){           //se no funziona --> while
            for(int j = getCoordY() - 1; j <= getCoordY() + 1 || canMove; j++) {
                if ((i != getCoordX() && j != getCoordY()) && (TurnManager.cannotGoUp() && Map.getCellBlockType(i, j).getAbbreviation() <= getCoordZ()) && Map.getCellBlockType(i, j).getAbbreviation() < getCoordZ() + 2 && Map.getCellBlockType(i, j) != BlockType.CUPOLA) {
                    canMove = true;
                }
            }
        }
        return canMove;
    }

    @Override
    public void changePosition(int newX, int newY){
        if(!Map.noWorkerHere(newX, newY)){
            Map.getWorkerInCell(newX, newY).setCoordX(getCoordX());
            Map.getWorkerInCell(newX, newY).setCoordY(getCoordY());
            Map.getWorkerInCell(newX, newY).setCoordZ(getCoordZ());
            setCoordX(newX);
            setCoordY(newY);
            setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
        }else {
            super.changePosition(newX, newY);
        }
    }

}
