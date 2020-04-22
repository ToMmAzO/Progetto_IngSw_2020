package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.Workers.Worker;
import it.polimi.ingsw.model.Workers.WorkerApollo;
import it.polimi.ingsw.model.Workers.WorkerArtemis;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    @Test
    void map() {
        new Map();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(Map.getCellBlockType(i, j), BlockType.GROUND);
                assertTrue(Map.noWorkerHere(0, 0));
            }
        }
        Map.setCellBlockType(0, 0, BlockType.BLOCK1);
        assertEquals(Map.getCellBlockType(0, 0), BlockType.BLOCK1);
        Worker workerApollo = new WorkerApollo("testApollo", 0, 0);
        assertFalse(Map.noWorkerHere(0, 0));
        assertEquals(Map.getWorkerInCell(0, 0), workerApollo);
        Worker workerArtemis = new WorkerArtemis("testArtemis", 1, 1);
        assertNotEquals(Map.getWorkerInCell(0, 0), workerArtemis);
        Map.deleteWorkerInCell(workerApollo);
        assertTrue(Map.noWorkerHere(0, 0));
    }

}