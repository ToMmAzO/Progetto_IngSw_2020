package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerPrometheusTest {

    @BeforeEach
    void setUp(){
        new Map();
        new TurnManager();
    }

    @Test
    void canMoveTest1(){
        WorkerPrometheus worker1 = new WorkerPrometheus("RED1",0,0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);

        TurnManager.getInstance().setAllowHeightPrometheus(false);
        assertFalse(worker1.canMove());

        TurnManager.getInstance().setAllowHeightPrometheus(true);
        assertTrue(worker1.canMove());
    }

}