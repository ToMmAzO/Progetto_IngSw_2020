package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerHestiaTest {

    @BeforeEach
    void setUp() {
        new GameManager();
    }

    @Test
    void canRebuild_OnlyInPerimeterTest() {
        WorkerHestia w = new WorkerHestia("RED1", 1, 1);
        Map.getInstance().setCellBlockType(1, 2, BlockType.CUPOLA);
        Map.getInstance().setCellBlockType(2, 1, BlockType.CUPOLA);
        Map.getInstance().setCellBlockType(2, 2, BlockType.CUPOLA);

        assertFalse(w.canBuild());
    }

    @Test
    void canRebuild_EverywhereTest() {
        WorkerHestia w = new WorkerHestia("RED1", 1, 1);

        assertTrue(w.canBuild());
    }

}