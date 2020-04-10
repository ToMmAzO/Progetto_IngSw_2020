package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

import java.util.Scanner;

public class TurnManager {

    private static Worker workerSelected;
    static boolean allowHeight, allowHeightPrometheus;
    private static int row, column;
    private static boolean canMove;
    private static int x, y;             //richiede le due coordinate e gliele ripassa controllando che siano diverse
    private static String answer;

    static Scanner scanner = new Scanner(System.in);    //prova

    public static void setColumn(int column) {
        TurnManager.column = column;
    }

    public static void setRow(int row) {
        TurnManager.row = row;
    }

    public static void startTurn(Player player){
        System.out.println("Scegli che worker usare: " + player.getNickname() + " o " + player.getNickname() + " (1 o 2)"); //workerID
        int selectionWorker = Integer.parseInt((scanner.nextLine()));
        while (selectionWorker != 1 && selectionWorker != 2) {
            System.out.println("Devi inserire 1 oppure 2!");
            selectionWorker = Integer.parseInt((scanner.nextLine()));
        }
        workerSelected = player.getWorkerSelected(selectionWorker);
        if(!workerSelected.canMove()){
            System.out.println(player.getNickname() + " NON può muoversi, prendo l'altro Worker"); //workerID
            if(selectionWorker + 1 == 3){       //BRUTTO --> selectionWorker = 2
                workerSelected = player.getWorkerSelected((selectionWorker - 1));
            }else{
                workerSelected = player.getWorkerSelected((selectionWorker + 1));
            }

            if(!workerSelected.canMove()){
                System.out.println(player.getNickname() + " NON può muoversi"); //workerID
                GameManager.deletePlayer(player);      //next turn
            }else{
                selectAction(player);
            }
        }else{
            selectAction(player);
        }

        workerSelected = null;
    }

    public static void selectAction(Player player){
        canMove = false;

        System.out.println("Inserisci le cordinate in cui ti vuoi spostare: ");
        String[] coordString = scanner.nextLine().split(",");
        column = Integer.parseInt(coordString[0]);
        row = Integer.parseInt(coordString[1]);
        while (canMove){
            if (ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){
                canMove = true;
            } else{
                System.out.println("Inserisci delle coordinate valide!");
                coordString = scanner.nextLine().split(",");
                column = Integer.parseInt(coordString[0]);
                row = Integer.parseInt(coordString[1]);
            }
        }

        switch (player.getGodChoice()) {
            case ARTEMIS:
                x = workerSelected.getCoordX();
                y = workerSelected.getCoordY();

                workerSelected.changePosition(column, row);

                if(workerSelected.canMove()) {
                    System.out.println("Vuoi muovere ancora? (Yes o No)");
                     answer = scanner.nextLine();
                    while (!answer.equals("yes") && !answer.equals("no") && !answer.equals("Yes") && !answer.equals("No")) {
                        System.out.println("Puoi rispondere solo con yes, Yes, no, No.");
                        answer = scanner.nextLine();
                    }
                }else{
                    System.out.println(player.getNickname() + " NON può muoversi una seconda volta!"); //workerID
                    answer = "no";
                }

                if(answer.equals("yes") || answer.equals("Yes")){
                    canMove = false;

                    System.out.println("Inserisci le NUOVE cordinate in cui ti vuoi spostare: ");
                    coordString = scanner.nextLine().split(",");
                    column = Integer.parseInt(coordString[0]);
                    row = Integer.parseInt(coordString[1]);
                    while (canMove){
                        if (ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){
                            if(x != column && y != row) {
                                canMove = true;
                            } else {
                                System.out.println("Inserisci delle coordinate DIVERSE da quelle di partenza!");
                                coordString = scanner.nextLine().split(",");
                                column = Integer.parseInt(coordString[0]);
                                row = Integer.parseInt(coordString[1]);
                            }
                        } else{
                            System.out.println("Inserisci delle coordinate valide!");
                            coordString = scanner.nextLine().split(",");
                            column = Integer.parseInt(coordString[0]);
                            row = Integer.parseInt(coordString[1]);
                        }
                    }

                    workerSelected.changePosition(column, row);
                }

                if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){
                    workerSelected.canBuild();
                    workerSelected.buildBlock(column, row);
                }
                break;

            case ATLAS:
                if(ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)) {
                    workerSelected.canMove();
                    workerSelected.changePosition(column, row);
                }

                if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){
                    workerSelected.canBuild();
                    workerSelected.buildBlock(yes, column, row);
                }
                break;

            case DEMETER:
                if(ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)) {
                    workerSelected.canMove();
                    workerSelected.changePosition(column, row);
                }

                if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){
                    x = column;
                    y = row;
                    workerSelected.canBuild();
                    workerSelected.buildBlock(column, row);
                }

                if(yes){
                    if(x != column && y != row) {
                        if (ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)) {    //sposta sopra + bello
                            workerSelected.canBuild();
                            workerSelected.buildBlock(column, row);
                        }
                    }else{
                        //ERRORE
                    }
                }
                break;

            case HEPHAESTUS:
                if(ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)) {
                    workerSelected.canMove();
                    workerSelected.changePosition(column, row);
                }

                if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){
                    workerSelected.canBuild(yes);
                    workerSelected.buildBlock(yes, column, row);
                }
                break;
            case PROMETHEUS:
                if(yes){
                    if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){    //sposta sopra + bello
                        workerSelected.canBuild();
                        workerSelected.buildBlock(column, row);
                        setAllowHeightPrometheus(false);
                    }
                }

                if(ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)) {
                    workerSelected.canMove();
                    workerSelected.changePosition(column, row);
                }

                if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){
                    workerSelected.canBuild();
                    workerSelected.buildBlock(column, row);
                    setAllowHeightPrometheus(true);
                }
                break;

            default:    //APOLLO, ATHENA, ATLAS, MINOTAUR, PAN
                if(ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)) {
                    workerSelected.canMove();
                    workerSelected.changePosition(column, row);
                }

                if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){
                    workerSelected.canBuild();
                    workerSelected.buildBlock(column, row);
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