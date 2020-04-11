package it.polimi.ingsw.model.Cards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void getCardToPlayer() {
        new Deck(3);
        int index = 0;
        God g = Deck.getCardsSelected()[index];
        God myg = Deck.getCardToPlayer(index+1);
        assertEquals(g,myg);
    }
}