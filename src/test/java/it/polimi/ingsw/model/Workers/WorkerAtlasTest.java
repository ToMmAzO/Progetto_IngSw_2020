package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerAtlasTest {

    @Test
    void buildBlockTest1(){
        new Map();
        WorkerAtlas worker1 = new WorkerAtlas("RED1",0,0);
        Map.setCellBlockType(0, 1, BlockType.GROUND);
        Map.setCellBlockType(1, 0, BlockType.BLOCK2);
        Map.setCellBlockType(1, 1, BlockType.BLOCK1);

        worker1.buildBlock(true,0,1);
        assertEquals(Map.getCellBlockType(0, 1), BlockType.CUPOLA);

        /*worker1.buildBlock(false,1,0);
        assertEquals(Map.getCellBlockType(1, 0), BlockType.BLOCK3);

        worker1.buildBlock(false,1,1);
        assertEquals(Map.getCellBlockType(1, 1), BlockType.BLOCK2);

        worker1.buildBlock(false,1,0);
        assertEquals(Map.getCellBlockType(1, 0), BlockType.CUPOLA);*/

    }

}