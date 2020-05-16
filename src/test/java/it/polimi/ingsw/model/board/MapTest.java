package it.polimi.ingsw.model.board;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.workers.Worker;
import it.polimi.ingsw.model.workers.WorkerApollo;
import it.polimi.ingsw.model.workers.WorkerArtemis;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    @BeforeEach
    void setUp() {
        new GameManager();
    }

    @Test
    void map_InitializationTest() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(Map.getInstance().getCellBlockType(i, j), BlockType.GROUND);
                assertEquals(0, Map.getInstance().getCellBlockType(i, j).getAbbreviation());
                assertTrue(Map.getInstance().noWorkerHere(i, j));
            }
        }
    }

    @Test
    void map_SetBlockTest() {
        Map.getInstance().setCellBlockType(0, 0, BlockType.BLOCK1);
        assertEquals(Map.getInstance().getCellBlockType(0, 0), BlockType.BLOCK1);
        assertEquals(1, Map.getInstance().getCellBlockType(0, 0).getAbbreviation());

        assertEquals(0, Map.getInstance().getNumberOfCompleteTurrets());
        Map.getInstance().setCellBlockType(2, 2, BlockType.BLOCK3);
        assertEquals(Map.getInstance().getCellBlockType(2, 2), BlockType.BLOCK3);
        assertEquals(3, Map.getInstance().getCellBlockType(2, 2).getAbbreviation());

        Worker workerArtemis = new WorkerArtemis("testArtemis", 1, 1);
        workerArtemis.buildBlock(2, 2);
        assertEquals(Map.getInstance().getCellBlockType(2, 2), BlockType.CUPOLA);
        assertEquals(4, Map.getInstance().getCellBlockType(2, 2).getAbbreviation());
        assertEquals(1, Map.getInstance().getNumberOfCompleteTurrets());
    }

    @Test
    void map_SetAndDeleteWorkerTest() {
        Worker workerApollo = new WorkerApollo("testApollo", 0, 0);
        assertFalse(Map.getInstance().noWorkerHere(0, 0));
        assertEquals(Map.getInstance().getWorkerInCell(0, 0), workerApollo);

        Worker workerArtemis = new WorkerArtemis("testArtemis", 1, 1);
        assertNotEquals(Map.getInstance().getWorkerInCell(0, 0), workerArtemis);

        Map.getInstance().deleteWorkerInCell(workerApollo);
        assertTrue(Map.getInstance().noWorkerHere(0, 0));
    }

    @Test
    void map_PrintTest() {
        Map.getInstance().setCellBlockType(0, 0, BlockType.BLOCK1);
        Worker workerArtemis = new WorkerArtemis("RED1", 1, 1);
        Map.getInstance().print();
    }


}