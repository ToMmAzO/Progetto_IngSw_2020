package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Cards.Deck;

public class Message_CardChoice extends Message{

    private final Target target = Target.CARD_CHOICE;

    public Target getTarget(){
        return target;
    }

    @Override
    public void printMessage(){
        Deck.getInstance().printCards();
        System.out.print("Scegli il numero di una delle carte ancora disponibili: ");
    }

}
