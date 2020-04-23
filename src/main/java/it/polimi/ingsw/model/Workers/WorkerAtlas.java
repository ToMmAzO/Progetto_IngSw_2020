package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;

public class WorkerAtlas extends Worker {

    public WorkerAtlas(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public void buildBlock(boolean buildCupola, int buildX, int buildY) {
        if(buildCupola){
            Map.setCellBlockType(buildX, buildY, BlockType.CUPOLA);
        }
    }

}
