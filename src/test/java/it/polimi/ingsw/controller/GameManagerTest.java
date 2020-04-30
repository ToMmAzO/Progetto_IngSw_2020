package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Cards.Deck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    @Test
    void GameFlowTest1() {
        GameManager.addFirstPlayer("Player 1", 2);
        assertEquals(2, Deck.getInstance().getCardsSelected().length);
        for (int i = 0; i < 2; i++){
            assertTrue(Deck.getInstance().getAvailability()[i]);
        }
        assertEquals(2, GameManager.getNumberOfPlayers());
        assertEquals("Player 1", GameManager.getPlayersInGame()[0].getNickname());
        assertEquals(1, GameManager.getPlayersInGame().length);

        GameManager.addPlayer("Player 2");
        assertEquals("Player 2", GameManager.getPlayersInGame()[1].getNickname());
        assertEquals(2, GameManager.getPlayersInGame().length);

        GameManager.choiceOfCard(0, 2);
        assertFalse(Deck.getInstance().isAvailable(2));
        assertEquals(Deck.getInstance().getCardToPlayer(2), GameManager.getPlayersInGame()[0].getGodChoice());

        GameManager.choiceOfCard(1, 1);
        assertFalse(Deck.getInstance().isAvailable(1));
        assertEquals(Deck.getInstance().getCardToPlayer(1), GameManager.getPlayersInGame()[1].getGodChoice());

        GameManager.getPlayersInGame()[0].setWorker1(0, 0);
        GameManager.getPlayersInGame()[0].setWorker2(1, 1);
        GameManager.getPlayersInGame()[1].setWorker1(2, 2);
        GameManager.getPlayersInGame()[1].setWorker2(3, 3);

        GameManager.deletePlayer(GameManager.getPlayersInGame()[1]);
        assertEquals(1, GameManager.getPlayersInGame().length);
        assertEquals("Player 1", GameManager.getPlayersInGame()[0].getNickname());

        GameManager.deletePlayer(GameManager.getPlayersInGame()[0]);
    }

    @Test
    void GameFlowTest2() {
        GameManager.addFirstPlayer("Player 1", 3);
        assertEquals(3, Deck.getInstance().getCardsSelected().length);
        for (int i = 0; i < 3; i++){
            assertTrue(Deck.getInstance().getAvailability()[i]);
        }
        assertEquals(3, GameManager.getNumberOfPlayers());
        assertEquals("Player 1", GameManager.getPlayersInGame()[0].getNickname());
        assertEquals(1, GameManager.getPlayersInGame().length);

        GameManager.addPlayer("Player 2");
        assertEquals("Player 2", GameManager.getPlayersInGame()[1].getNickname());
        assertEquals(2, GameManager.getPlayersInGame().length);

        GameManager.addPlayer("Player 3");
        assertEquals("Player 3", GameManager.getPlayersInGame()[2].getNickname());
        assertEquals(3, GameManager.getPlayersInGame().length);

        GameManager.choiceOfCard(0, 2);
        assertFalse(Deck.getInstance().isAvailable(2));
        assertEquals(Deck.getInstance().getCardToPlayer(2), GameManager.getPlayersInGame()[0].getGodChoice());

        GameManager.choiceOfCard(1, 3);
        assertFalse(Deck.getInstance().isAvailable(3));
        assertEquals(Deck.getInstance().getCardToPlayer(3), GameManager.getPlayersInGame()[1].getGodChoice());

        GameManager.choiceOfCard(2, 1);
        assertFalse(Deck.getInstance().isAvailable(1));
        assertEquals(Deck.getInstance().getCardToPlayer(1), GameManager.getPlayersInGame()[2].getGodChoice());

        GameManager.getPlayersInGame()[0].setWorker1(0, 0);
        GameManager.getPlayersInGame()[0].setWorker2(1, 1);
        GameManager.getPlayersInGame()[1].setWorker1(2, 2);
        GameManager.getPlayersInGame()[1].setWorker2(3, 3);
        GameManager.getPlayersInGame()[2].setWorker1(4, 4);
        GameManager.getPlayersInGame()[2].setWorker2(0, 4);

        GameManager.deletePlayer(GameManager.getPlayersInGame()[0]);
         assertEquals(2, GameManager.getPlayersInGame().length);
        assertEquals("Player 2", GameManager.getPlayersInGame()[0].getNickname());
        assertEquals("Player 3", GameManager.getPlayersInGame()[1].getNickname());

        GameManager.deletePlayer(GameManager.getPlayersInGame()[1]);
        assertEquals(1, GameManager.getPlayersInGame().length);
        assertEquals("Player 2", GameManager.getPlayersInGame()[0].getNickname());

        GameManager.deletePlayer(GameManager.getPlayersInGame()[0]);
    }

}