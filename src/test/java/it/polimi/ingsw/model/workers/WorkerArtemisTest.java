package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerArtemisTest {

    @BeforeEach
    void setUp() {
        new GameManager();
    }

    @Test
    void canMoveAgain_OnlyInOldPositionTest(){
        WorkerArtemis w = new WorkerArtemis("RED1", 0, 0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK2);

        assertFalse(w.canDoAgain(1, 1));
    }


    @Test
    void canMoveAgain_OnlyGoUpWithBoundAthenaTest(){
        WorkerArtemis w = new WorkerArtemis("RED1", 0, 0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);
        TurnManager.getInstance().setAllowHeight(false);

        assertFalse(w.canDoAgain(0, 1));
    }

    @Test
    void canMoveAgain_WithBoundAthenaTest(){
        WorkerArtemis w = new WorkerArtemis("RED1", 0, 0);
        TurnManager.getInstance().setAllowHeight(false);

        assertTrue(w.canDoAgain(0, 1));
    }

    @Test
    void canMoveAgain_DefaultTest(){
        WorkerArtemis w = new WorkerArtemis("RED1", 0, 0);

        assertTrue(w.canDoAgain(0, 1));
    }

}