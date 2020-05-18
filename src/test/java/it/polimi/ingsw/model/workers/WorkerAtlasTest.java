package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerAtlasTest {

    @BeforeEach
    void setUp() {
        new GameManager();
    }

    @Test
    void buildBlock_BuildCupolaTest() {
        WorkerAtlas w = new WorkerAtlas("RED1", 0, 0);

        w.buildBlock(true, 0, 1);
        assertEquals(Map.getInstance().getCellBlockType(0, 1), BlockType.CUPOLA);
    }

    @Test
    void buildBlock_DefaultTest() {
        WorkerAtlas w = new WorkerAtlas("RED1", 0, 0);


        w.buildBlock(false, 1, 0);
        assertEquals(Map.getInstance().getCellBlockType(1, 0), BlockType.BLOCK1);
    }

}