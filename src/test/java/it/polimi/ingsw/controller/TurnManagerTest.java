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
    void movement_TritonCantMoveAgainTest() {
        Player p = new Player("Player");
        p.setGodChoice(God.TRITON);
        p.setWorker1(2, 1);
        p.setWorker2(0, 1);
        GameManager.getInstance().setCurrPlayer(p);
        TurnManager.getInstance().workerChoice(1);
        Map.getInstance().setCellBlockType(0, 0, BlockType.BLOCK2);
        Map.getInstance().setCellBlockType(2, 0, BlockType.BLOCK2);

        TurnManager.getInstance().movement(1, 0);
        assertEquals(p.getWorkerSelected(1), Map.getInstance().getWorkerInCell(1, 0));
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



    }