package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerHephaestusTest {

    @BeforeEach
    void setUp() {
        new GameManager();
    }

    @Test
    void canBuildTwice_Test(){
        WorkerHephaestus w = new WorkerHephaestus("RED1",0,0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);

        assertTrue(w.canBuild(true));//togliere true
    }

    @Test
    void canBuildTwice_OnlyInBlock2Test(){
        WorkerHephaestus w = new WorkerHephaestus("RED1",0,0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK2);

        assertFalse(w.canBuild(true));//togliere true
    }

    @Test
    void buildBlock_SingleConstructionTest() {
        WorkerHephaestus w = new WorkerHephaestus("RED1", 0, 0);
        w.buildBlock(false, 1, 1);

        assertEquals(BlockType.BLOCK1, Map.getInstance().getCellBlockType(1, 1));
    }


    @Test
    void buildBlock_DoubleConstructionTest(){
        WorkerHephaestus w = new WorkerHephaestus("RED1",0,0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.GROUND);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK1);

        w.buildBlock(true, 0, 1);
        assertEquals(BlockType.BLOCK2, Map.getInstance().getCellBlockType(0, 1));

        w.buildBlock(true, 1, 0);
        assertEquals(BlockType.BLOCK3, Map.getInstance().getCellBlockType(1, 0));
    }

}