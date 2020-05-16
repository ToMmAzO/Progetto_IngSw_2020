package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.workers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @BeforeEach
    void setUp() {
        new GameManager();
    }

    @Test
    void color_AssignmentTest() {
        Player p1 = new Player("player1");
        Player p2 = new Player("player2");
        Player p3 = new Player("player3");

        assertNotEquals(p1.getColor(), p2.getColor());
        assertNotEquals(p1.getColor(), p3.getColor());
        assertNotEquals(p2.getColor(), p3.getColor());
    }

    @Test
    void player_SetWorkerTest() {
        Player p1 = new Player("player1");
        p1.setGodChoice(God.APOLLO);
        assertTrue(p1.setWorker1(0, 0));
        assertTrue(p1.setWorker2(2, 2));
        assertEquals(Map.getInstance().getWorkerInCell(0, 0), p1.getWorkerSelected(1));
        assertEquals(Map.getInstance().getWorkerInCell(2, 2), p1.getWorkerSelected(2));
        assertNotEquals(p1.getWorkerSelected(1), p1.getWorkerSelected(2));

        Player p2 = new Player("player2");
        p2.setGodChoice(God.MINOTAUR);
        assertFalse(p2.setWorker1(0, 0));
        assertNull(p2.getWorkerSelected(1));
        assertFalse(p2.setWorker2(0, 0));
        assertNull(p2.getWorkerSelected(2));
        assertTrue(p2.setWorker1(1, 1));
        assertEquals(Map.getInstance().getWorkerInCell(1, 1), p2.getWorkerSelected(1));
    }

    @Test
    void player_WorkerApolloTest() {
        Player p = new Player("APOLLO");
        p.setGodChoice(God.APOLLO);
        p.setWorker1(0, 0);
        assertTrue(p.getWorkerSelected(1) instanceof WorkerApollo);
        p.setWorker2(0, 1);
        assertTrue(p.getWorkerSelected(2) instanceof WorkerApollo);
    }

    @Test
    void player_WorkerArtemisTest() {
        Player p = new Player("ARTEMIS");
        p.setGodChoice(God.ARTEMIS);
        p.setWorker1(0, 0);
        assertTrue(p.getWorkerSelected(1) instanceof WorkerArtemis);
        p.setWorker2(0, 1);
        assertTrue(p.getWorkerSelected(2) instanceof WorkerArtemis);
    }

    @Test
    void player_WorkerAthenaTest() {
        Player p = new Player("ATHENA");
        p.setGodChoice(God.ATHENA);
        p.setWorker1(0, 0);
        assertTrue(p.getWorkerSelected(1) instanceof WorkerAthena);
        p.setWorker2(0, 1);
        assertTrue(p.getWorkerSelected(2) instanceof WorkerAthena);
    }

    @Test
    void player_WorkerAtlasTest() {
        Player p = new Player("ATLAS");
        p.setGodChoice(God.ATLAS);
        p.setWorker1(0, 0);
        assertTrue(p.getWorkerSelected(1) instanceof WorkerAtlas);
        p.setWorker2(0, 1);
        assertTrue(p.getWorkerSelected(2) instanceof WorkerAtlas);
    }

    @Test
    void player_WorkerChronusTest() {
        Player p = new Player("CHRONUS");
        p.setGodChoice(God.CHRONUS);
        p.setWorker1(0, 0);
        assertTrue(p.getWorkerSelected(1) instanceof WorkerChronus);
        p.setWorker2(0, 1);
        assertTrue(p.getWorkerSelected(2) instanceof WorkerChronus);
    }

    @Test
    void player_WorkerDemeterTest() {
        Player p = new Player("DEMETER");
        p.setGodChoice(God.DEMETER);
        p.setWorker1(0, 0);
        assertTrue(p.getWorkerSelected(1) instanceof WorkerDemeter);
        p.setWorker2(0, 1);
        assertTrue(p.getWorkerSelected(2) instanceof WorkerDemeter);
    }

    @Test
    void player_WorkerHephaestusTest() {
        Player p = new Player("HEPHAESTUS");
        p.setGodChoice(God.HEPHAESTUS);
        p.setWorker1(0, 0);
        assertTrue(p.getWorkerSelected(1) instanceof WorkerHephaestus);
        p.setWorker2(0, 1);
        assertTrue(p.getWorkerSelected(2) instanceof WorkerHephaestus);
    }

    @Test
    void player_WorkerHeraTest() {
        Player p = new Player("HERA");
        p.setGodChoice(God.HERA);
        p.setWorker1(0, 0);
        assertTrue(p.getWorkerSelected(1) instanceof WorkerHera);
        p.setWorker2(0, 1);
        assertTrue(p.getWorkerSelected(2) instanceof WorkerHera);
    }

    @Test
    void player_WorkerHestiaTest() {
        Player p = new Player("HESTIA");
        p.setGodChoice(God.HESTIA);
        p.setWorker1(0, 0);
        assertTrue(p.getWorkerSelected(1) instanceof WorkerHestia);
        p.setWorker2(0, 1);
        assertTrue(p.getWorkerSelected(2) instanceof WorkerHestia);
    }

    @Test
    void player_WorkerMinotaurTest() {
        Player p = new Player("MINOTAUR");
        p.setGodChoice(God.MINOTAUR);
        p.setWorker1(0, 0);
        assertTrue(p.getWorkerSelected(1) instanceof WorkerMinotaur);
        p.setWorker2(0, 1);
        assertTrue(p.getWorkerSelected(2) instanceof WorkerMinotaur);
    }

    @Test
    void player_WorkerPanTest() {
        Player p = new Player("PAN");
        p.setGodChoice(God.PAN);
        p.setWorker1(0, 0);
        assertTrue(p.getWorkerSelected(1) instanceof WorkerPan);
        p.setWorker2(0, 1);
        assertTrue(p.getWorkerSelected(2) instanceof WorkerPan);
    }

    @Test
    void player_WorkerPrometheusTest() {
        Player p = new Player("PROMETHEUS");
        p.setGodChoice(God.PROMETHEUS);
        p.setWorker1(0, 0);
        assertTrue(p.getWorkerSelected(1) instanceof WorkerPrometheus);
        p.setWorker2(0, 1);
        assertTrue(p.getWorkerSelected(2) instanceof WorkerPrometheus);
    }

    @Test
    void player_WorkerTritonTest() {
        Player p = new Player("TRITON");
        p.setGodChoice(God.TRITON);
        p.setWorker1(0, 0);
        assertTrue(p.getWorkerSelected(1) instanceof WorkerTriton);
        p.setWorker2(0, 1);
        assertTrue(p.getWorkerSelected(2) instanceof WorkerTriton);
    }

    @Test
    void player_WorkerZeusTest() {
        Player p = new Player("ZEUS");
        p.setGodChoice(God.ZEUS);
        p.setWorker1(0, 0);
        assertTrue(p.getWorkerSelected(1) instanceof WorkerZeus);
        p.setWorker2(0, 1);
        assertTrue(p.getWorkerSelected(2) instanceof WorkerZeus);
    }

    @Test
    void player_PrintTest() {
        Player p1 = new Player("player1");
        p1.setGodChoice(God.APOLLO);
        p1.setWorker1(0, 0);
        p1.setWorker2(2, 2);
        p1.printWorkersPositions();
    }

}