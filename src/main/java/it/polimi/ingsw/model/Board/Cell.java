package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.Worker;

public class Cell {

    private BlockType block;
    private Worker workerPresence;

    public Cell(){
        this.block = BlockType.GROUND;
        this.workerPresence = null;
    }

    public void setBlock(BlockType block) {
        this.block = block;
    }

    public BlockType getBlock() {
        return block;
    }

    public void setWorkerPresence(Worker worker) {
        this.workerPresence = worker;
    }

    public Worker getWorkerPresence() {
        return workerPresence;
    }

}
