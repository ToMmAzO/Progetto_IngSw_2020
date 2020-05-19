package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerDemeterTest {

    @BeforeEach
    void setUp() {
        new GameManager();
    }

    @Test
    void canBuild_OnlyInOldCoordinatesTest() {
        WorkerDemeter w = new WorkerDemeter("RED1", 1, 1);
        Map.getInstance().setCellBlockType(0, 0, BlockType.CUPOLA);
        Map.getInstance().setCellBlockType(0, 1, BlockType.CUPOLA);
        Map.getInstance().setCellBlockType(0, 2, BlockType.CUPOLA);
        Map.getInstance().setCellBlockType(1, 0, BlockType.CUPOLA);
        Map.getInstance().setCellBlockType(1, 2, BlockType.CUPOLA);
        Map.getInstance().setCellBlockType(2, 0, BlockType.CUPOLA);
        Map.getInstance().setCellBlockType(2, 2, BlockType.CUPOLA);

        assertFalse(w.canBuild(2, 1));
    }

    @Test
    void canBuild_RegularitySecondConstructionTest() {
        WorkerDemeter w = new WorkerDemeter("RED1", 1, 1);

        assertTrue(w.canBuild(2, 1));
    }

}