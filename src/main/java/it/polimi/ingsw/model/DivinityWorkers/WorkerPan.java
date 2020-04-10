package it.polimi.ingsw.model.DivinityWorkers;


import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Worker;


public class WorkerPan extends Worker {

    public WorkerPan(Color c, int coordX, int coordY) {
        super(c, coordX, coordY);
    }

    @Override
    public void changePosition(int newX, int newY){
        if((getCoordZ() == 3 && Map.getCellBlockType(newX, newY).getAbbreviation() == 1) || (getCoordZ() == 3 && Map.getCellBlockType(newX, newY).getAbbreviation() == 0) || (getCoordZ() == 2 && Map.getCellBlockType(newX, newY).getAbbreviation() == 0) || (getCoordZ() == 2 && Map.getCellBlockType(newX, newY).getAbbreviation() == 3)){
            setCoordX(newX);
            setCoordY(newY);
            setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
            //setWorkerInCell NO?
            GameManager.setVictory();
        }else{
            setCoordX(newX);
            setCoordY(newY);
            setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
        }
    }
}
