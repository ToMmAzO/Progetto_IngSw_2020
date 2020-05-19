package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerApolloTest {

    @BeforeEach
    void setUp() {
        new GameManager();
    }

    @Test
    void canMove_BlockedByWorkersTest(){
        WorkerApollo w = new WorkerApollo("RED1", 0, 0);
        new WorkerAtlas("YEL1", 0, 1);
        new WorkerChronus("YEL2", 1, 0);
        new WorkerHera("BLU1", 1, 1);

        assertTrue(w.canMove());
    }

    @Test
    void canMove_BlockedByBlockTest(){
        WorkerApollo w = new WorkerApollo("RED1", 0, 0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK2);

        assertFalse(w.canMove());

        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        assertTrue(w.canMove());
    }

    @Test
    void canMove_OnlyGoUpWithBoundAthenaWithWorkersTest(){
        WorkerApollo w = new WorkerApollo("RED1", 0, 0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        new WorkerTriton("YEL1", 0, 1);
        new WorkerAtlas("YEL2", 1, 0);
        new WorkerPan("BLU1", 1, 1);
        TurnManager.getInstance().setAllowHeight(false);

        assertFalse(w.canMove());
    }

    @Test
    void canMove_WithBoundAthenaWithWorkersTest(){
        WorkerApollo w = new WorkerApollo("RED1", 0, 0);
        new WorkerAtlas("YEL1", 0, 1);
        new WorkerAtlas("YEL2", 1, 0);
        new WorkerPan("BLU1", 1, 1);
        TurnManager.getInstance().setAllowHeight(false);

        assertTrue(w.canMove());
    }

    @Test
    void canMove_OnlyGoUpWithBoundAthenaTest(){
        WorkerApollo w = new WorkerApollo("RED1", 0, 0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        TurnManager.getInstance().setAllowHeight(false);

        assertFalse(w.canMove());
    }

    @Test
    void canMove_DefaultTest(){
        WorkerApollo w = new WorkerApollo("RED1", 0, 0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.GROUND);
        Map.getInstance().setCellBlockType(1, 0, BlockType.GROUND);
        Map.getInstance().setCellBlockType(1, 1, BlockType.GROUND);
        TurnManager.getInstance().setAllowHeight(false);

        assertTrue(w.canMove());
    }

    @Test
    void changePosition_ReplaceTest(){
        WorkerApollo w1 = new WorkerApollo("RED1", 0, 0);
        WorkerAtlas w2 = new WorkerAtlas("YEL2", 1, 0);

        w1.changePosition(1, 0);
        assertEquals(w1.getCoordX(), 1);
        assertEquals(w1.getCoordY(), 0);
        assertEquals(w2.getCoordX(), 0);
        assertEquals(w2.getCoordY(), 0);
    }

    @Test
    void changePosition_DefaultTest(){
        WorkerApollo w = new WorkerApollo("RED1",0,0);

        w.changePosition(1, 0);
        assertEquals(w, Map.getInstance().getWorkerInCell(1, 0));
    }



}