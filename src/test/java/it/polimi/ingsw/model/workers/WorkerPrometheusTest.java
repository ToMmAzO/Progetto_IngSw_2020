package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerPrometheusTest {

    @BeforeEach
    void setUp() {
        new GameManager();
    }

    @Test
    void canMove_DefaultTes(){
        WorkerPrometheus w = new WorkerPrometheus("RED1", 0, 0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);

        assertTrue(w.canMove());
    }

    @Test
    void canMove_OnlyGoUpWithBondPrometheusTest(){
        WorkerPrometheus w = new WorkerPrometheus("RED1", 0, 0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);

        TurnManager.getInstance().setAllowHeightPrometheus(false);
        assertFalse(w.canMove());
    }

    @Test
    void canMove_WithBondPrometheusTest(){
        WorkerPrometheus w = new WorkerPrometheus("RED1", 0, 0);
        Map.getInstance().setCellBlockType(0, 1, BlockType.BLOCK1);
        Map.getInstance().setCellBlockType(1, 0, BlockType.GROUND);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK1);

        TurnManager.getInstance().setAllowHeightPrometheus(false);
        assertTrue(w.canMove());
    }


}