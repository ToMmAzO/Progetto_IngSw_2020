package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;

/**
 * This Worker may build a cupola at any level.
 */
public class WorkerAtlas extends Worker {

    public WorkerAtlas(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public void specialBuild(int buildX, int buildY) {
        Map.getInstance().setCellBlockType(buildX, buildY, BlockType.CUPOLA);
    }

}
