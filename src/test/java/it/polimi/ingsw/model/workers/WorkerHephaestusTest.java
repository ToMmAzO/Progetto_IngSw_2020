package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerHephaestusTest {

    @BeforeEach
    void setUp(){
        new Map();
    }

    @Test
    void canBuildTest1(){
        WorkerHephaestus worker1 = new WorkerHephaestus("RED1",0,0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        assertTrue(worker1.canBuild(false));
        assertTrue(worker1.canBuild(true));

        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK2);
        assertTrue(worker1.canBuild(false));
        assertFalse(worker1.canBuild(true));

    }

    @Test
    void buildBlockTest1(){
        WorkerHephaestus worker1 = new WorkerHephaestus("RED1",0,0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.GROUND);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        worker1.buildBlock(true,1,0);
        assertNotEquals(Map.getInstance().getCellBlockType(1, 0), BlockType.BLOCK2);

        worker1.buildBlock(true,1,1);
        assertEquals(Map.getInstance().getCellBlockType(1, 1), BlockType.BLOCK3);

        worker1.buildBlock(true,0,1);
        assertEquals(Map.getInstance().getCellBlockType(0, 1), BlockType.BLOCK2);

    }

}