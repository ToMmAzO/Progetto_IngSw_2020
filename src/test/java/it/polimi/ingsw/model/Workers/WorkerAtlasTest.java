package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerAtlasTest {

    @BeforeEach
    void setUp(){
        new Map();
    }

    @Test
    void buildBlockTest1(){
        WorkerAtlas worker1 = new WorkerAtlas("RED1",0,0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.GROUND);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);

        worker1.buildBlock(true,0,1);
        assertEquals(Map.getInstance().getCellBlockType(0, 1), BlockType.CUPOLA);

        worker1.buildBlock(false,1,0);
        assertEquals(Map.getInstance().getCellBlockType(1, 0), BlockType.BLOCK3);

        worker1.buildBlock(false,1,1);
        assertEquals(Map.getInstance().getCellBlockType(1, 1), BlockType.BLOCK2);

        worker1.buildBlock(false,1,0);
        assertEquals(Map.getInstance().getCellBlockType(1, 0), BlockType.CUPOLA);

    }

}