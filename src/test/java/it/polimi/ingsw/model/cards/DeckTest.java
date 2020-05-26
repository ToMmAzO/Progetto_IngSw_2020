package it.polimi.ingsw.model.cards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void deck_InitializationTest1() {
        new Deck(2);
        God[] godsSelected = Deck.getInstance().getCardsSelected();
        assertNotEquals(godsSelected[0], godsSelected[1]);
        assertNotEquals(godsSelected[1], godsSelected[0]);

        for (int i = 0; i < 2; i++) {
            assertTrue(Deck.getInstance().isAvailable(i + 1));
        }
    }

    @Test
    void deck_InitializationTest2() {
        new Deck(3);
        God[] godsSelected = Deck.getInstance().getCardsSelected();
        assertNotEquals(godsSelected[0], godsSelected[1]);
        assertNotEquals(godsSelected[1], godsSelected[2]);
        assertNotEquals(godsSelected[2], godsSelected[0]);

        for (int i = 0; i < 3; i++) {
            assertTrue(Deck.getInstance().isAvailable(i + 1));
        }
    }

    @Test
    void deck_CardSelectionTest() {
        new Deck(3);
        God[] godsSelected = Deck.getInstance().getCardsSelected();

        for (int i = 0; i < 3; i++){
            God god = godsSelected[i];
            God godChosen = Deck.getInstance().getCardToPlayer(i+1);
            assertEquals(god,godChosen);
            assertFalse(Deck.getInstance().isAvailable(i + 1));
        }
    }

}