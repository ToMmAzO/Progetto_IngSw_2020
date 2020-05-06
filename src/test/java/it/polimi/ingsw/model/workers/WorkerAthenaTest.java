package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerAthenaTest {

    @BeforeEach
    void setUp(){
        new Map();
    }

    @Test
    void canMoveTest1(){     //worker1 is blocked by other workers
        WorkerAthena worker1 = new WorkerAthena("RED1",0,0);
        WorkerAtlas worker2 = new WorkerAtlas("YEL1",0,1);
        WorkerAtlas worker3 = new WorkerAtlas("YEL2",1,0);
        WorkerPan worker4 = new WorkerPan("BLU1",1,1);

        assertFalse(worker1.canMove());
    }

    @Test
    void canMoveTest2(){    //worker1 is blocked by BlockTypes
        WorkerAthena worker1 = new WorkerAthena("RED1",0,0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK2);

        assertFalse(worker1.canMove());

        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        assertTrue(worker1.canMove());
    }

    @Test
    void canMoveTest3(){    //worker1 can move even with allowHeight == false
        WorkerAthena worker1 = new WorkerAthena("RED1",0,0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        TurnManager.setAllowHeight(false);

        assertTrue(worker1.canMove());

    }

    @Test
    void changePositionTest1(){
        WorkerAthena worker1 = new WorkerAthena("RED1",0,0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        WorkerAtlas worker2 = new WorkerAtlas("YEL1",0,4);
        Map.getInstance().setCellBlockType(0, 3, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 3, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 4, BlockType.BLOCK1);

        worker1.changePosition(0,1);

        assertEquals(worker1.getCoordX(),0);
        assertEquals(worker1.getCoordY(),1);
        assertEquals(worker1.getCoordZ(),1);
        assertTrue(TurnManager.cannotGoUp());
        assertFalse(worker2.canMove());

    }

    @Test
    void changePositionTest2(){
        WorkerAthena worker1 = new WorkerAthena("RED1",0,0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);

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