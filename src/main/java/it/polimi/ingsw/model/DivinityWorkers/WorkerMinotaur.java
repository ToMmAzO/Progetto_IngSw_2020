package it.polimi.ingsw.model.DivinityWorkers;

import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class WorkerMinotaur extends Worker {

    public WorkerMinotaur(Player p, int coordX, int coordY) {
        super(p, coordX, coordY);
    }

    @Override
    public boolean canMove() {
        for(int i = getCoordX() - 1; i < getCoordX() + 1; i++){
            for(int j = getCoordY() - 1; j < getCoordY() + 1; j++) {
                if ((i == getCoordX() && j == getCoordY()) || Map.getCellBlockType(i, j).getAbbreviation() >= getCoordZ() + 2 || Map.getCellBlockType(i, j) == BlockType.CUPOLA) {
                    //BASSO SINISTRA
                    if(i == getCoordX() - 1 && j == getCoordY() - 1 && !Map.noWorkerHere(i, j)){
                        if(getCoordX() - 2 < 0 || getCoordX() - 2 > 4 || getCoordY() - 2 < 0 || getCoordY() - 2 > 4 || Map.getCellBlockType(getCoordX() - 2, getCoordY() - 2).getAbbreviation() >= Map.getCellBlockType(i, j).getAbbreviation() + 2 || Map.getCellBlockType(getCoordX() - 2, getCoordY() - 2) == BlockType.CUPOLA){
                            return false;
                        }
                    }

                    //BASSO
                    if(i == getCoordX() - 1 && j == getCoordY() && !Map.noWorkerHere(i, j)){
                        if(getCoordX() - 2 < 0 || getCoordX() - 2 > 4 || getCoordY() < 0 || getCoordY() > 4 || Map.getCellBlockType(getCoordX() - 2, getCoordY()).getAbbreviation() >= Map.getCellBlockType(i, j).getAbbreviation() + 2 || Map.getCellBlockType(getCoordX() - 2, getCoordY()) == BlockType.CUPOLA){
                            return false;
                        }
                    }

                    //BASSO DESTRA
                    if(i == getCoordX() - 1 && j == getCoordY() + 1 && !Map.noWorkerHere(i, j)){
                        if(getCoordX() - 2 < 0 || getCoordX() - 2 > 4 || getCoordY() + 2 < 0 || getCoordY() + 2 > 4 || Map.getCellBlockType(getCoordX() - 2, getCoordY() + 2).getAbbreviation() >= Map.getCellBlockType(i, j).getAbbreviation() + 2 || Map.getCellBlockType(getCoordX() - 2, getCoordY() + 2) == BlockType.CUPOLA){
                            return false;
                        }
                    }

                    //CENTRO SINISTRA
                    if(i == getCoordX() && j == getCoordY() - 1 && !Map.noWorkerHere(i, j)){
                        if(getCoordX() < 0 || getCoordX() > 4 || getCoordY() - 2 < 0 || getCoordY() - 2 > 4 || Map.getCellBlockType(getCoordX(), getCoordY() - 2).getAbbreviation() >= Map.getCellBlockType(i, j).getAbbreviation() + 2 || Map.getCellBlockType(getCoordX(), getCoordY() - 2) == BlockType.CUPOLA){
                            return false;
                        }
                    }

                    //CENTRO DESTRA
                    if(i == getCoordX() - 1 && j == getCoordY() - 1 && !Map.noWorkerHere(i, j)){
                        if(getCoordX() < 0 || getCoordX() > 4 || getCoordY() + 2 < 0 || getCoordY() + 2 > 4 || Map.getCellBlockType(getCoordX(), getCoordY() + 2).getAbbreviation() >= Map.getCellBlockType(i, j).getAbbreviation() + 2 || Map.getCellBlockType(getCoordX(), getCoordY() + 2) == BlockType.CUPOLA){
                            return false;
                        }
                    }

                    //ALTO SINISTRA
                    if(i == getCoordX() + 1 && j == getCoordY() - 1 && !Map.noWorkerHere(i, j)){
                        if(getCoordX() + 2 < 0 || getCoordX() + 2 > 4 || getCoordY() - 2 < 0 || getCoordY() - 2 > 4 || Map.getCellBlockType(getCoordX() + 2, getCoordY() - 2).getAbbreviation() >= Map.getCellBlockType(i, j).getAbbreviation() + 2 || Map.getCellBlockType(getCoordX() + 2, getCoordY() - 2) == BlockType.CUPOLA){
                            return false;
                        }
                    }

                    //ALTO
                    if(i == getCoordX() + 1 && j == getCoordY() && !Map.noWorkerHere(i, j)){
                        if(getCoordX() + 2 < 0 || getCoordX() + 2 > 4 || getCoordY() < 0 || getCoordY() > 4 || Map.getCellBlockType(getCoordX() + 2, getCoordY()).getAbbreviation() >= Map.getCellBlockType(i, j).getAbbreviation() + 2 || Map.getCellBlockType(getCoordX() + 2, getCoordY()) == BlockType.CUPOLA){
                            return false;
                        }
                    }

                    //ALTO DESTRA
                    if(i == getCoordX() + 1 && j == getCoordY() + 1 && !Map.noWorkerHere(i, j)){
                        if(getCoordX() + 2 < 0 || getCoordX() + 2 > 4 || getCoordY() + 2 < 0 || getCoordY() + 2 > 4 || Map.getCellBlockType(getCoordX() + 2, getCoordY() + 2).getAbbreviation() >= Map.getCellBlockType(i, j).getAbbreviation() + 2 || Map.getCellBlockType(getCoordX() + 2, getCoordY() + 2) == BlockType.CUPOLA){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void changePosition(int newX, int newY){
        if(Map.noWorkerHere(newX, newY)) {
            super.changePosition(newX, newY);
        }else{
            //BASSO SINISTRA
            if(newX == getCoordX() - 1 && newY == getCoordY() - 1){
                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
                Map.getWorkerInCell(newX, newY).setCoordX(newX - 1);
                Map.getWorkerInCell(newX, newY).setCoordY(newY - 1);
                Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(newX - 1, newY - 1).getAbbreviation());
            }

            //BASSO
            if(newX == getCoordX() - 1 && newY == getCoordY()){
                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
                Map.getWorkerInCell(newX, newY).setCoordX(newX - 1);
                Map.getWorkerInCell(newX, newY).setCoordY(newY);        //inutile in quanto già sue coordinate, fatto per capire
                Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(newX - 1, newY).getAbbreviation());
            }

            //BASSO DESTRA
            if(newX == getCoordX() - 1 && newY == getCoordY() + 1){
                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
                Map.getWorkerInCell(newX, newY).setCoordX(newX - 1);
                Map.getWorkerInCell(newX, newY).setCoordY(newY + 1);
                Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(newX - 1, newY + 1).getAbbreviation());
            }

            //CENTRO SINISTRA
            if(newX == getCoordX() && newY == getCoordY() - 1){
                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
                Map.getWorkerInCell(newX, newY).setCoordX(newX);
                Map.getWorkerInCell(newX, newY).setCoordY(newY - 1);
                Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(newX, newY - 1).getAbbreviation());
            }

            //CENTRO DESTRA
            if(newX == getCoordX() && newY == getCoordY() + 1){
                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
                Map.getWorkerInCell(newX, newY).setCoordX(newX);
                Map.getWorkerInCell(newX, newY).setCoordY(newY + 1);
                Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(newX, newY + 1).getAbbreviation());
            }

            //ALTO SINISTRA
            if(newX == getCoordX() + 1 && newY == getCoordY() - 1){
                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
                Map.getWorkerInCell(newX, newY).setCoordX(newX + 1);
                Map.getWorkerInCell(newX, newY).setCoordY(newY - 1);
                Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(newX + 1, newY - 1).getAbbreviation());
            }

            //ALTO
            if(newX == getCoordX() + 1 && newY == getCoordY()){
                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
                Map.getWorkerInCell(newX, newY).setCoordX(newX + 1);
                Map.getWorkerInCell(newX, newY).setCoordY(newY);
                Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(newX + 1, newY).getAbbreviation());
            }

            //ALTO DESTRA
            if(newX == getCoordX() + 1 && newY == getCoordY() + 1){
                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
                Map.getWorkerInCell(newX, newY).setCoordX(newX + 1);
                Map.getWorkerInCell(newX, newY).setCoordY(newY + 1);
                Map.getWorkerInCell(newX, newY).setCoordZ(Map.getCellBlockType(newX + 1, newY + 1).getAbbreviation());
            }
        }
    }

}
