package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerApolloTest {

    @Test
    void canMoveTest1(){     //worker1 is blocked by other workers
        new Map();
        WorkerApollo worker1 = new WorkerApollo("RED1",0,0);
        WorkerAtlas worker2 = new WorkerAtlas("YEL1",0,1);
        WorkerAtlas worker3 = new WorkerAtlas("YEL2",1,0);
        WorkerPan worker4 = new WorkerPan("BLU1",1,1);

        assertTrue(worker1.canMove());
    }

    @Test
    void canMoveTest2(){    //worker1 is blocked by BlockTypes
        new Map();
        WorkerApollo worker1 = new WorkerApollo("RED1",0,0);
        Map.setCellBlockType(0, 1, BlockType.BLOCK2);
        Map.setCellBlockType(1, 0, BlockType.BLOCK2);
        Map.setCellBlockType(1, 1, BlockType.BLOCK2);

        assertFalse(worker1.canMove());

        Map.setCellBlockType(1, 1, BlockType.BLOCK1);
        assertTrue(worker1.canMove());
    }

    @Test
    void canMoveTest3(){    //worker1 is blocked by allowHeight
        new Map();
        WorkerApollo worker1 = new WorkerApollo("RED1",0,0);
        Map.setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.setCellBlockType(1, 0, BlockType.BLOCK1);
        Map.setCellBlockType(1, 1, BlockType.BLOCK1);
        TurnManager.setAllowHeight(false);

        assertFalse(worker1.canMove());

    }

    @Test
    void changePositionTest1(){     //worker1 is blocked by other workers
        new Map();
        WorkerApollo worker1 = new WorkerApollo("RED1",0,0);
        WorkerAtlas worker2 = new WorkerAtlas("YEL1",0,1);
        WorkerAtlas worker3 = new WorkerAtlas("YEL2",1,0);
        WorkerPan worker4 = new WorkerPan("BLU1",1,1);

        worker1.changePosition(1,0);

        assertEquals(worker1.getCoordX(),1);
        assertEquals(worker1.getCoordY(),0);
        assertEquals(worker3.getCoordX(),0);
        assertEquals(worker3.getCoordY(),0);

    }

    @Test
    void changePositionTest2(){
        new Map();
        WorkerApollo worker1 = new WorkerApollo("RED1",0,0);
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