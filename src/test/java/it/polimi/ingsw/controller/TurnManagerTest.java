package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnManagerTest {

    @BeforeEach
    void setUp(){
        new Map();
    }

    @Test
    void verifyRegularityTest1() {
        Player p = new Player("Player 1");
        p.setGodChoice(God.APOLLO);
        p.setWorker1(0, 0);
        p.setWorker2(0,1);
        assertTrue(TurnManager.verifyRegularity(p, 1));
        assertTrue(TurnManager.verifyRegularity(p, 2));
    }

    @Test
    void verifyRegularityTest2() {
        Player p1 = new Player("Player 1");
        p1.setGodChoice(God.DEMETER);
        p1.setWorker1(0, 0);
        p1.setWorker2(0,1);

        Player p2 = new Player("Player 2");
        p2.setGodChoice(God.HEPHAESTUS);
        p2.setWorker1(0, 2);
        p2.setWorker2(1,2);

        Player p3 = new Player("Player 3");
        p3.setGodChoice(God.MINOTAUR);
        p3.setWorker1(1, 0);
        p3.setWorker2(1,1);

        assertFalse(TurnManager.verifyRegularity(p1, 1));
        assertFalse(TurnManager.verifyRegularity(p1, 2));
        assertTrue(TurnManager.verifyRegularity(p2, 1));
        assertTrue(TurnManager.verifyRegularity(p2, 2));
        assertTrue(TurnManager.verifyRegularity(p3, 1));
        assertTrue(TurnManager.verifyRegularity(p3, 2));
    }

}