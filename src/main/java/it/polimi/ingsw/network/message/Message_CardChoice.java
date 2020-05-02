package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.model.Cards.Deck;

public class Message_CardChoice extends Message{

    private final GameState gameState = GameState.CARD_CHOICE;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        Deck.getInstance().printCards();
        System.out.print("Scegli il numero di una delle carte ancora disponibili: ");
    }

}
