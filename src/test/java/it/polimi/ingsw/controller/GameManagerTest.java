package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Cards.Deck;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    @BeforeEach
    void setUp(){
        GameManager.addFirstPlayer("Player 1", 3);
    }

    @AfterEach
    void cleanUp(){

    }

    @Test
    void addFirstPlayer() {
        assertEquals(3, Deck.getCardsSelected().length);
        for (int i = 0; i < 3; i++){
            assertTrue(Deck.getAvailability()[i]);
        }
        assertEquals(3, GameManager.getNumberOfPlayers());
        assertEquals("Player 1", GameManager.getPlayersInGame()[0].getNickname());
        assertEquals(1, GameManager.getPlayersInGame().length);
    }

    @Test
    void addPlayer() {
        GameManager.addPlayer("Player 2");
        assertEquals("Player 2", GameManager.getPlayersInGame()[1].getNickname());
        assertEquals(2, GameManager.getPlayersInGame().length);
        assertNotEquals(GameManager.getPlayersInGame()[0].getColor(), GameManager.getPlayersInGame()[1].getColor());

        GameManager.addPlayer("Player 3");
        assertEquals("Player 3", GameManager.getPlayersInGame()[2].getNickname());
        assertEquals(3, GameManager.getPlayersInGame().length);
        assertNotEquals(GameManager.getPlayersInGame()[0].getColor(), GameManager.getPlayersInGame()[2].getColor());
        assertNotEquals(GameManager.getPlayersInGame()[1].getColor(), GameManager.getPlayersInGame()[2].getColor());
    }

    @Test
    void choiceOfCard() {
        GameManager.addPlayer("Player 2");
        GameManager.addPlayer("Player 3");

        GameManager.choiceOfCard(0, 2);
        assertFalse(Deck.isAvailable(2));
        assertEquals(Deck.getCardToPlayer(2), GameManager.getPlayersInGame()[0].getGodChoice());

        GameManager.choiceOfCard(1, 3);
        assertFalse(Deck.isAvailable(3));
        assertEquals(Deck.getCardToPlayer(3), GameManager.getPlayersInGame()[1].getGodChoice());

        GameManager.choiceOfCard(2, 1);
        assertFalse(Deck.isAvailable(1));
        assertEquals(Deck.getCardToPlayer(1), GameManager.getPlayersInGame()[2].getGodChoice());
    }

    @Test
    void deletePlayer() {
        GameManager.addPlayer("Player 2");
        GameManager.addPlayer("Player 3");

        GameManager.choiceOfCard(0, 2);
        GameManager.getPlayersInGame()[0].setWorker1(0, 0);
        GameManager.getPlayersInGame()[0].setWorker2(1, 1);

        GameManager.choiceOfCard(1, 3);
        GameManager.getPlayersInGame()[1].setWorker1(2, 2);
        GameManager.getPlayersInGame()[1].setWorker2(3, 3);

        GameManager.choiceOfCard(2, 1);
        GameManager.getPlayersInGame()[2].setWorker1(4, 4);
        GameManager.getPlayersInGame()[2].setWorker2(0, 4);

        GameManager.deletePlayer(GameManager.getPlayersInGame()[0]);
        assertEquals(2, GameManager.getPlayersInGame().length);
        assertEquals("Player 2", GameManager.getPlayersInGame()[0].getNickname());
        assertEquals("Player 3", GameManager.getPlayersInGame()[1].getNickname());

        GameManager.deletePlayer(GameManager.getPlayersInGame()[1]);
        assertEquals(1, GameManager.getPlayersInGame().length);
        assertEquals("Player 2", GameManager.getPlayersInGame()[0].getNickname());
    }

}