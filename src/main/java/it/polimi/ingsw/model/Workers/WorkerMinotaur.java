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
        int m = getCoordX() - 2;
        int n = getCoordY() - 2;

        for (int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {           //se no funziona --> while
            for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
                if (!(i == getCoordX() && j == getCoordY()) && Map.isAcceptable(i, j) && Map.getCellBlockType(i, j) != BlockType.CUPOLA){
                    if(TurnManager.cannotGoUp()){
                        if(Map.getCellBlockType(i, j).getAbbreviation() <= getCoordZ()) {
                            if (Map.isAcceptable(m, n) && Map.getCellBlockType(m, n).getAbbreviation() <= Map.getCellBlockType(i, j).getAbbreviation() && Map.getCellBlockType(m, n) != BlockType.CUPOLA) {
                                return true;
                            }
                        }
                    }else{
                        if(Map.getCellBlockType(i, j).getAbbreviation() <= getCoordZ() + 1) {
                            if (Map.isAcceptable(m, n) && Map.getCellBlockType(m, n).getAbbreviation() <= Map.getCellBlockType(i, j).getAbbreviation() + 1 && Map.getCellBlockType(m, n) != BlockType.CUPOLA) {
                                return true;
                            }
                        }
                    }
                }
                n = n + 2;
            }
            m = m + 2;
        }
        return false;
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
                        Map.deleteWorkerInCell(this);

                        setCoordX(newX);
                        setCoordY(newY);
                        setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());

                        Map.getWorkerInCell(newX, newY).setCoordX(m);
                        Map.getWorkerInCell(newX, newY).setCoordY(n);
                        Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(m, n).getAbbreviation());

                        Map.setWorkerInCell(m, n, Map.getWorkerInCell(newX, newY));
                        Map.deleteWorkerInCell(Map.getWorkerInCell(newX, newY));

                        Map.setWorkerInCell(newX, newY, this);
                    }
                    n = n + 2;
                }
                m = m + 2;
            }
        }
    }

}
