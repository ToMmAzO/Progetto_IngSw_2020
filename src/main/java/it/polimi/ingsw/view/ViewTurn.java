package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Player.Player;

import java.util.Scanner;

public class ViewTurn {

    private static final Scanner scanner= new Scanner(System.in);

    public static int turn(Player player){
        int selectionWorker = 0;

        System.out.print("Scegli che worker usare: " + player.getWorkerSelected(1).getIdWorker() + " oppure " + player.getWorkerSelected(2).getIdWorker() + ". ");
        while (selectionWorker != 1 && selectionWorker != 2){
            try{
                System.out.print("Scrivi 1 oppure 2: ");
                selectionWorker= Integer.parseInt((scanner.nextLine()));
            } catch(IllegalArgumentException e){
                System.out.print("Formato Input scorretto! ");
            }
        }
        return selectionWorker;
    }

}
