package it.polimi.ingsw.model;

public class Cell {

    private Worker workerToken;
    private BlockType block;

    public void setWorkerToken(Worker workerToken) {
        this.workerToken = workerToken;
    }

    public Worker getWorkerToken() {
        return workerToken;
    }

    public void setBlock(BlockType block) {
        this.block = block;
    }

    public BlockType getBlock() {
        return block;
    }

}
