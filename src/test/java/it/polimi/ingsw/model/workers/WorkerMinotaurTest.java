package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerMinotaurTest {

    @BeforeEach
    void setUp() {
        new GameManager();
    }

    @Test
    void canPush_WorkerPushedNoGoUpTest(){
        WorkerMinotaur w1 = new WorkerMinotaur("RED1",0,0);
        new WorkerAtlas("YEL1",1,1);

        assertTrue(w1.canPush(1,1));
    }

    @Test
    void canPush_WorkerPushedSingleGoUpTest(){
        WorkerMinotaur w1 = new WorkerMinotaur("RED1",0,0);
        new WorkerAtlas("YEL1",0,1);
        Map.getInstance().setCellBlockType(0, 2, BlockType.BLOCK1);

        assertTrue(w1.canPush(0,1));
    }

    @Test
    void canPush_WorkerPushedDoubleGoUpTest(){
        WorkerMinotaur w1 = new WorkerMinotaur("RED1",0,0);
        new WorkerAtlas("YEL1",0,1);
        Map.getInstance().setCellBlockType(0, 2, BlockType.BLOCK3);

        assertTrue(w1.canPush(0,1));
    }

    @Test
    void canPush_WorkerPushedInCupolaTest(){
        WorkerMinotaur w1 = new WorkerMinotaur("RED1",0,0);
        new WorkerAtlas("YEL1",0,1);
        Map.getInstance().setCellBlockType(0, 2, BlockType.CUPOLA);

        assertFalse(w1.canPush(0,1));
    }


    @Test
    void canMove_OnlyGoUpWithBoundAthenaTest(){
        WorkerMinotaur w1 = new WorkerMinotaur("RED1",0,0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        TurnManager.getInstance().setAllowHeightAthena(false);

        assertFalse(w1.canMove());
    }

    @Test
    void canMove_WithBoundAthenaWithNoWorkerInCellTest(){
        WorkerMinotaur w1 = new WorkerMinotaur("RED1",0,0);
        TurnManager.getInstance().setAllowHeightAthena(false);

        assertTrue(w1.canMove());
    }

    @Test
    void canMove_WithBoundAthenaOnlyWithYourWorkerInCellTest(){
        WorkerMinotaur w1 = new WorkerMinotaur("RED1",0,0);
        new WorkerMinotaur("RED2",1,1);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK2);
        TurnManager.getInstance().setAllowHeightAthena(false);

        assertFalse(w1.canMove());
    }

    @Test
    void canMove_WithBoundAthenaOnlyWithOtherWorkerInCellTest(){
        WorkerMinotaur w1 = new WorkerMinotaur("RED1",0,0);
        new WorkerAtlas("YEL2",1,1);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK2);
        TurnManager.getInstance().setAllowHeightAthena(false);

        assertTrue(w1.canMove());
    }

    @Test
    void canMove_WithNoWorkerInCellTest(){
        WorkerMinotaur w1 = new WorkerMinotaur("RED1",0,0);

        assertTrue(w1.canMove());
    }

    @Test
    void canMove_OnlyWithYourWorkerInCellTest(){
        WorkerMinotaur w1 = new WorkerMinotaur("RED1",0,0);
        new WorkerMinotaur("RED2",1,1);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK2);

        assertFalse(w1.canMove());
    }

    @Test
    void canMove_OnlyWithOtherWorkerInCellTest(){
        WorkerMinotaur w1 = new WorkerMinotaur("RED1",0,0);
        new WorkerAtlas("YEL2",1,1);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK2);

        assertTrue(w1.canMove());
    }

    @Test
    void changePosition_DefaultTest(){
        WorkerMinotaur w = new WorkerMinotaur("RED1",0,0);

        w.changePosition(1, 0);
        assertEquals(w, Map.getInstance().getWorkerInCell(1, 0));
    }

    @Test
    void changePosition_PushedOtherWorkerTest(){
        WorkerMinotaur w1 = new WorkerMinotaur("RED1",0,0);
        WorkerAtlas w2 = new WorkerAtlas("RED2",1,1);
        w1.changePosition(1, 1);

        assertEquals(w1, Map.getInstance().getWorkerInCell(1, 1));
        assertEquals(w2, Map.getInstance().getWorkerInCell(2, 2));
    }

}