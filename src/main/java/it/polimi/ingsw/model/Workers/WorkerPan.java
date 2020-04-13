package it.polimi.ingsw.model.Workers;


import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Board.Map;


public class WorkerPan extends Worker {

    public WorkerPan(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public void changePosition(int newX, int newY){
        if((getCoordZ() == 3 && Map.getCellBlockType(newX, newY).getAbbreviation() == 1) || (getCoordZ() == 3 && Map.getCellBlockType(newX, newY).getAbbreviation() == 0) || (getCoordZ() == 2 && Map.getCellBlockType(newX, newY).getAbbreviation() == 0) || (getCoordZ() == 2 && Map.getCellBlockType(newX, newY).getAbbreviation() == 3)){
            Map.deleteWorkerInCell(this);
            setCoordX(newX);
            setCoordY(newY);
            setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
            Map.setWorkerInCell(newX, newY, this);
            GameManager.setVictory();
        }else{
            Map.deleteWorkerInCell(this);
            setCoordX(newX);
            setCoordY(newY);
            setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
            Map.setWorkerInCell(newX, newY, this);
        }
    }
}
