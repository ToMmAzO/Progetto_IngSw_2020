package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.workers.WorkerDemeter;
import it.polimi.ingsw.network.message.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnManagerTest {


    @BeforeEach
    void setUp(){
        new GameManager();
    }

    @Test
    void workerChoice_InvalidChoiceTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.APOLLO);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);

        TurnManager.getInstance().workerChoice(4);
        assertNull(TurnManager.getInstance().getWorkerSelected());
    }

    @Test
    void workerChoice_WorkerCanMoveTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.APOLLO);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);

        assertNull(TurnManager.getInstance().getWorkerSelected());
        TurnManager.getInstance().workerChoice(1);
        assertEquals(p.getWorkerSelected(1), TurnManager.getInstance().getWorkerSelected());
        assertEquals(GameState.MOVEMENT, Game.getInstance().getGameState(p));
    }

    @Test
    void workerChoice_PrometheusCanMoveAndPrebuildTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.PROMETHEUS);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);

        assertNull(TurnManager.getInstance().getWorkerSelected());
        TurnManager.getInstance().workerChoice(1);
        assertEquals(p.getWorkerSelected(1), TurnManager.getInstance().getWorkerSelected());
        assertEquals(GameState.QUESTION_PROMETHEUS, Game.getInstance().getGameState(p));
    }

    @Test
    void workerChoice_WorkerCantMoveTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.ARTEMIS);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        new WorkerDemeter("YEL1", 1, 0);
        new WorkerDemeter("YEL2", 1, 1);
        GameManager.getInstance().setCurrPlayer(p);

        assertNull(TurnManager.getInstance().getWorkerSelected());
        TurnManager.getInstance().workerChoice(1);
        assertNull(TurnManager.getInstance().getWorkerSelected());
    }

    @Test
    void workerChoice_WorkersCantMoveTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.ARTEMIS);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        new WorkerDemeter("YEL1", 1, 0);
        new WorkerDemeter("YEL2", 1, 1);
        new WorkerDemeter("BLU1", 0, 2);
        new WorkerDemeter("BLU2", 1, 2);
        GameManager.getInstance().setCurrPlayer(p);

        assertNull(TurnManager.getInstance().getWorkerSelected());
        try{
            TurnManager.getInstance().workerChoice(1);
        } catch (NullPointerException ignore){}
        assertNull(TurnManager.getInstance().getWorkerSelected());
    }

    @Test
    void preBuildPrometheus_CanMoveTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.PROMETHEUS);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);

        TurnManager.getInstance().prebuildPrometheus(1, 0);
        assertEquals(BlockType.BLOCK1, Map.getInstance().getCellBlockType(1, 0));
        assertTrue(TurnManager.getInstance().cannotGoUpPrometheus());
        assertEquals(GameState.MOVEMENT, Game.getInstance().getGameState(p));
    }

    @Test
    void preBuildPrometheus_CantMoveTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.PROMETHEUS);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        new WorkerDemeter("YEL1", 1, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);

        Map.getInstance().setCellBlockType(1, 0, BlockType.BLOCK1);
        try {
            TurnManager.getInstance().prebuildPrometheus(1, 0);
        } catch(IndexOutOfBoundsException ignore){}
        assertEquals(BlockType.BLOCK2, Map.getInstance().getCellBlockType(1, 0));
        assertFalse(TurnManager.getInstance().cannotGoUpPrometheus());
        assertEquals(GameState.WAIT_TURN, Game.getInstance().getGameState(p));
    }

    @Test
    void movement_Test() {
        Player p = new Player("Player");
        p.setGodChoice(God.ZEUS);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);

        TurnManager.getInstance().movement(1, 0);
        assertEquals(p.getWorkerSelected(1), Map.getInstance().getWorkerInCell(1, 0));
        assertEquals(GameState.CONSTRUCTION, Game.getInstance().getGameState(p));
    }

    @Test
    void movement_ArtemisCantMoveAgainTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.ARTEMIS);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        new WorkerDemeter("YEL1", 2, 0);
        new WorkerDemeter("YEL2", 2, 1);
        new WorkerDemeter("BLU1", 1, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);

        TurnManager.getInstance().movement(1, 0);
        assertEquals(p.getWorkerSelected(1), Map.getInstance().getWorkerInCell(1, 0));
        assertEquals(GameState.CONSTRUCTION, Game.getInstance().getGameState(p));
    }

    @Test
    void movement_ArtemisCanMoveAgainTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.ARTEMIS);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);

        TurnManager.getInstance().movement(1, 0);
        assertEquals(p.getWorkerSelected(1), Map.getInstance().getWorkerInCell(1, 0));
        assertEquals(GameState.QUESTION_ARTEMIS, Game.getInstance().getGameState(p));
    }

    @Test
    void movement_AtlasCanBuildTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.ATLAS);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);

        TurnManager.getInstance().movement(1, 0);
        assertEquals(p.getWorkerSelected(1), Map.getInstance().getWorkerInCell(1, 0));
        assertEquals(GameState.QUESTION_ATLAS, Game.getInstance().getGameState(p));
    }

    @Test
    void movement_HephaestusCanBuildX2Test() {
        Player p = new Player("Player");
        p.setGodChoice(God.HEPHAESTUS);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);

        TurnManager.getInstance().movement(1, 0);
        assertEquals(p.getWorkerSelected(1), Map.getInstance().getWorkerInCell(1, 0));
        assertEquals(GameState.QUESTION_HEPHAESTUS, Game.getInstance().getGameState(p));
    }

    @Test
    void movement_HephaestusCantBuildX2Test() {
        Player p = new Player("Player");
        p.setGodChoice(God.HEPHAESTUS);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);
        Map.getInstance().setCellBlockType(0, 0, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(2, 0, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(2, 1, BlockType.BLOCK2);

        TurnManager.getInstance().movement(1, 0);
        assertEquals(p.getWorkerSelected(1), Map.getInstance().getWorkerInCell(1, 0));
        assertEquals(GameState.CONSTRUCTION, Game.getInstance().getGameState(p));
    }

    @Test
    void movement_TritonCanMoveAgainTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.TRITON);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);

        TurnManager.getInstance().movement(1, 0);
        assertEquals(p.getWorkerSelected(1), Map.getInstance().getWorkerInCell(1, 0));
        assertEquals(GameState.QUESTION_TRITON, Game.getInstance().getGameState(p));
    }

    @Test
    void movement_TritonNotInPerimeterTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.TRITON);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);

        TurnManager.getInstance().movement(1, 1);
        assertEquals(p.getWorkerSelected(1), Map.getInstance().getWorkerInCell(1, 1));
        assertEquals(GameState.CONSTRUCTION, Game.getInstance().getGameState(p));
    }

    @Test
    void secondMove_Test() {
        Player p = new Player("Player");
        p.setGodChoice(God.ARTEMIS);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);
        TurnManager.getInstance().movement(1, 0);

        TurnManager.getInstance().secondMove(1, 1);
        assertEquals(p.getWorkerSelected(1), Map.getInstance().getWorkerInCell(1, 1));
        assertEquals(GameState.CONSTRUCTION, Game.getInstance().getGameState(p));
    }

    @Test
    void secondMove_ComeBackTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.ARTEMIS);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);
        TurnManager.getInstance().movement(1, 0);

        TurnManager.getInstance().secondMove(0, 0);
        assertTrue(Map.getInstance().noWorkerHere(0, 0));
        assertEquals(p.getWorkerSelected(1), Map.getInstance().getWorkerInCell(1, 0));
    }

    @Test
    void construction_Test() {
        Player p = new Player("Player");
        p.setGodChoice(God.ZEUS);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);

        assertEquals(BlockType.GROUND, Map.getInstance().getCellBlockType(1, 0));
        try {
            TurnManager.getInstance().construction(1, 0);
        } catch(IndexOutOfBoundsException ignore){}
        assertEquals(BlockType.BLOCK1, Map.getInstance().getCellBlockType(1, 0));
        assertEquals(GameState.WAIT_TURN, Game.getInstance().getGameState(p));
    }


    //CHRONUS win


    @Test
    void construction_ChronusNotWinTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.CHRONUS);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);

        assertEquals(BlockType.GROUND, Map.getInstance().getCellBlockType(1, 0));
        try {
            TurnManager.getInstance().construction(1, 0);
        } catch(IndexOutOfBoundsException ignore){}
        assertEquals(BlockType.BLOCK1, Map.getInstance().getCellBlockType(1, 0));
        assertEquals(GameState.WAIT_TURN, Game.getInstance().getGameState(p));
    }

    @Test
    void construction_DemeterCanRebuildTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.DEMETER);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);

        assertEquals(BlockType.GROUND, Map.getInstance().getCellBlockType(1, 0));
        TurnManager.getInstance().construction(1, 0);
        assertEquals(BlockType.BLOCK1, Map.getInstance().getCellBlockType(1, 0));
        assertEquals(GameState.QUESTION_DEMETER, Game.getInstance().getGameState(p));
    }

    @Test
    void construction_DemeterCantRebuildTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.DEMETER);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.CUPOLA);

        assertEquals(BlockType.GROUND, Map.getInstance().getCellBlockType(1, 0));
        try {
            TurnManager.getInstance().construction(1, 0);
        } catch(IndexOutOfBoundsException ignore){}
        assertEquals(BlockType.BLOCK1, Map.getInstance().getCellBlockType(1, 0));
        assertEquals(GameState.WAIT_TURN, Game.getInstance().getGameState(p));
    }

    @Test
    void construction_HestiaCanRebuildTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.HESTIA);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);

        assertEquals(BlockType.GROUND, Map.getInstance().getCellBlockType(1, 0));
        TurnManager.getInstance().construction(1, 0);
        assertEquals(BlockType.BLOCK1, Map.getInstance().getCellBlockType(1, 0));
        assertEquals(GameState.QUESTION_HESTIA, Game.getInstance().getGameState(p));
    }

    @Test
    void construction_HestiaCantRebuildTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.HESTIA);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);
        Map.getInstance().setCellBlockType(1, 1, BlockType.BLOCK3);

        try {
            TurnManager.getInstance().construction(1, 1);
        } catch(IndexOutOfBoundsException ignore){}
        assertEquals(BlockType.CUPOLA, Map.getInstance().getCellBlockType(1, 1));
        assertEquals(GameState.WAIT_TURN, Game.getInstance().getGameState(p));
    }

    @Test
    void secondConstructionDemeter_Test() {
        Player p = new Player("Player");
        p.setGodChoice(God.DEMETER);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);
        TurnManager.getInstance().construction(1, 0);

        assertEquals(BlockType.GROUND, Map.getInstance().getCellBlockType(1, 1));
        try {
            TurnManager.getInstance().secondConstructionDemeter(1, 1);
        } catch(IndexOutOfBoundsException ignore){}
        assertEquals(BlockType.BLOCK1, Map.getInstance().getCellBlockType(1, 1));
        assertEquals(GameState.WAIT_TURN, Game.getInstance().getGameState(p));
    }


    @Test
    void secondConstructionDemeter_InSamePositionTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.DEMETER);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);
        TurnManager.getInstance().construction(1, 0);

        assertEquals(BlockType.BLOCK1, Map.getInstance().getCellBlockType(1, 0));
        TurnManager.getInstance().secondConstructionDemeter(1, 0);
        assertEquals(BlockType.BLOCK1, Map.getInstance().getCellBlockType(1, 0));
    }

    @Test
    void secondConstructionHestia_Test() {
        Player p = new Player("Player");
        p.setGodChoice(God.HESTIA);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);
        TurnManager.getInstance().construction(1, 1);

        assertEquals(BlockType.BLOCK1, Map.getInstance().getCellBlockType(1, 1));
        try {
            TurnManager.getInstance().secondConstructionHestia(1, 1);
        } catch(IndexOutOfBoundsException ignore){}
        assertEquals(BlockType.BLOCK2, Map.getInstance().getCellBlockType(1, 1));
        assertEquals(GameState.WAIT_TURN, Game.getInstance().getGameState(p));
    }

    @Test
    void secondConstructionHestia_InPerimeterTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.HESTIA);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);
        TurnManager.getInstance().construction(1, 1);

        assertEquals(BlockType.GROUND, Map.getInstance().getCellBlockType(0, 0));
        TurnManager.getInstance().secondConstructionHestia(0, 0);
        assertEquals(BlockType.GROUND, Map.getInstance().getCellBlockType(0, 0));
    }

    @Test
    void constructionCupola_Test() {
        Player p = new Player("Player");
        p.setGodChoice(God.ATLAS);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);

        assertEquals(BlockType.GROUND, Map.getInstance().getCellBlockType(1, 0));
        try{
            TurnManager.getInstance().constructionCupola(1, 0);
        } catch(IndexOutOfBoundsException ignore){}
        assertEquals(BlockType.CUPOLA, Map.getInstance().getCellBlockType(1, 0));
        assertEquals(GameState.WAIT_TURN, Game.getInstance().getGameState(p));
    }

    @Test
    void doubleConstruction_Test() {
        Player p = new Player("Player");
        p.setGodChoice(God.HEPHAESTUS);
        p.setWorker1(0, 0);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);

        assertEquals(BlockType.GROUND, Map.getInstance().getCellBlockType(1, 0));
        try {
            TurnManager.getInstance().doubleConstruction(1, 0);
        } catch(IndexOutOfBoundsException ignore){}
        assertEquals(BlockType.BLOCK2, Map.getInstance().getCellBlockType(1, 0));
        assertEquals(GameState.WAIT_TURN, Game.getInstance().getGameState(p));
    }

}