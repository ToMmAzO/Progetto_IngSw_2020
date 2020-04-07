package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Cards.God;
import it.polimi.ingsw.model.Game;

public class View {

    public static void printCardsSelected(){
        God[] x = Game.getCardsSelected();
        Boolean[] a = Game.getAvailability();
        for (int i=0; i<3; i++){
            System.out.println("Carta " + (i+1) + ": " + x[i].getGodName().toString() + ": " + x[i].getGodPower());
            if (a[i]){
                System.out.println("La carta è ancora disponibile.\n");
            } else {
                System.out.println("La carta non è più disponibile.\n");
            }
        }
    }

    public static void printCardChosen(God god){
        System.out.println("Hai il potere di " + god.getGodName().toString() + ": " + god.getGodPower());
    }

    public static void printMapToPlayer(){
        Map.printMap();
    }

}
