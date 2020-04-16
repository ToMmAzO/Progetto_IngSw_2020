package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Workers.Worker;
import it.polimi.ingsw.view.View;

import java.util.Scanner;

public class TurnManager {

    private static Worker workerSelected;
    static boolean allowHeight, allowHeightPrometheus;

    static Scanner scanner = new Scanner(System.in);    //prova

    public static boolean startTurn(Player player){
        System.out.println("Scegli che worker usare: " + player.getWorkerSelected(1).getIdWorker() + " o " + player.getWorkerSelected(2).getIdWorker() + " (scrivi 1 o 2)");
        int selectionWorker = Integer.parseInt((scanner.nextLine()));
        while (selectionWorker != 1 && selectionWorker != 2) {
            System.out.println("Devi inserire 1 oppure 2!");
            selectionWorker = Integer.parseInt((scanner.nextLine()));
        }
        workerSelected = player.getWorkerSelected(selectionWorker);
        if(workerSelected.canMove())          //se il worker scelto può muoversi passa a selectAction()
            selectAction(player);
        else {                                //altrimenti controlla: se l'altro worker può muoversi fa muovere l'altro, se nemmeno l'altro può muoversi (entrambi bloccati) --> player eliminato
            System.out.println(workerSelected.getIdWorker() + " NON può muoversi, prendo l'altro Worker");
            if (selectionWorker == 1) {
                selectionWorker ++;
            } else {
                selectionWorker --;
            }
            workerSelected = player.getWorkerSelected((selectionWorker));

            System.out.println("Il worker selezionato è: "+ workerSelected.getIdWorker());
            if (workerSelected.canMove()){
                selectAction(player);
            } else {
                System.out.println(workerSelected.getIdWorker() + " NON può muoversi");
                GameManager.deletePlayer(player);
                return false;
            }
        }
        workerSelected = null;
        return true;
    }

    public static void selectAction(Player player){
        String answer;
        int x, y;
        String[] coordString;

        switch (player.getGodChoice()) {
            case ARTEMIS:
                System.out.println("MOVIMENTO: ");
                coordString =  ActionManager.insertCoordinateMovement(workerSelected);

                x = workerSelected.getCoordX();
                y = workerSelected.getCoordY();

                workerSelected.changePosition(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));

                View.printMap();
                View.printWorkersPositions(player);

                if(workerSelected.canMove(x, y)){
                    System.out.println("Vuoi muovere ancora? (Yes o No)");
                    answer = ActionManager.yesOrNo();

                    if((answer.equals("yes") || answer.equals("Yes"))) {
                        System.out.println("MOVIMENTO: ");
                        coordString = ActionManager.insertCoordinateMovement(workerSelected);
                        while (x == Integer.parseInt(coordString[0]) && y == Integer.parseInt(coordString[1])) {
                            System.out.println("Inserisci delle coordinate DIVERSE da quelle di partenza!");
                            coordString = ActionManager.insertCoordinateMovement(workerSelected);
                        }

                        workerSelected.changePosition(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));

                        View.printMap();
                        View.printWorkersPositions(player);
                    }
                }else{
                    System.out.println(workerSelected.getIdWorker() + " NON può muoversi una seconda volta!");
                }

                if(workerSelected.canBuild()) {
                    System.out.println("COSTRUZIONE: ");
                    coordString =  ActionManager.insertCoordinateConstruction(workerSelected);

                    workerSelected.buildBlock(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                }else{
                    System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
                }
                break;

            case ATLAS:
                System.out.println("MOVIMENTO: ");
                coordString =  ActionManager.insertCoordinateMovement(workerSelected);

                workerSelected.changePosition(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));

                View.printMap();
                View.printWorkersPositions(player);

