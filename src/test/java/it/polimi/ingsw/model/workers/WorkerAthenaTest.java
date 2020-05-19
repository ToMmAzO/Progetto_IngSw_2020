package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerAthenaTest {

    @BeforeEach
    void setUp() {
        new GameManager();
    }

    @Test
    void canMove_BlockedByWorkersTest() {
        WorkerAthena w = new WorkerAthena("RED1", 0, 0);
        new WorkerAtlas("YEL1", 0, 1);
        new WorkerAtlas("YEL2", 1, 0);
        new WorkerPan("BLU1", 1, 1);

        assertFalse(w.canMove());
    }

    @Test
    void canMove_BoundAthenaTest() {
        WorkerAthena worker1 = new WorkerAthena("RED1", 0, 0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        TurnManager.getInstance().setAllowHeight(false);

        assertTrue(worker1.canMove());
    }

    @Test
    void changePosition_NoGoUpTest() {
        WorkerAthena w = new WorkerAthena("RED1", 0, 0);
        w.changePosition(0, 1);

        assertFalse(TurnManager.getInstance().cannotGoUp());
    }

    @Test
    void changePosition_GoUpTest() {
        WorkerAthena w = new WorkerAthena("RED1", 0, 0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK1);
        w.changePosition(0, 1);

        assertTrue(TurnManager.getInstance().cannotGoUp());
    }

}