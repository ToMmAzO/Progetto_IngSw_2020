package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.controller.GameManager;

import java.io.Serializable;

public class Deck implements Serializable {

    private static Deck deck = null;
    private final God[] godSelected;
    private final boolean[] availability;

    public Deck(int numberOfCards){
        God[] godList = new God[God.values().length];
        int i = 0;
        for(God d: God.values()) {
            godList[i] = d;
            i++;
        }
        int[] casual = extractCasualNumbers(numberOfCards);
        godSelected = new God[numberOfCards];
        availability = new boolean[numberOfCards];
        for (i = 0; i < numberOfCards; i++){
            godSelected[i] = godList[casual[i]];
            availability[i] = true;
        }
        deck = this;
    }

    public static Deck getInstance(){
        return deck;
    }

    public God[] getCardsSelected(){
        return godSelected;
    }

    public boolean[] getAvailability() {
        return availability;
    }

    public God getCardToPlayer(int cardNumber){
        availability[cardNumber-1] = false;
        return godSelected[cardNumber-1];
    }

    public boolean isAvailable(int cardNumber) {
        return (cardNumber >= 1 && cardNumber <= GameManager.getNumberOfPlayers()) && (Deck.getInstance().getAvailability()[cardNumber - 1]);
    }

    private int[] extractCasualNumbers(int numberOfNumbers){
        int numberOfGods = God.values().length;
        int[] casual = new int[numberOfNumbers];
        casual[0] = (int) (Math.random() * numberOfGods);
        while (true){
            int random = (int) (Math.random() * numberOfGods);
            if (random != casual[0]) {
                casual[1] = random;
                if (numberOfNumbers == 3){
                    while (true){
                        random = (int) (Math.random() * numberOfGods);
                        if (random != casual[0] && random != casual[1]) {
                            casual[2] = random;
                            break;
                        }
                    }
                }
                break;
            }
        }
        return casual;
    }

    public void printCards(){
        System.out.println("La partita avrà le seguenti carte divinità:");
        for (int i = 0; i < godSelected.length; i++){
            System.out.println("Carta " + (i+1) + " --> " + godSelected[i].toString() + ": " + God.getGodDescription(godSelected[i]));
            if (availability[i]){
                System.out.println("            La carta è ancora disponibile.");
            } else {
                System.out.println("            La carta non è più disponibile.");
            }
        }
    }

}
