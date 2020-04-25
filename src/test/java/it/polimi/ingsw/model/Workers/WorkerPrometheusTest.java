package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerPrometheusTest {

    @Test
    void canMoveTest1(){
        new Map();
        WorkerPrometheus worker1 = new WorkerPrometheus("RED1",0,0);
        Map.setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.setCellBlockType(1, 0, BlockType.BLOCK1);
        Map.setCellBlockType(1, 1, BlockType.BLOCK1);

        TurnManager.setAllowHeightPrometheus(false);
        assertFalse(worker1.canMove());

        TurnManager.setAllowHeightPrometheus(true);
        assertTrue(worker1.canMove());
    }

}