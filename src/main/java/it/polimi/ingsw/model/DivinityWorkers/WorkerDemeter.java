package it.polimi.ingsw.model.DivinityWorkers;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class WorkerDemeter extends Worker {

    public WorkerDemeter(Player p, int coordX, int coordY) {
        super(p, coordX, coordY);
    }

    @Override
    public void buildBlock(int buildX, int buildY, int buildAgainX, int buildAgainY) {
        if(buildAgainX == buildX && buildAgainY == buildY){         //BRUTTO: meglio nel conroller: chiamata buildBlock -> bollean buildAgain -> chiamata buildBlock con controlli sul =
            //ERRORE
        }else{
            super.buildBlock(buildX, buildY);
            super.buildBlock(buildAgainX, buildAgainY);
        }
    }

}
