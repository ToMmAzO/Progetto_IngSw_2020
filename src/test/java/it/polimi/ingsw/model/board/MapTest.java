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
    void mapTest() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(Map.getInstance().getCellBlockType(i, j), BlockType.GROUND);
                assertTrue(Map.getInstance().noWorkerHere(0, 0));
            }
        }

        Map.getInstance().setCellBlockType(0, 0, BlockType.BLOCK1);
        assertEquals(Map.getInstance().getCellBlockType(0, 0), BlockType.BLOCK1);

        Worker workerApollo = new WorkerApollo("testApollo", 0, 0);
        assertFalse(Map.getInstance().noWorkerHere(0, 0));
        assertEquals(Map.getInstance().getWorkerInCell(0, 0), workerApollo);

        Worker workerArtemis = new WorkerArtemis("testArtemis", 1, 1);
        assertNotEquals(Map.getInstance().getWorkerInCell(0, 0), workerArtemis);

        Map.getInstance().deleteWorkerInCell(workerApollo);
        assertTrue(Map.getInstance().noWorkerHere(0, 0));
    }

}