package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Workers.Worker;
import it.polimi.ingsw.view.View;

import java.util.Scanner;

public class TurnManager {

    private static Worker workerSelected;
    static boolean allowHeight, allowHeightPrometheus;

    public static boolean startTurn(Player player){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Scegli che worker usare: " + player.getWorkerSelected(1).getIdWorker() +
                " oppure " + player.getWorkerSelected(2).getIdWorker() + ". ");
        int selectionWorker = 0;
        while (selectionWorker != 1 && selectionWorker != 2){
            try{
                System.out.print("Scrivi 1 oppure 2: ");
                selectionWorker= Integer.parseInt((scanner.nextLine()));
            } catch(IllegalArgumentException e){
                System.out.print("Formato Input scorretto! ");
            }
        }
        workerSelected = player.getWorkerSelected(selectionWorker);
        if(workerSelected.canMove()){
            selectAction(player);
        } else{
            System.out.println(workerSelected.getIdWorker() + " non può muoversi! Seleziono l'altro Worker.");
            if (selectionWorker == 1){
                selectionWorker ++;
            } else{
                selectionWorker --;
            }
            workerSelected = player.getWorkerSelected((selectionWorker));
            if (workerSelected.canMove()){
                System.out.println("Il worker selezionato è: "+ workerSelected.getIdWorker() + ".");
                selectAction(player);
            } else{
                System.out.println("Nemmeno " + workerSelected.getIdWorker() + " può muoversi!");
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
        int[] coords;
        switch (player.getGodChoice()) {
            case ARTEMIS:
                System.out.println("MOVIMENTO: ");
                coords =  ActionManager.insertCoordinateMovement(workerSelected);

                x = workerSelected.getCoordX();
                y = workerSelected.getCoordY();

                workerSelected.changePosition(coords[0], coords[1]);

                View.printMap();
                View.printWorkersPositions(player);

                if(workerSelected.canMove(x, y)){
                    System.out.println("Vuoi muovere ancora? (Yes o No)");
                    answer = ActionManager.yesOrNo();

                    if(answer.equals("yes")) {
                        System.out.println("MOVIMENTO: ");
                        coords = ActionManager.insertCoordinateMovement(workerSelected);
                        while (x == coords[0] && y == coords[1]) {
                            System.out.println("Inserisci delle coordinate DIVERSE da quelle di partenza!");
                            coords = ActionManager.insertCoordinateMovement(workerSelected);
                        }

                        workerSelected.changePosition(coords[0], coords[1]);

                        View.printMap();
                        View.printWorkersPositions(player);
                    }
                }else{
                    System.out.println(workerSelected.getIdWorker() + " NON può muoversi una seconda volta!");
                }

                if(workerSelected.canBuild()) {
                    System.out.println("COSTRUZIONE: ");
                    coords =  ActionManager.insertCoordinateConstruction(workerSelected);

                    workerSelected.buildBlock(coords[0], coords[1]);
                }else{
                    System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
                }
                break;

            case ATLAS:
                System.out.println("MOVIMENTO: ");
                coords =  ActionManager.insertCoordinateMovement(workerSelected);

                workerSelected.changePosition(coords[0], coords[1]);

                View.printMap();
                View.printWorkersPositions(player);

                if(workerSelected.canBuild()) {
                    System.out.println("Vuoi costruire una CUPOLA? (Yes o No)");
                    answer = ActionManager.yesOrNo();

                    if(answer.equals("yes")){
                        System.out.println("COSTRUZIONE: ");
                        coords =  ActionManager.insertCoordinateConstruction(workerSelected);

                        workerSelected.buildBlock(true, coords[0], coords[1]);
                    }else{
                        System.out.println("COSTRUZIONE: ");
                        coords =  ActionManager.insertCoordinateConstruction(workerSelected);

                        workerSelected.buildBlock(false, coords[0], coords[1]);
                    }
                }else{
                    System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
                }

                break;

            case DEMETER:
                System.out.println("MOVIMENTO: ");
                coords =  ActionManager.insertCoordinateMovement(workerSelected);

                workerSelected.changePosition(coords[0], coords[1]);

                View.printMap();
                View.printWorkersPositions(player);

                if(workerSelected.canBuild()) {
                    System.out.println("COSTRUZIONE: ");
                    coords =  ActionManager.insertCoordinateConstruction(workerSelected);

                    x = coords[0];
                    y = coords[1];

                    workerSelected.buildBlock(coords[0], coords[1]);

                    View.printMap();
                    View.printWorkersPositions(player);

                    System.out.println("Vuoi costruire ancora? (Yes o No)");
                    answer = ActionManager.yesOrNo();

                    if(answer.equals("yes")){
                        System.out.println("COSTRUZIONE: ");
                        coords =  ActionManager.insertCoordinateConstruction(workerSelected);
                        while (x == coords[0] && y == coords[1]) {
                            System.out.println("Inserisci delle coordinate DIVERSE da quelle dove hai costruito prima!");
                            coords = ActionManager.insertCoordinateConstruction(workerSelected);
                        }

                        workerSelected.buildBlock(coords[0], coords[1]);
                    }
                }else{
                    System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
                }
                break;

            case HEPHAESTUS:
                System.out.println("MOVIMENTO: ");
                coords =  ActionManager.insertCoordinateMovement(workerSelected);

                workerSelected.changePosition(coords[0], coords[1]);

                View.printMap();
                View.printWorkersPositions(player);

                if(workerSelected.canBuild()) {
                    System.out.println("Vuoi costruire 2 volte? (Yes o No)");
                    answer = ActionManager.yesOrNo();

                    if ((answer.equals("yes")) && workerSelected.canBuild(true)) {
                        System.out.println("COSTRUZIONE: ");
                        coords = ActionManager.insertCoordinateConstruction(workerSelected);

                        workerSelected.buildBlock(true, coords[0], coords[1]);
                    } else {
                        if (!workerSelected.canBuild(true)) {
                            System.out.println(workerSelected.getIdWorker() + " NON può costruire 2 volte!");
                        }

                        System.out.println("COSTRUZIONE: ");
                        coords = ActionManager.insertCoordinateConstruction(workerSelected);

                        workerSelected.buildBlock(false, coords[0], coords[1]);
                    }
                } else {
                    System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
                }
                break;

            case PROMETHEUS:
                if (workerSelected.canBuild()) {
                    System.out.println("Vuoi costruire prima di muoverti? (Yes o No)");
                    answer = ActionManager.yesOrNo();

                    if (answer.equals("yes")) {
                        System.out.println("COSTRUZIONE: ");
                        coords =  ActionManager.insertCoordinateConstruction(workerSelected);

                        setAllowHeightPrometheus(false);
                        if (workerSelected.canMove(coords[0], coords[1])) {
                            workerSelected.buildBlock(coords[0], coords[1]);
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
                coords =  ActionManager.insertCoordinateMovement(workerSelected);

                workerSelected.changePosition(coords[0], coords[1]);

                View.printMap();
                View.printWorkersPositions(player);

                if(workerSelected.canBuild()) {
                    System.out.println("COSTRUZIONE: ");
                    coords =  ActionManager.insertCoordinateConstruction(workerSelected);

                    workerSelected.buildBlock(coords[0], coords[1]);
                }else{
                    System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
                }
                break;

            default:    //APOLLO, ATHENA, ATLAS, MINOTAUR, PAN
                System.out.println("MOVIMENTO: ");
                coords =  ActionManager.insertCoordinateMovement(workerSelected);

                workerSelected.changePosition(coords[0], coords[1]);

                View.printMap();
                View.printWorkersPositions(player);

                if(workerSelected.canBuild()) {
                    System.out.println("COSTRUZIONE: ");
                    coords =  ActionManager.insertCoordinateMovement(workerSelected);

                    workerSelected.buildBlock(coords[0], coords[1]);
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

    public static boolean cannotGoUpPrometheus() {
        return !allowHeightPrometheus;
    }
}