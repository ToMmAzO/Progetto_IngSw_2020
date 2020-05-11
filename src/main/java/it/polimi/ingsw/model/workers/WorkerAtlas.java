package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;

public class WorkerAtlas extends Worker {

    public WorkerAtlas(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public void buildBlock(boolean buildCupola, int buildX, int buildY) {
        if(buildCupola){
            Map.getInstance().setCellBlockType(buildX, buildY, BlockType.CUPOLA);
        } else{
            super.buildBlock(buildX,buildY);
        }
    }

}
