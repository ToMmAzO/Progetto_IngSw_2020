package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;

public class WorkerMinotaur extends Worker {

    public WorkerMinotaur(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public boolean canPush(int i, int j) {
        //ALTO SINISTRA
        if(i == getCoordX() - 1 && j == getCoordY() - 1){
            if (ActionManager.getInstance().validCoords(i - 1, j - 1) && Map.getInstance().noWorkerHere(i - 1, j - 1) && Map.getInstance().getCellBlockType(i - 1, j - 1) != BlockType.CUPOLA){
                if(TurnManager.getInstance().cannotGoUp()){
                    if(Map.getInstance().getCellBlockType(i - 1, j - 1).getAbbreviation() <= Map.getInstance().getWorkerInCell(i,j).getCoordZ()) {
                        return true;
                    }
                }else{
                    if(Map.getInstance().getCellBlockType(i - 1, j - 1).getAbbreviation() <= Map.getInstance().getWorkerInCell(i,j).getCoordZ() + 1) {
                        return true;
                    }
                }
            }
        }

        //BASSO
        if(i == getCoordX() - 1 && j == getCoordY()){
            if (ActionManager.getInstance().validCoords(i - 1, j) && Map.getInstance().noWorkerHere(i - 1, j) && Map.getInstance().getCellBlockType(i - 1, j) != BlockType.CUPOLA){
                if(TurnManager.getInstance().cannotGoUp()){
                    if(Map.getInstance().getCellBlockType(i - 1, j).getAbbreviation() <= Map.getInstance().getWorkerInCell(i,j).getCoordZ()) {
                        return true;
                    }
                }else{
                    if(Map.getInstance().getCellBlockType(i - 1, j).getAbbreviation() <= Map.getInstance().getWorkerInCell(i,j).getCoordZ() + 1) {
                        return true;
                    }
                }
            }
        }

        //BASSO DESTRA
        if(i == getCoordX() - 1 && j == getCoordY() + 1){
            if (ActionManager.getInstance().validCoords(i - 1, j + 1) && Map.getInstance().noWorkerHere(i - 1, j + 1) && Map.getInstance().getCellBlockType(i - 1, j + 1) != BlockType.CUPOLA){
                if(TurnManager.getInstance().cannotGoUp()){
                    if(Map.getInstance().getCellBlockType(i - 1, j + 1).getAbbreviation() <= Map.getInstance().getWorkerInCell(i,j).getCoordZ()) {
                        return true;
                    }
                }else{
                    if(Map.getInstance().getCellBlockType(i - 1, j + 1).getAbbreviation() <= Map.getInstance().getWorkerInCell(i,j).getCoordZ() + 1) {
                        return true;
                    }
                }
            }
        }

        //CENTRO SINISTRA
        if(i == getCoordX() && j == getCoordY() - 1){
            if (ActionManager.getInstance().validCoords(i, j - 1) && Map.getInstance().noWorkerHere(i, j - 1) && Map.getInstance().getCellBlockType(i, j - 1) != BlockType.CUPOLA){
                if(TurnManager.getInstance().cannotGoUp()){
                    if(Map.getInstance().getCellBlockType(i, j - 1).getAbbreviation() <= Map.getInstance().getWorkerInCell(i,j).getCoordZ()) {
                        return true;
                    }
                }else{
                    if(Map.getInstance().getCellBlockType(i, j - 1).getAbbreviation() <= Map.getInstance().getWorkerInCell(i,j).getCoordZ() + 1) {
                        return true;
                    }
                }
            }
        }

        //CENTRO DESTRA
        if(i == getCoordX() && j == getCoordY() + 1){
            if (ActionManager.getInstance().validCoords(i, j + 1) && Map.getInstance().noWorkerHere(i, j + 1) && Map.getInstance().getCellBlockType(i, j + 1) != BlockType.CUPOLA){
                if(TurnManager.getInstance().cannotGoUp()){
                    if(Map.getInstance().getCellBlockType(i, j + 1).getAbbreviation() <= Map.getInstance().getWorkerInCell(i,j).getCoordZ()) {
                        return true;
                    }
                }else{
                    if(Map.getInstance().getCellBlockType(i, j + 1).getAbbreviation() <= Map.getInstance().getWorkerInCell(i,j).getCoordZ() + 1) {
                        return true;
                    }
                }
            }
        }

        //ALTO SINISTRA
        if(i == getCoordX() + 1 && j == getCoordY() - 1){
            if (ActionManager.getInstance().validCoords(i + 1, j - 1) && Map.getInstance().noWorkerHere(i + 1, j - 1) && Map.getInstance().getCellBlockType(i + 1, j - 1) != BlockType.CUPOLA){
                if(TurnManager.getInstance().cannotGoUp()){
                    if(Map.getInstance().getCellBlockType(i + 1, j - 1).getAbbreviation() <= Map.getInstance().getWorkerInCell(i,j).getCoordZ()) {
                        return true;
                    }
                }else{
                    if(Map.getInstance().getCellBlockType(i + 1, j - 1).getAbbreviation() <= Map.getInstance().getWorkerInCell(i,j).getCoordZ() + 1) {
                        return true;
                    }
                }
            }
        }

        //ALTO
        if(i == getCoordX() + 1 && j == getCoordY()){
            if (ActionManager.getInstance().validCoords(i + 1, j) && Map.getInstance().noWorkerHere(i + 1, j) && Map.getInstance().getCellBlockType(i + 1, j) != BlockType.CUPOLA){
                if(TurnManager.getInstance().cannotGoUp()){
                    if(Map.getInstance().getCellBlockType(i + 1, j).getAbbreviation() <= Map.getInstance().getWorkerInCell(i,j).getCoordZ()) {
                        return true;
                    }
                }else{
                    if(Map.getInstance().getCellBlockType(i + 1, j).getAbbreviation() <= Map.getInstance().getWorkerInCell(i,j).getCoordZ() + 1) {
                        return true;
                    }
                }
            }
        }

        //ALTO DESTRA
        if(i == getCoordX() + 1 && j == getCoordY() + 1){
            if (ActionManager.getInstance().validCoords(i + 1, j + 1) && Map.getInstance().noWorkerHere(i + 1, j + 1) && Map.getInstance().getCellBlockType(i + 1, j + 1) != BlockType.CUPOLA){
                if(TurnManager.getInstance().cannotGoUp()){
                    if(Map.getInstance().getCellBlockType(i + 1, j + 1).getAbbreviation() <= Map.getInstance().getWorkerInCell(i, j).getCoordZ()){
                        return true;
                    }
                }else{
                    if(Map.getInstance().getCellBlockType(i + 1, j + 1).getAbbreviation() <= Map.getInstance().getWorkerInCell(i,j).getCoordZ() + 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
    public boolean canMove() {
        for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
            for (int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {
                if (!(i == getCoordX() && j == getCoordY()) && ActionManager.getInstance().validCoords(i, j) && Map.getInstance().getCellBlockType(i, j) != BlockType.CUPOLA){
                    if (TurnManager.getInstance().cannotGoUp()){
                        if (Map.getInstance().getCellBlockType(i, j).getAbbreviation() <= getCoordZ())
                            if (Map.getInstance().noWorkerHere(i, j))
                                return true;
                            if (!Map.getInstance().noWorkerHere(i, j) && (!Map.getInstance().getWorkerInCell(i, j).getIdWorker().substring(0, 3).equals(getIdWorker().substring(0, 3))))          //controllo separato per verificare i casi in cui può spostare il worker avversario
                                if (this.canPush(i, j))
                                    return true;
                    }
                    if (!TurnManager.getInstance().cannotGoUp()) {
                        if (Map.getInstance().getCellBlockType(i, j).getAbbreviation() <= getCoordZ() + 1)
                            if (Map.getInstance().noWorkerHere(i, j))
                                return true;
                            if (!Map.getInstance().noWorkerHere(i, j) && (!Map.getInstance().getWorkerInCell(i, j).getIdWorker().substring(0, 3).equals(getIdWorker().substring(0, 3))))          //controllo separato per verificare i casi in cui può spostare il worker avversario
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
        if(Map.getInstance().noWorkerHere(newX, newY)) {
            super.changePosition(newX, newY);
        }else{
            //BASSO SINISTRA
            if(newX == getCoordX() - 1 && newY == getCoordY() - 1){
                Map.getInstance().deleteWorkerInCell(this);

                Map.getInstance().getWorkerInCell(newX, newY).setCoordX(newX - 1);
                Map.getInstance().getWorkerInCell(newX, newY).setCoordY(newY - 1);
                Map.getInstance().getWorkerInCell(newX, newY).setCoordZ(Map.getInstance().getCellBlockType(newX - 1, newY - 1).getAbbreviation());

                Map.getInstance().setWorkerInCell(newX - 1, newY - 1, Map.getInstance().getWorkerInCell(newX, newY));
                Map.getInstance().setWorkerInCell(newX, newY, this);

                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getInstance().getCellBlockType(newX, newY).getAbbreviation());
            }

            //BASSO
            if(newX == getCoordX() - 1 && newY == getCoordY()){
                Map.getInstance().deleteWorkerInCell(this);

                Map.getInstance().getWorkerInCell(newX, newY).setCoordX(newX - 1);
                Map.getInstance().getWorkerInCell(newX, newY).setCoordY(newY);
                Map.getInstance().getWorkerInCell(newX, newY).setCoordZ(Map.getInstance().getCellBlockType(newX - 1, newY).getAbbreviation());

                Map.getInstance().setWorkerInCell(newX - 1, newY, Map.getInstance().getWorkerInCell(newX, newY));
                Map.getInstance().setWorkerInCell(newX, newY, this);

                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getInstance().getCellBlockType(newX, newY).getAbbreviation());
            }

            //BASSO DESTRA
            if(newX == getCoordX() - 1 && newY == getCoordY() + 1){
                Map.getInstance().deleteWorkerInCell(this);

                Map.getInstance().getWorkerInCell(newX, newY).setCoordX(newX - 1);
                Map.getInstance().getWorkerInCell(newX, newY).setCoordY(newY + 1);
                Map.getInstance().getWorkerInCell(newX, newY).setCoordZ(Map.getInstance().getCellBlockType(newX - 1, newY + 1).getAbbreviation());

                Map.getInstance().setWorkerInCell(newX - 1, newY + 1, Map.getInstance().getWorkerInCell(newX, newY));
                Map.getInstance().setWorkerInCell(newX, newY, this);

                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getInstance().getCellBlockType(newX, newY).getAbbreviation());
            }

            //CENTRO SINISTRA
            if(newX == getCoordX() && newY == getCoordY() - 1){
                Map.getInstance().deleteWorkerInCell(this);

                Map.getInstance().getWorkerInCell(newX, newY).setCoordX(newX);
                Map.getInstance().getWorkerInCell(newX, newY).setCoordY(newY - 1);
                Map.getInstance().getWorkerInCell(newX, newY).setCoordZ(Map.getInstance().getCellBlockType(newX, newY - 1).getAbbreviation());

                Map.getInstance().setWorkerInCell(newX, newY - 1, Map.getInstance().getWorkerInCell(newX, newY));
                Map.getInstance().setWorkerInCell(newX, newY, this);

                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getInstance().getCellBlockType(newX, newY).getAbbreviation());
            }

            //CENTRO DESTRA
            if(newX == getCoordX() && newY == getCoordY() + 1){
                Map.getInstance().deleteWorkerInCell(this);

                Map.getInstance().getWorkerInCell(newX, newY).setCoordX(newX);
                Map.getInstance().getWorkerInCell(newX, newY).setCoordY(newY + 1);
                Map.getInstance().getWorkerInCell(newX, newY).setCoordZ(Map.getInstance().getCellBlockType(newX, newY + 1).getAbbreviation());

                Map.getInstance().setWorkerInCell(newX, newY + 1, Map.getInstance().getWorkerInCell(newX, newY));
                Map.getInstance().setWorkerInCell(newX, newY, this);

                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getInstance().getCellBlockType(newX, newY).getAbbreviation());
            }

            //ALTO SINISTRA
            if(newX == getCoordX() + 1 && newY == getCoordY() - 1){
                Map.getInstance().deleteWorkerInCell(this);

                Map.getInstance().getWorkerInCell(newX, newY).setCoordX(newX + 1);
                Map.getInstance().getWorkerInCell(newX, newY).setCoordY(newY - 1);
                Map.getInstance().getWorkerInCell(newX, newY).setCoordZ(Map.getInstance().getCellBlockType(newX + 1, newY - 1).getAbbreviation());

                Map.getInstance().setWorkerInCell(newX + 1, newY - 1, Map.getInstance().getWorkerInCell(newX, newY));
                Map.getInstance().setWorkerInCell(newX, newY, this);

                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getInstance().getCellBlockType(newX, newY).getAbbreviation());
            }

            //ALTO
            if(newX == getCoordX() + 1 && newY == getCoordY()){
                Map.getInstance().deleteWorkerInCell(this);

                Map.getInstance().getWorkerInCell(newX, newY).setCoordX(newX + 1);
                Map.getInstance().getWorkerInCell(newX, newY).setCoordY(newY);
                Map.getInstance().getWorkerInCell(newX, newY).setCoordZ(Map.getInstance().getCellBlockType(newX + 1, newY).getAbbreviation());

                Map.getInstance().setWorkerInCell(newX + 1, newY, Map.getInstance().getWorkerInCell(newX, newY));
                Map.getInstance().setWorkerInCell(newX, newY, this);

                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getInstance().getCellBlockType(newX, newY).getAbbreviation());
            }

            //ALTO DESTRA
            if(newX == getCoordX() + 1 && newY == getCoordY() + 1){
                Map.getInstance().deleteWorkerInCell(this);

                Map.getInstance().getWorkerInCell(newX, newY).setCoordX(newX + 1);
                Map.getInstance().getWorkerInCell(newX, newY).setCoordY(newY + 1);
                Map.getInstance().getWorkerInCell(newX, newY).setCoordZ(Map.getInstance().getCellBlockType(newX + 1, newY + 1).getAbbreviation());

                Map.getInstance().setWorkerInCell(newX + 1, newY + 1, Map.getInstance().getWorkerInCell(newX, newY));
                Map.getInstance().setWorkerInCell(newX, newY, this);

                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getInstance().getCellBlockType(newX, newY).getAbbreviation());
            }
            notify(GameManager.getInstance().getCurrPlayer());
        }
    }
}
