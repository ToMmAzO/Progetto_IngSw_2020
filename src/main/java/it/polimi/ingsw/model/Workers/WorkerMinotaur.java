package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;

public class WorkerMinotaur extends Worker {

    public WorkerMinotaur(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    public boolean canPush(int i,int j){

            if (i==this.getCoordX() && j!=this.getCoordY()){         //caso in cui il worker avversario è sulla stessa colonna di Minotauro
                int temp = this.getCoordY() - j;
                if(temp > 0){        //il worker avversario si trova a coord minori di Minotauro --> controllo se può spingerlo alla cella i-1
                    if(ActionManager.validCoords(i-1,j) && Map.noWorkerHere(i-1,j) && Map.getCellBlockType(i-1,j) != BlockType.CUPOLA)
                        return true;
                }
                if(temp < 0) {        //il worker avversario si trova a coord maggiori di Minotauro --> controllo se può spingerlo alla cella i+1
                    if (ActionManager.validCoords(i + 1, j) && Map.noWorkerHere(i + 1, j) && (Map.getCellBlockType(i + 1, j) != BlockType.CUPOLA))
                        return true;
                }
            }

            if (i!=this.getCoordX() && j==this.getCoordY()){         //caso in cui il worker avversario è sulla stessa riga di Minotauro
                int temp = this.getCoordX() - i;
                if(temp > 0){        //il worker avversario si trova a coord minori di Minotauro --> controllo se può spingerlo alla cella j-1
                    if(ActionManager.validCoords(i,j-1) && Map.noWorkerHere(i,j-1) && Map.getCellBlockType(i,j-1) != BlockType.CUPOLA)
                        return true;
                }
                if(temp < 0) {        //il worker avversario si trova a coord maggiori di Minotauro --> controllo se può spingerlo alla cella j+1
                    if (ActionManager.validCoords(i, j+1) && Map.noWorkerHere(i, j+1) && Map.getCellBlockType(i , j+1) != BlockType.CUPOLA)
                        return true;
                }
            }
            return false;
    }




    @Override
    public boolean canMove() {
        for (int j = this.getCoordY() - 1; j <= this.getCoordY() + 1; j++) {
            for (int i = this.getCoordX() - 1; i <= this.getCoordX() + 1; i++) {
                if (ActionManager.validCoords(i, j)) {
                    if (TurnManager.cannotGoUp()){
                        if ((Map.getCellBlockType(i, j).getAbbreviation() <= this.getCoordZ()) && (Map.getCellBlockType(i, j) != BlockType.CUPOLA))
                            if (Map.noWorkerHere(i, j))
                                return true;
                            if (!Map.noWorkerHere(i, j))          //controllo separato per verificare i casi in cui può spostare il worker avversario
                                if (this.canPush(i, j))
                                    return true;
                    }
                    if (!TurnManager.cannotGoUp()) {
                        if ((Map.getCellBlockType(i, j).getAbbreviation() <= this.getCoordZ() + 1) && (Map.getCellBlockType(i, j) != BlockType.CUPOLA))
                            if (Map.noWorkerHere(i, j))
                                return true;
                            if (!Map.noWorkerHere(i, j))          //controllo separato per verificare i casi in cui può spostare il worker avversario
                                if (this.canPush(i, j))
                                    return true;
                    }
                }
            }
        }
        return false;
    }
        /* int m = getCoordX() - 2;
        int n = getCoordY() - 2;

        for (int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {           //se no funziona --> while
            for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
                if (!(i == getCoordX() && j == getCoordY()) && ActionManager.validCoords(i, j) && Map.getCellBlockType(i, j) != BlockType.CUPOLA){
                    if(TurnManager.cannotGoUp()){
                        if(Map.getCellBlockType(i, j).getAbbreviation() <= getCoordZ()) {
                            if (ActionManager.validCoords(m, n) && Map.getCellBlockType(m, n).getAbbreviation() <= Map.getCellBlockType(i, j).getAbbreviation() && Map.getCellBlockType(m, n) != BlockType.CUPOLA) {
                                return true;
                            }
                        }
                    }else{
                        if(Map.getCellBlockType(i, j).getAbbreviation() <= getCoordZ() + 1) {
                            if (ActionManager.validCoords(m, n) && Map.getCellBlockType(m, n).getAbbreviation() <= Map.getCellBlockType(i, j).getAbbreviation() + 1 && Map.getCellBlockType(m, n) != BlockType.CUPOLA) {
                                return true;
                            }
                        }
                    }
                }
                n = n + 2;
            }
            m = m + 2;
        }
        return false;*/


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
