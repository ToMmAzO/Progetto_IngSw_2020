package it.polimi.ingsw.model.cards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void deckTest() {
        new Deck(3);
        God[] godsSelected = Deck.getInstance().getCardsSelected();
        assertNotEquals(godsSelected[0], godsSelected[1]);
        assertNotEquals(godsSelected[1], godsSelected[2]);
        assertNotEquals(godsSelected[2], godsSelected[0]);
        for (int i = 0; i < 3; i++){
            assertTrue(Deck.getInstance().getAvailability()[i]);
        }
        for (int i = 0; i < 3; i++){
            God god = godsSelected[i];
            God godChosen = Deck.getInstance().getCardToPlayer(i+1);
            assertEquals(god,godChosen);
            assertFalse(Deck.getInstance().getAvailability()[i]);
        }
    }

}