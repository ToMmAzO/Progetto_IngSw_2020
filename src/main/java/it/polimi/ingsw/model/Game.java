package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Cards.Deck;
import it.polimi.ingsw.model.Cards.God;

public class Game {

    private static God[] cardsSelected;
    private static Boolean[] availability;
    
    /*per testare impostare tutte le istanze in Game() di GameManager.getNumberOfPlayers() a 3
    */
    /*
    public static void main (String[] args){
        new Game();
        God[] x = Game.getCardsSelected();
        Boolean[] a = Game.getAvailability();
        for (int i=0; i<3; i++){
            System.out.println(x[i].getGodName().toString() + x[i].getGodPower());
            System.out.println(a[i].toString());
        }
        Game.getCardToPlayer(2);//seleziona la seconda carta dell'array
        God[] y = Game.getCardsSelected();
        Boolean[] b = Game.getAvailability();
        for (int i=0; i<3; i++){
            System.out.println(y[i].getGodName().toString() + y[i].getGodPower());
            System.out.println(a[i].toString());
        }
    }
    */


    public Game(){
        Deck d = new Deck();
        cardsSelected = d.extractCards(3);
        availability = new Boolean[3];
        for (int i = 0; i < 3; i++){
            availability[i] = true;
        }
    }

    public static God[] getCardsSelected(){
        return cardsSelected;
    }

    public static Boolean[] getAvailability() {
        return availability;
    }

    public static God getCardToPlayer(int number){
        cardChosen(number);
        return cardsSelected[number-1];
    }

    private static void cardChosen(int number) {
        availability[number-1] = false;
    }

    public void endGame(){
        //codice
    }

}
