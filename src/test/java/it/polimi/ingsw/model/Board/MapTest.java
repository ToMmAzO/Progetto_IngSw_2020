package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.Workers.Worker;
import it.polimi.ingsw.model.Workers.WorkerApollo;
import it.polimi.ingsw.model.Workers.WorkerArtemis;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MapTest {

    @Test
    public void Map(){
        new Map();
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                assertEquals(Map.getCellBlockType(i, j),  BlockType.GROUND);
                assertEquals(Map.noWorkerHere(0, 0), true);
            }
        }
        Map.setCellBlockType(0, 0, BlockType.BLOCK1);
        assertEquals(Map.getCellBlockType(0, 0), BlockType.BLOCK1);
        Worker workerApollo = new WorkerApollo("testApollo", 0, 0);
        assertEquals(Map.noWorkerHere(0, 0), false);
        assertEquals(Map.getWorkerInCell(0, 0), workerApollo);
        Worker workerArtemis = new WorkerArtemis("testArtemis", 1, 1);
        assertNotEquals(Map.getWorkerInCell(0, 0), workerArtemis);
        Map.deleteWorkerInCell(workerApollo);
        assertEquals(Map.noWorkerHere(0, 0), true);
    }

}