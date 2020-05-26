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
        System.out.println("\nLa partita avrà le seguenti carte divinità:");
        for (int i = 0; i < cardsSelected.length; i++){
            System.out.println("Carta " + (i+1) + " --> " + cardsSelected[i].toString() + ": " + God.getGodDescription(cardsSelected[i]));
            if (availability[i]){
                System.out.println("            La carta è ancora disponibile.");
            } else {
                System.out.println("            La carta non è più disponibile.");
            }
        }
    }

}
