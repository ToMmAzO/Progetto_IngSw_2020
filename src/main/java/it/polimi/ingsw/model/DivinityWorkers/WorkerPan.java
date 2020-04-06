package it.polimi.ingsw.model.DivinityWorkers;


import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;


public class WorkerPan extends Worker {

    public WorkerPan(Player p, int coordX, int coordY) {
        super(p, coordX, coordY);
    }

    @Override
    public void changePosition(int newX, int newY){
        if((getCoordZ() == 3 && Map.getCellBlockType(newX, newY).getAbbreviation() == 1) || (getCoordZ() == 3 && Map.getCellBlockType(newX, newY).getAbbreviation() == 0) || (getCoordZ() == 2 && Map.getCellBlockType(newX, newY).getAbbreviation() == 0) || (getCoordZ() == 2 && Map.getCellBlockType(newX, newY).getAbbreviation() == 3)){
                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
                //setWorkerInCell NO?
                GameManager.victory();
            }else{
                setCoordX(newX);
                setCoordY(newY);
                setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());
            }


    }
}
