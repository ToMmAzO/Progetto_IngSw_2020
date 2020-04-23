package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerHephaestusTest {

    @Test
    void canBuildTest1(){
        new Map();
        WorkerHephaestus worker1 = new WorkerHephaestus("RED1",0,0);
        Map.setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.setCellBlockType(1, 0, BlockType.BLOCK1);
        Map.setCellBlockType(1, 1, BlockType.BLOCK1);
        assertTrue(worker1.canBuild(false));
        assertTrue(worker1.canBuild(true));

        Map.setCellBlockType(0, 1, BlockType.BLOCK2);
        Map.setCellBlockType(1, 0, BlockType.BLOCK2);
        Map.setCellBlockType(1, 1, BlockType.BLOCK2);
        assertTrue(worker1.canBuild(false));
        assertFalse(worker1.canBuild(true));

    }

    @Test
    void buildBlockTest1(){
        new Map();
        WorkerHephaestus worker1 = new WorkerHephaestus("RED1",0,0);
        Map.setCellBlockType(0, 1, BlockType.GROUND);
        Map.setCellBlockType(1, 0, BlockType.BLOCK1);
        Map.setCellBlockType(1, 1, BlockType.BLOCK1);
        worker1.buildBlock(false,1,0);
        assertEquals(Map.getCellBlockType(1, 0), BlockType.BLOCK2);

        worker1.buildBlock(true,1,1);
        assertEquals(Map.getCellBlockType(1, 1), BlockType.BLOCK3);

        worker1.buildBlock(true,0,1);
        assertEquals(Map.getCellBlockType(0, 1), BlockType.BLOCK2);

    }

}