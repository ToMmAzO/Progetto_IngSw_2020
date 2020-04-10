package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Cards.Deck;
import it.polimi.ingsw.model.Cards.God;
import it.polimi.ingsw.model.Player;

public class View {

    public static void printCardsSelected(){
        God[] x = Deck.getCardsSelected();
        boolean[] a = Deck.getAvailability();
        for (int i = 0; i < x.length; i++){
            System.out.println("Carta " + (i+1) + " --> " + x[i].toString() + ": " + x[i].getGodDescription(x[i]));
            if (a[i]){
                System.out.println("La carta è ancora disponibile.\n");
            } else {
                System.out.println("La carta non è più disponibile.\n");
            }
        }
    }

    public static void printCardChosen(God god){
        System.out.println("Hai il potere di " + god.toString() + ": " + god.getGodDescription(god));
    }

    public static void printMap(){
        System.out.printf("   |   %d  |   %d  |   %d  |   %d  |   %d  |\n", 0, 1, 2, 3, 4);
        System.out.println("---|------|------|------|------|------|");
        for (int i = 0; i < 5; i++){
            System.out.printf("%d  |", i);
            for (int j = 0; j < 5; j++){
                if(Map.noWorkerHere(i, j)){
                    if(Map.getCellBlockType(i, j) == BlockType.GROUND){
                        System.out.printf("%7c", '|');
                    } else{
                        System.out.printf("%s|", Map.getCellBlockType(i, j).toString());
                    }
                } else{
                    System.out.printf("|%s||", Map.getWorkerInCell(i, j).getIdWorker());
                }
            }
            System.out.println("\n---|------|------|------|------|------|");
        }
    }

    public static void printWorkersPositions(Player player){
        System.out.printf("%s: %s (livello %s), %s (livello %s).\n", player.getNickname(),
                player.getWorkerSelected(1).getIdWorker(), player.getWorkerSelected(1).getCoordZ(),
                player.getWorkerSelected(2).getIdWorker(), player.getWorkerSelected(2).getCoordZ()
        );
    }

}
