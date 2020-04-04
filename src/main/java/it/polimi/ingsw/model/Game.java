package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Cards.Deck;

public class Game {

    private God[] cardsSelected;
    private Boolean[] availability = {true, true, true};

    public void setCardsSelected(){
        this.cardsSelected = Deck.extractCards(GameManager.getNumberOfPlayers());
    }

    public God[] getCardsSelected(){
        return cardsSelected;
    }

    public Boolean[] getAvailability() {
        return availability;
    }

    public God getCardToPlayer(int number){
        cardChosen(number);
        return cardsSelected[number-1];
    }

    public void cardChosen(int number) {
        this.availability[number-1] = false;
    }

    public void endGame(){
        //codice
    }

}
