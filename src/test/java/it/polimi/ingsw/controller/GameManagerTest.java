package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.workers.WorkerAtlas;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.SocketClientConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;


class GameManagerTest {

    @BeforeEach
    void setUp() {
        new GameManager();
    }

    @Test
    void addPlayerTest() throws IOException {
        Server server = new Server(12345);
        assertTrue(GameManager.getInstance().mapEmpty());

        Player p1 = new Player("Player 1");
        try {
            GameManager.getInstance().addPlayerConnection(p1, new SocketClientConnection(new Socket(), server));
        } catch(NullPointerException ignored){}
        assertFalse(GameManager.getInstance().mapEmpty());
        assertEquals(1, GameManager.getInstance().getPlayersInGame().length);
        assertEquals(p1, GameManager.getInstance().getPlayersInGame()[0]);

        Player p2 = new Player("Player 2");
        try {
            GameManager.getInstance().addPlayerConnection(p2, new SocketClientConnection(new Socket(), server));
        } catch(NullPointerException ignored){}
        assertEquals(2, GameManager.getInstance().getPlayersInGame().length);
        assertEquals(p1, GameManager.getInstance().getPlayersInGame()[0]);
        assertEquals(p2, GameManager.getInstance().getPlayersInGame()[1]);

        Player p3 = new Player("Player 3");
        try {
            GameManager.getInstance().addPlayerConnection(p3, new SocketClientConnection(new Socket(), server));
        } catch(NullPointerException ignored){}
        assertEquals(3, GameManager.getInstance().getPlayersInGame().length);
        assertEquals(p1, GameManager.getInstance().getPlayersInGame()[0]);
        assertEquals(p2, GameManager.getInstance().getPlayersInGame()[1]);
        assertEquals(p3, GameManager.getInstance().getPlayersInGame()[2]);
    }

    @Test
    void setNumberOfPlayersTest(){
        GameManager.getInstance().setNumberOfPlayers(2);
        assertEquals(2, Deck.getInstance().getCardsSelected().length);

        GameManager.getInstance().setNumberOfPlayers(3);
        assertEquals(3, Deck.getInstance().getCardsSelected().length);

        Deck old = Deck.getInstance();

        GameManager.getInstance().setNumberOfPlayers(1);
        assertEquals(old, Deck.getInstance());

        GameManager.getInstance().setNumberOfPlayers(4);
        assertEquals(old, Deck.getInstance());

    }

    @Test
    void choiceOfCardTest(){
        new Deck(3);

        Player p1 = new Player("Player 1");
        GameManager.getInstance().setCurrPlayer(p1);
        try {
            GameManager.getInstance().choiceOfCard(1);
        } catch(IndexOutOfBoundsException ignored){}
        assertEquals(p1.getGodChoice(), Deck.getInstance().getCardsSelected()[0]);
        assertFalse(Deck.getInstance().getAvailability()[0]);

        Player p2 = new Player("Player 2");
        GameManager.getInstance().setCurrPlayer(p2);
        try {
            GameManager.getInstance().choiceOfCard(1);
        } catch(IndexOutOfBoundsException ignored){}
        assertNull(p2.getGodChoice());
        try {
            GameManager.getInstance().choiceOfCard(2);
        } catch(IndexOutOfBoundsException ignored){}
        assertEquals(p2.getGodChoice(), Deck.getInstance().getCardsSelected()[1]);
        assertFalse(Deck.getInstance().getAvailability()[1]);

        Player p3 = new Player("Player 3");
        GameManager.getInstance().setCurrPlayer(p3);
        try {
            GameManager.getInstance().choiceOfCard(1);
        } catch(IndexOutOfBoundsException ignored){}
        assertNull(p3.getGodChoice());
        try {
            GameManager.getInstance().choiceOfCard(2);
        } catch(IndexOutOfBoundsException ignored){}
        assertNull(p3.getGodChoice());
        try {
            GameManager.getInstance().choiceOfCard(3);
        } catch(IndexOutOfBoundsException ignored){}
        assertEquals(p3.getGodChoice(), Deck.getInstance().getCardsSelected()[2]);
        assertFalse(Deck.getInstance().getAvailability()[2]);
    }

    @Test
    void setWorkerTest(){
        new WorkerAtlas("Worker", 0, 0);

        Player p = new Player("Player");
        p.setGodChoice(God.APOLLO);
        assertNull(p.getWorkerSelected(1));
        assertNull(p.getWorkerSelected(2));
        GameManager.getInstance().setCurrPlayer(p);

        GameManager.getInstance().setWorker(5, 0);
        assertNull(p.getWorkerSelected(1));

        GameManager.getInstance().setWorker(0, 5);
        assertNull(p.getWorkerSelected(1));

        GameManager.getInstance().setWorker(0, 0);
        assertNull(p.getWorkerSelected(1));

        GameManager.getInstance().setWorker(1, 1);
        assertEquals(p.getWorkerSelected(1), Map.getInstance().getWorkerInCell(1, 1));

        GameManager.getInstance().setWorker(5, 0);
        assertNotNull(p.getWorkerSelected(1));
        assertNull(p.getWorkerSelected(2));

        GameManager.getInstance().setWorker(0, 5);
        assertNotNull(p.getWorkerSelected(1));
        assertNull(p.getWorkerSelected(2));

        GameManager.getInstance().setWorker(0, 0);
        assertNotNull(p.getWorkerSelected(1));
        assertNull(p.getWorkerSelected(2));

        GameManager.getInstance().setWorker(1, 1);
        assertNotNull(p.getWorkerSelected(1));
        assertNull(p.getWorkerSelected(2));

        try{
            GameManager.getInstance().setWorker(2, 2);
        } catch(IndexOutOfBoundsException ignored){}
        assertNotNull(p.getWorkerSelected(1));
        assertEquals(p.getWorkerSelected(2), Map.getInstance().getWorkerInCell(2, 2));
    }

    @Test
    void nextPlayerTest() throws IOException {
        Server server = new Server(54321);
        Player p1 = new Player("Player 1");
        try {
            GameManager.getInstance().addPlayerConnection(p1, new SocketClientConnection(new Socket(), server));
        } catch(NullPointerException ignored){}
        Player p2 = new Player("Player 2");
        try {
            GameManager.getInstance().addPlayerConnection(p2, new SocketClientConnection(new Socket(), server));
        } catch(NullPointerException ignored){}
        Player p3 = new Player("Player 3");
        try {
            GameManager.getInstance().addPlayerConnection(p3, new SocketClientConnection(new Socket(), server));
        } catch(NullPointerException ignored){}

        try{
            GameManager.getInstance().nextPlayer(p1);
        } catch(NullPointerException ignored){}
        assertEquals(p2, GameManager.getInstance().getCurrPlayer());

        try{
            GameManager.getInstance().nextPlayer(p2);
        } catch(NullPointerException ignored){}
        assertEquals(p3, GameManager.getInstance().getCurrPlayer());

        try {
            GameManager.getInstance().nextPlayer(p3);
        } catch(NullPointerException ignored){}
        assertEquals(p1, GameManager.getInstance().getCurrPlayer());
    }

}