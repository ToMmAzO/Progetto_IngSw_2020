package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerZeusTest {

    @BeforeEach
    void setUp() {
        new GameManager();
    }

    @Test
    void buildBlock_defaultTest(){
        WorkerZeus w = new WorkerZeus("RED1", 0, 0);
        w.buildBlock(0, 1);

        assertEquals(BlockType.BLOCK1, Map.getInstance().getCellBlockType(0, 1));
    }

    @Test
    void buildBlock_InActualPositionTest(){
        WorkerZeus w = new WorkerZeus("RED1", 0, 0);
        w.buildBlock(0, 0);

        assertEquals(BlockType.BLOCK1, Map.getInstance().getCellBlockType(0, 0));
    }


}