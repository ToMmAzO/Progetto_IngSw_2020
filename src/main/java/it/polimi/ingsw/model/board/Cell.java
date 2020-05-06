package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.workers.Worker;

import java.io.Serializable;

public class Cell implements Serializable {

    private BlockType block;
    private Worker workerPresence;

    public Cell(){
        this.block = BlockType.GROUND;
        this.workerPresence = null;
    }

    public void setBlockType(BlockType block) {
        this.block = block;
    }

    public BlockType getBlockType() {
        return block;
    }

    public void setWorkerPresence(Worker worker) {
        this.workerPresence = worker;
    }

    public Worker getWorkerPresence() {
        return workerPresence;
    }

}