                if(workerSelected.canBuild()) {
                    System.out.println("Vuoi costruire una CUPOLA? (Yes o No)");
                    answer = ActionManager.yesOrNo();

                    if(answer.equals("yes") || answer.equals("Yes")){
                        System.out.println("COSTRUZIONE: ");
                        coordString =  ActionManager.insertCoordinateConstruction(workerSelected);

                        workerSelected.buildBlock(true, Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                    }else{
                        System.out.println("COSTRUZIONE: ");
                        coordString =  ActionManager.insertCoordinateConstruction(workerSelected);

                        workerSelected.buildBlock(false, Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                    }
                }else{
                    System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
                }

                break;

            case DEMETER:
                System.out.println("MOVIMENTO: ");
                coordString =  ActionManager.insertCoordinateMovement(workerSelected);

                workerSelected.changePosition(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));

                View.printMap();
                View.printWorkersPositions(player);

                if(workerSelected.canBuild()) {
                    System.out.println("COSTRUZIONE: ");
                    coordString =  ActionManager.insertCoordinateConstruction(workerSelected);

                    x = Integer.parseInt(coordString[0]);
                    y = Integer.parseInt(coordString[1]);

                    workerSelected.buildBlock(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));

                    View.printMap();
                    View.printWorkersPositions(player);

                    System.out.println("Vuoi costruire ancora? (Yes o No)");
                    answer = ActionManager.yesOrNo();

                    if(answer.equals("yes") || answer.equals("Yes")){
                        System.out.println("COSTRUZIONE: ");
                        coordString =  ActionManager.insertCoordinateConstruction(workerSelected);
                        while (x == Integer.parseInt(coordString[0]) && y == Integer.parseInt(coordString[1])) {
                            System.out.println("Inserisci delle coordinate DIVERSE da quelle dove hai costruito prima!");
                            coordString = ActionManager.insertCoordinateConstruction(workerSelected);
                        }

                        workerSelected.buildBlock(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                    }
                }else{
                    System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
                }
                break;

            case HEPHAESTUS:
                System.out.println("MOVIMENTO: ");
                coordString =  ActionManager.insertCoordinateMovement(workerSelected);

                workerSelected.changePosition(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));

                View.printMap();
                View.printWorkersPositions(player);

                if(workerSelected.canBuild()) {
                    System.out.println("Vuoi costruire 2 volte? (Yes o No)");
                    answer = ActionManager.yesOrNo();

                    if ((answer.equals("yes") || answer.equals("Yes")) && workerSelected.canBuild(true)) {
                        System.out.println("COSTRUZIONE: ");
                        coordString = ActionManager.insertCoordinateConstruction(workerSelected);

                        workerSelected.buildBlock(true, Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                    } else {
                        if (!workerSelected.canBuild(true)) {
                            System.out.println(workerSelected.getIdWorker() + " NON può costruire 2 volte!");
                        }

                        System.out.println("COSTRUZIONE: ");
                        coordString = ActionManager.insertCoordinateConstruction(workerSelected);

                        workerSelected.buildBlock(false, Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                    }
                } else {
                    System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
                }
                break;

            case PROMETHEUS:
                if (workerSelected.canBuild()) {
                    System.out.println("Vuoi costruire prima di muoverti? (Yes o No)");
                    answer = ActionManager.yesOrNo();

                    if (answer.equals("yes") || answer.equals("Yes")) {
                        System.out.println("COSTRUZIONE: ");
                        coordString =  ActionManager.insertCoordinateConstruction(workerSelected);

                        setAllowHeightPrometheus(false);
                        if (workerSelected.canMove(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]))) {
                            workerSelected.buildBlock(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                            View.printMap();
                            View.printWorkersPositions(player);
                        } else {
                            System.out.println(workerSelected.getIdWorker() + " NON può costruire prima!");
                            setAllowHeightPrometheus(true);
                        }
                    }else{
                        setAllowHeightPrometheus(true);
                    }
                }else {
                    System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
                }

                System.out.println("MOVIMENTO: ");
                coordString =  ActionManager.insertCoordinateMovement(workerSelected);

                workerSelected.changePosition(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));

                View.printMap();
                View.printWorkersPositions(player);

                if(workerSelected.canBuild()) {
                    System.out.println("COSTRUZIONE: ");
                    coordString =  ActionManager.insertCoordinateConstruction(workerSelected);

                    workerSelected.buildBlock(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                }else{
                    System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
                }
                break;

            default:    //APOLLO, ATHENA, ATLAS, MINOTAUR, PAN
                System.out.println("MOVIMENTO: ");
                coordString =  ActionManager.insertCoordinateMovement(workerSelected);

                workerSelected.changePosition(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));

                View.printMap();
                View.printWorkersPositions(player);

                if(workerSelected.canBuild()) {
                    System.out.println("COSTRUZIONE: ");
                    coordString =  ActionManager.insertCoordinateMovement(workerSelected);

                    workerSelected.buildBlock(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                }else{
                    System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
                }
                break;
        }
    }

    public static void setAllowHeight(boolean allowHeight) {
        TurnManager.allowHeight = allowHeight;
    }

    public static boolean cannotGoUp() {
        return !allowHeight;
    }

    public static void setAllowHeightPrometheus(boolean allowHeightPrometheus) {
        TurnManager.allowHeightPrometheus = allowHeightPrometheus;
    }

    public static boolean getAllowHeightPrometheus() {
        return allowHeightPrometheus;
    }
}