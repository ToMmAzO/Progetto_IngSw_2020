package it.polimi.ingsw.model.cards;

import java.io.Serializable;

public class DeckCopy implements Serializable {

    private final God[] cardsSelected;
    private final boolean[] availability;

    public DeckCopy(){
        cardsSelected = Deck.getInstance().getCardsSelected();
        availability = new boolean[cardsSelected.length];
        for(int i = 0; i < availability.length; i++){
            availability[i] = Deck.getInstance().isAvailable(i + 1);
        }
    }

    public void printCards(){
        System.out.println("\nThe game will have the following divinity cards:");
        for (int i = 0; i < cardsSelected.length; i++){
            System.out.println("Card " + (i+1) + " --> " + cardsSelected[i].toString() + ": " + God.getGodDescription(cardsSelected[i]));
            if (availability[i]){
                System.out.println("           This card is still available.");
            } else {
                System.out.println("           This card is not available.");
            }
        }
    }

    public God[] getCardsSelected() {
        return cardsSelected;
    }

    public boolean[] getAvailability() {
        return availability;
    }
}
