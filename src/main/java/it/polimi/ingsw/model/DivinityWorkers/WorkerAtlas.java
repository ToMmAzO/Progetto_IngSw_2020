package it.polimi.ingsw.model.DivinityWorkers;

import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Worker;

public class WorkerAtlas extends Worker {

    public WorkerAtlas(Color c, int coordX, int coordY) {
        super(c, coordX, coordY);
    }

    @Override
    public void buildBlock(boolean buildCupola, int buildX, int buildY) {
        if(buildCupola){
            Map.setCellBlockType(buildX, buildY, BlockType.CUPOLA);
        }else {
            super.buildBlock(buildX, buildY);
        }
    }

}
