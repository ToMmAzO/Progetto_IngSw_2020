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
            System.out.println(x[i].getGodName().toString() + ": " + x[i].getGodPower());
            if (a[i]){
                System.out.println("La carta è ancora disponibile.\n");
            } else {
                System.out.println("La carta non è più disponibile.\n");
            }

        }
        System.out.println("\n*Scelta della carta*\n");
        Game.getCardToPlayer(2);//seleziona la seconda carta dell'array
        God[] y = Game.getCardsSelected();
        Boolean[] b = Game.getAvailability();
        for (int i=0; i<3; i++){
            System.out.println(y[i].getGodName().toString() + ": " + y[i].getGodPower());
            if (a[i]){
                System.out.println("La carta è ancora disponibile.\n");
            } else {
                System.out.println("La carta non è più disponibile.\n");
            }
        }
    }
    */

    public Game(){
        new Deck();
        cardsSelected = Deck.extractCards(GameManager.getNumberOfPlayers());
        availability = new Boolean[GameManager.getNumberOfPlayers()];
        for (int i = 0; i < GameManager.getNumberOfPlayers(); i++){
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

}
