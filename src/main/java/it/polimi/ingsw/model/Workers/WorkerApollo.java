package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;

public class WorkerApollo extends Worker {

    public WorkerApollo(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public boolean canMove() {
        for (int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {           //se no funziona --> while
            for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
                if (!(i == getCoordX() && j == getCoordY()) && ActionManager.validCoords(i, j) && Map.getCellBlockType(i, j) != BlockType.CUPOLA){
                    if(TurnManager.cannotGoUp()){
                        if(Map.getCellBlockType(i, j).getAbbreviation() <= getCoordZ()) {
                            return true;
                        }
                    }else{
                        if(Map.getCellBlockType(i, j).getAbbreviation() <= getCoordZ() + 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void changePosition(int newX, int newY){
        if(!Map.noWorkerHere(newX, newY)){
            Map.deleteWorkerInCell(this);

            Map.getWorkerInCell(newX, newY).setCoordX(getCoordX());
            Map.getWorkerInCell(newX, newY).setCoordY(getCoordY());
            Map.getWorkerInCell(newX, newY).setCoordZ(getCoordZ());

            Map.setWorkerInCell(getCoordX(), getCoordY(), Map.getWorkerInCell(newX, newY));
            Map.deleteWorkerInCell(Map.getWorkerInCell(newX, newY));

            setCoordX(newX);
            setCoordY(newY);
            setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());

            Map.setWorkerInCell(newX, newY, this);
        }else {
            super.changePosition(newX, newY);
        }
    }

}
