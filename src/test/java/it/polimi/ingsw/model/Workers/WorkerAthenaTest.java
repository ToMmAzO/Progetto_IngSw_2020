package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerAthenaTest {

    @Test
    void canMoveTest1(){     //worker1 is blocked by other workers
        new Map();
        WorkerAthena worker1 = new WorkerAthena("RED1",0,0);
        WorkerAtlas worker2 = new WorkerAtlas("YEL1",0,1);
        WorkerAtlas worker3 = new WorkerAtlas("YEL2",1,0);
        WorkerPan worker4 = new WorkerPan("BLU1",1,1);

        assertFalse(worker1.canMove());
    }

    @Test
    void canMoveTest2(){    //worker1 is blocked by BlockTypes
        new Map();
        WorkerAthena worker1 = new WorkerAthena("RED1",0,0);
        Map.setCellBlockType(0, 1, BlockType.BLOCK2);
        Map.setCellBlockType(1, 0, BlockType.BLOCK2);
        Map.setCellBlockType(1, 1, BlockType.BLOCK2);

        assertFalse(worker1.canMove());

        Map.setCellBlockType(1, 1, BlockType.BLOCK1);
        assertTrue(worker1.canMove());
    }

    @Test
    void canMoveTest3(){    //worker1 can move even with allowHeight == false
        new Map();
        WorkerAthena worker1 = new WorkerAthena("RED1",0,0);
        Map.setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.setCellBlockType(1, 0, BlockType.BLOCK1);
        Map.setCellBlockType(1, 1, BlockType.BLOCK1);
        TurnManager.setAllowHeight(false);

        assertTrue(worker1.canMove());

    }

    @Test
    void changePositionTest1(){
        new Map();
        WorkerAthena worker1 = new WorkerAthena("RED1",0,0);
        Map.setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.setCellBlockType(1, 1, BlockType.BLOCK1);
        WorkerAtlas worker2 = new WorkerAtlas("YEL1",0,4);
        Map.setCellBlockType(0, 3, BlockType.BLOCK1);
        Map.setCellBlockType(1, 3, BlockType.BLOCK1);
        Map.setCellBlockType(1, 4, BlockType.BLOCK1);

        worker1.changePosition(0,1);

        assertEquals(worker1.getCoordX(),0);
        assertEquals(worker1.getCoordY(),1);
        assertEquals(worker1.getCoordZ(),1);
        assertEquals(TurnManager.cannotGoUp(),true);
        assertFalse(worker2.canMove());

    }

    @Test
    void changePositionTest2(){
        new Map();
        WorkerAthena worker1 = new WorkerAthena("RED1",0,0);
        Map.setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.setCellBlockType(1, 0, BlockType.BLOCK2);
        Map.setCellBlockType(1, 1, BlockType.BLOCK1);

        worker1.changePosition(1,0);
        assertEquals(worker1.getCoordX(),1);
        assertEquals(worker1.getCoordY(),0);
        assertEquals(worker1.getCoordZ(),2);

        worker1.changePosition(1,1);
        assertEquals(worker1.getCoordX(),1);
        assertEquals(worker1.getCoordY(),1);
        assertEquals(worker1.getCoordZ(),1);


    }

}