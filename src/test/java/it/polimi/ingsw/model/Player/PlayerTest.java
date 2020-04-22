package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Cards.God;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void Player(){
        Player p1 = new Player("player1");
        Player p2 = new Player("player2");
        Player p3 = new Player("player3");
        assertNotEquals(p1.getColor(), p2.getColor());
        assertNotEquals(p1.getColor(), p3.getColor());
        assertNotEquals(p2.getColor(), p3.getColor());
        new Map();
        p1.setGodChoice(God.APOLLO);
        assertTrue(p1.setWorker1(0, 0));
        assertTrue(p1.setWorker2(2, 2));
        assertEquals(Map.getWorkerInCell(0, 0), p1.getWorkerSelected(1));
        assertEquals(Map.getWorkerInCell(2, 2), p1.getWorkerSelected(2));
        assertNotEquals(p1.getWorkerSelected(1), p1.getWorkerSelected(2));
        p2.setGodChoice(God.MINOTAUR);
        assertFalse(p2.setWorker1(0, 0));
        assertNull(p2.getWorkerSelected(1));
        assertTrue(p2.setWorker1(1, 1));
        assertEquals(Map.getWorkerInCell(1, 1), p2.getWorkerSelected(1));
    }

}