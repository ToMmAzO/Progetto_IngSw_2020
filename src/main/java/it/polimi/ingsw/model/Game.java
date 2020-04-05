package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Cards.Deck;
import it.polimi.ingsw.model.Cards.God;

public class Game {

    private God[] cardsSelected;
    private Boolean[] availability;
    
    /*per testare impostare tutte le istanze in Game() di GameManager.getNumberOfPlayers() a 3
    */
    /*
    public static void main (String[] args){
        Game g= new Game();
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

    public Game(){
        Deck d = new Deck();
        this.cardsSelected = d.extractCards(GameManager.getNumberOfPlayers());
        this.availability = new Boolean[GameManager.getNumberOfPlayers()];
        for (int i = 0; i < GameManager.getNumberOfPlayers(); i++){
            this.availability[i] = true;
        }
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
