package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Cards.Deck;
import it.polimi.ingsw.model.Cards.God;

public class Game {

    private God[] cardsSelected;
    private Boolean[] availability = {true, true, true};


    /*per testare impostare in setCardsSelected()
        this.cardsSelected = Deck.extractCards(3);
    */
    /*
    public static void main (String[] args){
        Deck d= new Deck();
        Game g= new Game();
        g.setCardsSelected();
        God[] x = g.getCardsSelected();
        Boolean[] a = g.getAvailability();
        for (int i=0; i<3; i++){
            System.out.println(x[i].getGodName().toString() + x[i].getGodPower());
            System.out.println(a[i].toString());
        }
        g.getCardToPlayer(2);//seleziona la seconda carta dell'array
        God[] y = g.getCardsSelected();
        Boolean[] b = g.getAvailability();
        for (int i=0; i<3; i++){
            System.out.println(y[i].getGodName().toString() + y[i].getGodPower());
            System.out.println(a[i].toString());
        }
    }
     */


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
