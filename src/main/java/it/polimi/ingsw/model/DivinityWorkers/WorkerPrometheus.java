package it.polimi.ingsw.model.DivinityWorkers;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class WorkerPrometheus extends Worker {

    public WorkerPrometheus(Player p, int coordX, int coordY) {
        super(p, coordX, coordY);
    }

    @Override           //se passo cose rompe la superclasse, come faccio?
    public boolean canMove(boolean noGoUp) {
        for(int i = getCoordX() - 1; i < getCoordX() + 1; i++){
            for(int j = getCoordY() - 1; j < getCoordY() + 1; j++) {
                                                                                    //se true non può salire
                if ((i == getCoordX() && j == getCoordY()) || !Map.noWorkerHere(i, j) || (noGoUp && Map.getCellBlockType(i, j).getAbbreviation() > getCoordZ()) || Map.getCellBlockType(i, j) == BlockType.CUPOLA) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void buildBlock(boolean buildFirst, int buildX, int buildY) {
        if(buildFirst){
            super.buildBlock(buildX, buildY);
            GameManager.setAllowHeight(true);       //true quando?
        }else{
            super.buildBlock(buildX, buildY);
        }

    }

}
