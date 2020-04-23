package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.model.Cards.God;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Workers.Worker;

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

    public static int[] insertCoordinateMovement(Worker w, God g){
        Scanner scanner = new Scanner(System.in);
        String[] coordString;      //prova
        int coordX, coordY;

        System.out.println("MOVIMENTO: ");
        while(true){
            try{
                System.out.print("Inserisci delle coordinate x, y: ");
                coordString = scanner.nextLine().replace(" ", "").split(",");
                coordX = Integer.parseInt(coordString[0]);
                coordY = Integer.parseInt(coordString[1]);
                if(ActionManager.verifyCoordinateMovement(w, g, coordX, coordY)){
                    return new int[]{coordX, coordY};
                }
            } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                System.out.print("Formato Input scorretto! ");
            }
        }
    }

    public static int[] insertCoordinateConstruction(Worker w){
        Scanner scanner = new Scanner(System.in);
        String[] coordString;      //prova
        int coordX, coordY;

        System.out.println("COSTRUZIONE: ");
        while(true){
            try{
                System.out.print("Inserisci delle coordinate x, y: ");
                coordString = scanner.nextLine().replace(" ", "").split(",");
                coordX = Integer.parseInt(coordString[0]);
                coordY = Integer.parseInt(coordString[1]);
                if(ActionManager.verifyCoordinateConstruction(w, coordX, coordY)){
                    return new int[]{coordX, coordY};
                }
            } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                System.out.print("Formato Input scorretto! ");
            }
        }
    }

    public static String yesOrNo(){
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine().toLowerCase().replace(" ", "");
        while (!answer.equals("yes") && !answer.equals("no")){
            System.out.println("Puoi rispondere solo con yes o no!");
            answer = scanner.nextLine();
        }
        return answer;
    }

}
