package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;

public class WorkerMinotaur extends Worker {

    public WorkerMinotaur(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public boolean canMove() {
        boolean canMove = false;
        int m = getCoordX() - 2;
        int n = getCoordY() - 2;

        for(int i = getCoordX() - 1; i <= getCoordX() + 1 || canMove; i++){
            for(int j = getCoordY() - 1; j <= getCoordY() + 1 || canMove; j++) {
                if ((i != getCoordX() && j != getCoordY()) && (TurnManager.cannotGoUp() && Map.getCellBlockType(i, j).getAbbreviation() <= getCoordZ()) && Map.getCellBlockType(i, j).getAbbreviation() < getCoordZ() + 2 && Map.getCellBlockType(i, j) != BlockType.CUPOLA) {
                    if (m >= 0 && m <= 4 && n >= 0 && n <= 4 && Map.getCellBlockType(m, n).getAbbreviation() < Map.getCellBlockType(i, j).getAbbreviation() + 2 && Map.getCellBlockType(m, n) != BlockType.CUPOLA) {
                        canMove = true;
                    }
                }
                n = n + 2;
            }
            m = m + 2;
        }
        return canMove;
    }

    @Override
    public void changePosition(int newX, int newY){
        int m = getCoordX() - 2;
        int n = getCoordY() - 2;

        if(Map.noWorkerHere(newX, newY)) {
            super.changePosition(newX, newY);
        }else{
            for(int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {
                for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
                    if(newX == i && newY == j) {
                        setCoordX(newX);
                        setCoordY(newY);
                        setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
                        Map.getWorkerInCell(newX, newY).setCoordX(m);
                        Map.getWorkerInCell(newX, newY).setCoordY(n);
                        Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(m, n).getAbbreviation());
                    }
                    n = n + 2;
                }
                m = m + 2;
            }
        }
    }

}
