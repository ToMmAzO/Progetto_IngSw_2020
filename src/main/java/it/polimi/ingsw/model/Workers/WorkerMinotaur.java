package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;

public class WorkerMinotaur extends Worker {

    public WorkerMinotaur(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    /*
    public boolean canPush(int i,int j){

            if (i==this.getCoordX() && j!=this.getCoordY()){         //caso in cui il worker avversario è sulla stessa colonna di Minotauro
                int temp = this.getCoordY() - j;
                if(temp > 0){        //il worker avversario si trova a coord minori di Minotauro --> controllo se può spingerlo alla cella j-1
                    int m = j-1;
                    if(ActionManager.validCoords(i,m) && Map.noWorkerHere(i,m) && Map.getCellBlockType(i,m) != BlockType.CUPOLA)
                        return true;
                }
                if(temp < 0) {        //il worker avversario si trova a coord maggiori di Minotauro --> controllo se può spingerlo alla cella j+1
                    int m = j+1;
                    if (ActionManager.validCoords(i,m) && Map.noWorkerHere(i,m) && (Map.getCellBlockType(i,m) != BlockType.CUPOLA))
                        return true;
                }
            }

            if (i!=this.getCoordX() && j==this.getCoordY()){         //caso in cui il worker avversario è sulla stessa riga di Minotauro
                int temp = this.getCoordX() - i;
                if(temp > 0){        //il worker avversario si trova a coord minori di Minotauro --> controllo se può spingerlo alla cella i-1
                    int m = i-1;
                    if(ActionManager.validCoords(m,j) && Map.noWorkerHere(m,j) && Map.getCellBlockType(m,j) != BlockType.CUPOLA)
                        return true;
                }
                if(temp < 0) {        //il worker avversario si trova a coord maggiori di Minotauro --> controllo se può spingerlo alla cella i+1
                    int m = i+1;
                    if (ActionManager.validCoords(m,j) && Map.noWorkerHere(m,j) && Map.getCellBlockType(m,j) != BlockType.CUPOLA)
                        return true;
                }
            }
            return false;
    }
     */

    @Override
    public boolean canPush(int i, int j) {      //si potrebbe fare anche un for
        //BASSO SINISTRA
        if(i == getCoordX() - 1 && j == getCoordY() - 1){
            if (ActionManager.validCoords(i - 1, j - 1) && Map.noWorkerHere(i - 1, j - 1) && Map.getCellBlockType(i - 1, j - 1) != BlockType.CUPOLA){
                if(TurnManager.cannotGoUp()){
                    if(Map.getCellBlockType(i - 1, j - 1).getAbbreviation() <= getCoordZ()) {
                        return true;
                    }
                }else{
                    if(Map.getCellBlockType(i - 1, j - 1).getAbbreviation() <= getCoordZ() + 1) {
                        return true;
                    }
                }
            }
        }

        //BASSO
        if(i == getCoordX() - 1 && j == getCoordY()){
            if (ActionManager.validCoords(i - 1, j) && Map.noWorkerHere(i - 1, j) && Map.getCellBlockType(i - 1, j) != BlockType.CUPOLA){
                if(TurnManager.cannotGoUp()){
                    if(Map.getCellBlockType(i - 1, j).getAbbreviation() <= getCoordZ()) {
                        return true;
                    }
                }else{
                    if(Map.getCellBlockType(i - 1, j).getAbbreviation() <= getCoordZ() + 1) {
                        return true;
                    }
                }
            }
        }

        //BASSO DESTRA
        if(i == getCoordX() - 1 && j == getCoordY() + 1){
            if (ActionManager.validCoords(i - 1, j + 1) && Map.noWorkerHere(i - 1, j + 1) && Map.getCellBlockType(i - 1, j + 1) != BlockType.CUPOLA){
                if(TurnManager.cannotGoUp()){
                    if(Map.getCellBlockType(i - 1, j + 1).getAbbreviation() <= getCoordZ()) {
                        return true;
                    }
                }else{
                    if(Map.getCellBlockType(i - 1, j + 1).getAbbreviation() <= getCoordZ() + 1) {
                        return true;
                    }
                }
            }
        }

        //CENTRO SINISTRA
        if(i == getCoordX() && j == getCoordY() - 1){
            if (ActionManager.validCoords(i, j - 1) && Map.noWorkerHere(i, j - 1) && Map.getCellBlockType(i, j - 1) != BlockType.CUPOLA){
                if(TurnManager.cannotGoUp()){
                    if(Map.getCellBlockType(i, j - 1).getAbbreviation() <= getCoordZ()) {
                        return true;
                    }
                }else{
                    if(Map.getCellBlockType(i, j - 1).getAbbreviation() <= getCoordZ() + 1) {
                        return true;
                    }
                }
            }
        }

        //CENTRO DESTRA
        if(i == getCoordX() && j == getCoordY() + 1){
            if (ActionManager.validCoords(i, j + 1) && Map.noWorkerHere(i, j + 1) && Map.getCellBlockType(i, j + 1) != BlockType.CUPOLA){
                if(TurnManager.cannotGoUp()){
                    if(Map.getCellBlockType(i, j + 1).getAbbreviation() <= getCoordZ()) {
                        return true;
                    }
                }else{
                    if(Map.getCellBlockType(i, j + 1).getAbbreviation() <= getCoordZ() + 1) {
                        return true;
                    }
                }
            }
        }

        //ALTO SINISTRA
        if(i == getCoordX() + 1 && j == getCoordY() - 1){
            if (ActionManager.validCoords(i + 1, j - 1) && Map.noWorkerHere(i + 1, j - 1) && Map.getCellBlockType(i + 1, j - 1) != BlockType.CUPOLA){
                if(TurnManager.cannotGoUp()){
                    if(Map.getCellBlockType(i + 1, j - 1).getAbbreviation() <= getCoordZ()) {
                        return true;
                    }
                }else{
                    if(Map.getCellBlockType(i + 1, j - 1).getAbbreviation() <= getCoordZ() + 1) {
                        return true;
                    }
                }
            }
        }

        //ALTO
        if(i == getCoordX() + 1 && j == getCoordY()){
            if (ActionManager.validCoords(i + 1, j) && Map.noWorkerHere(i + 1, j) && Map.getCellBlockType(i + 1, j) != BlockType.CUPOLA){
                if(TurnManager.cannotGoUp()){
                    if(Map.getCellBlockType(i + 1, j).getAbbreviation() <= getCoordZ()) {
                        return true;
                    }
                }else{
                    if(Map.getCellBlockType(i + 1, j).getAbbreviation() <= getCoordZ() + 1) {
                        return true;
                    }
                }
            }
        }

        //ALTO DESTRA
        if(i == getCoordX() + 1 && j == getCoordY() + 1){
            if (ActionManager.validCoords(i + 1, j + 1) && Map.noWorkerHere(i + 1, j + 1) && Map.getCellBlockType(i + 1, j + 1) != BlockType.CUPOLA){
                if(TurnManager.cannotGoUp()){
                    if(Map.getCellBlockType(i + 1, j + 1).getAbbreviation() <= getCoordZ()) {
                        return true;
                    }
                }else{
                    if(Map.getCellBlockType(i + 1, j + 1).getAbbreviation() <= getCoordZ() + 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
    public boolean canMove() {
        for (int j = this.getCoordY() - 1; j <= this.getCoordY() + 1; j++) {
            for (int i = this.getCoordX() - 1; i <= this.getCoordX() + 1; i++) {
                if (!(i == getCoordX() && j == getCoordY()) && ActionManager.validCoords(i, j) && Map.getCellBlockType(i, j) != BlockType.CUPOLA){
                    if (TurnManager.cannotGoUp()){
                        if (Map.getCellBlockType(i, j).getAbbreviation() <= this.getCoordZ())
                            if (Map.noWorkerHere(i, j))
                                return true;
                            if (!Map.noWorkerHere(i, j) && (!Map.getWorkerInCell(i, j).getIdWorker().substring(0, 3).equals(getIdWorker().substring(0, 3))))          //controllo separato per verificare i casi in cui può spostare il worker avversario
                                if (this.canPush(i, j))
                                    return true;
                    }
                    if (!TurnManager.cannotGoUp()) {
                        if (Map.getCellBlockType(i, j).getAbbreviation() <= this.getCoordZ() + 1)
                            if (Map.noWorkerHere(i, j))
                                return true;
                            if (!Map.noWorkerHere(i, j) && (!Map.getWorkerInCell(i, j).getIdWorker().substring(0, 3).equals(getIdWorker().substring(0, 3))))          //controllo separato per verificare i casi in cui può spostare il worker avversario
                                if (this.canPush(i, j))
                                    return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void changePosition(int newX, int newY){
        if(Map.noWorkerHere(newX, newY)) {
            super.changePosition(newX, newY);
        }else{
            //BASSO SINISTRA
            if(newX == getCoordX() - 1 && newY == getCoordY() - 1){
                Map.deleteWorkerInCell(this);

                Map.getWorkerInCell(newX, newY).setCoordX(newX - 1);
                Map.getWorkerInCell(newX, newY).setCoordY(newY - 1);
                Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(newX - 1, newY - 1).getAbbreviation());

                Map.setWorkerInCell(newX - 1, newY - 1, Map.getWorkerInCell(newX, newY));
                //Map.deleteWorkerInCell(Map.getWorkerInCell(newX, newY));     //non necessaria(?) viene sovrascritto con il comando successivo
                Map.setWorkerInCell(newX, newY, this);

                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
            }

            //BASSO
            if(newX == getCoordX() - 1 && newY == getCoordY()){
                Map.deleteWorkerInCell(this);

                Map.getWorkerInCell(newX, newY).setCoordX(newX - 1);
                Map.getWorkerInCell(newX, newY).setCoordY(newY);
                Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(newX - 1, newY).getAbbreviation());

                Map.setWorkerInCell(newX - 1, newY, Map.getWorkerInCell(newX, newY));
                //Map.deleteWorkerInCell(Map.getWorkerInCell(newX, newY));     //non necessaria(?) viene sovrascritto con il comando successivo
                Map.setWorkerInCell(newX, newY, this);

                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
            }

            //BASSO DESTRA
            if(newX == getCoordX() - 1 && newY == getCoordY() + 1){
                Map.deleteWorkerInCell(this);

                Map.getWorkerInCell(newX, newY).setCoordX(newX - 1);
                Map.getWorkerInCell(newX, newY).setCoordY(newY + 1);
                Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(newX - 1, newY + 1).getAbbreviation());

                Map.setWorkerInCell(newX - 1, newY + 1, Map.getWorkerInCell(newX, newY));
                //Map.deleteWorkerInCell(Map.getWorkerInCell(newX, newY));     //non necessaria(?) viene sovrascritto con il comando successivo
                Map.setWorkerInCell(newX, newY, this);

                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
            }

            //CENTRO SINISTRA
            if(newX == getCoordX() && newY == getCoordY() - 1){
                Map.deleteWorkerInCell(this);

                Map.getWorkerInCell(newX, newY).setCoordX(newX);
                Map.getWorkerInCell(newX, newY).setCoordY(newY - 1);
                Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(newX, newY - 1).getAbbreviation());

                Map.setWorkerInCell(newX, newY - 1, Map.getWorkerInCell(newX, newY));
                //Map.deleteWorkerInCell(Map.getWorkerInCell(newX, newY));     //non necessaria(?) viene sovrascritto con il comando successivo
                Map.setWorkerInCell(newX, newY, this);

                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
            }

            //CENTRO DESTRA
            if(newX == getCoordX() && newY == getCoordY() + 1){
                Map.deleteWorkerInCell(this);

                Map.getWorkerInCell(newX, newY).setCoordX(newX);
                Map.getWorkerInCell(newX, newY).setCoordY(newY + 1);
                Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(newX, newY + 1).getAbbreviation());

                Map.setWorkerInCell(newX, newY + 1, Map.getWorkerInCell(newX, newY));
                //Map.deleteWorkerInCell(Map.getWorkerInCell(newX, newY));     //non necessaria(?) viene sovrascritto con il comando successivo
                Map.setWorkerInCell(newX, newY, this);

                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
            }

            //ALTO SINISTRA
            if(newX == getCoordX() + 1 && newY == getCoordY() - 1){
                Map.deleteWorkerInCell(this);

                Map.getWorkerInCell(newX, newY).setCoordX(newX + 1);
                Map.getWorkerInCell(newX, newY).setCoordY(newY - 1);
                Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(newX + 1, newY - 1).getAbbreviation());

                Map.setWorkerInCell(newX + 1, newY - 1, Map.getWorkerInCell(newX, newY));
                //Map.deleteWorkerInCell(Map.getWorkerInCell(newX, newY));     //non necessaria(?) viene sovrascritto con il comando successivo
                Map.setWorkerInCell(newX, newY, this);

                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
            }

            //ALTO
            if(newX == getCoordX() + 1 && newY == getCoordY()){
                Map.deleteWorkerInCell(this);

                Map.getWorkerInCell(newX, newY).setCoordX(newX + 1);
                Map.getWorkerInCell(newX, newY).setCoordY(newY);
                Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(newX + 1, newY).getAbbreviation());

                Map.setWorkerInCell(newX + 1, newY, Map.getWorkerInCell(newX, newY));
                //Map.deleteWorkerInCell(Map.getWorkerInCell(newX, newY));     //non necessaria(?) viene sovrascritto con il comando successivo
                Map.setWorkerInCell(newX, newY, this);

                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
            }

            //ALTO DESTRA
            if(newX == getCoordX() + 1 && newY == getCoordY() + 1){
                Map.deleteWorkerInCell(this);

                Map.getWorkerInCell(newX, newY).setCoordX(newX + 1);
                Map.getWorkerInCell(newX, newY).setCoordY(newY + 1);
                Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(newX + 1, newY + 1).getAbbreviation());

                Map.setWorkerInCell(newX + 1, newY + 1, Map.getWorkerInCell(newX, newY));
                //Map.deleteWorkerInCell(Map.getWorkerInCell(newX, newY));     //non necessaria(?) viene sovrascritto con il comando successivo
                Map.setWorkerInCell(newX, newY, this);

                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
            }

            /*
            int m = getCoordX() - 2;
            int n = getCoordY() - 2;

            for(int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {
                for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
                    if(newX == i && newY == j) {
                        Map.deleteWorkerInCell(this);

                        Map.getWorkerInCell(newX, newY).setCoordX(m);
                        Map.getWorkerInCell(newX, newY).setCoordY(n);
                        Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(m, n).getAbbreviation());

                        Map.setWorkerInCell(m, n, Map.getWorkerInCell(newX, newY));
                        //Map.deleteWorkerInCell(Map.getWorkerInCell(newX, newY));     //non necessaria(?) viene sovrascritto con il comando successivo
                        Map.setWorkerInCell(newX, newY, this);

                        setCoordX(newX);
                        setCoordY(newY);
                        setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
                    }
                    n = n + 2;
                }
                m = m + 2;
            }

             */
        }
    }

}
