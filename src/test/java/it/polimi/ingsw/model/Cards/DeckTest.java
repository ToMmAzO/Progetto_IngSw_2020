package it.polimi.ingsw.model.Cards;

import org.junit.Test;

import static org.junit.Assert.*;

public class DeckTest {

    @Test
    public void Deck() {
        new Deck(3);
        God[] godsSelected = Deck.getCardsSelected();
        assertNotEquals(godsSelected[0], godsSelected[1]);
        assertNotEquals(godsSelected[1], godsSelected[2]);
        assertNotEquals(godsSelected[2], godsSelected[0]);
        for (int i = 0; i < 3; i++){
            assertTrue(Deck.getAvailability()[i]);
        }
        for (int i = 0; i < 3; i++){
            God god = godsSelected[i];
            God godChosen = Deck.getCardToPlayer(i+1);
            assertEquals(god,godChosen);
            assertFalse(Deck.getAvailability()[i]);
        }
    }

}