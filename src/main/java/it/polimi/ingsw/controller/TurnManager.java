package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Workers.Worker;

import java.util.Scanner;

public class TurnManager {

    private static Worker workerSelected;
    static boolean allowHeight, allowHeightPrometheus;
    private static int row, column;
    private static int x, y;             //richiede le due coordinate e gliele ripassa controllando che siano diverse
    private static String answer;
    private static String[] coordString;

    static Scanner scanner = new Scanner(System.in);    //prova

    public static void startTurn(Player player){
        System.out.println("Scegli che worker usare: " + player.getWorkerSelected(1).getIdWorker() +
                " o " + player.getWorkerSelected(2).getIdWorker() + " (scrivi 1 o 2)");
        int selectionWorker = Integer.parseInt((scanner.nextLine()));
        while (selectionWorker != 1 && selectionWorker != 2) {
            System.out.println("Devi inserire 1 oppure 2!");
            selectionWorker = Integer.parseInt((scanner.nextLine()));
        }
        workerSelected = player.getWorkerSelected(selectionWorker);  //DA ERRORE QUA
        //System.out.println("hai selezionato: "+ player.getWorkerSelected(selectionWorker).getIdWorker());


        if(workerSelected.canMove())          //se il worker scelto può muoversi passa a selectAction()
            selectAction(player);
        else {                                //altrimenti controlla: se l'altro worker può muoversi fa muovere l'altro, se nemmeno l'altro può muoversi (entrambi bloccati) --> player eliminato
            System.out.println(player.getNickname() + " NON può muoversi, prendo l'altro Worker"); //workerID
            if (selectionWorker == 1) {
                selectionWorker = 2;
                workerSelected = player.getWorkerSelected((selectionWorker - 1));

            } else {
                selectionWorker = 1;
                workerSelected = player.getWorkerSelected((selectionWorker - 1));
            }


            if (workerSelected.canMove())
                selectAction(player);

            if (!workerSelected.canMove()) {
                System.out.println(player.getNickname() + " NON può muoversi"); //workerID
                GameManager.deletePlayer(player);      //next turn
            }
        }
        workerSelected = null;
    }

    public static void selectAction(Player player){
        switch (player.getGodChoice()) {
            case ARTEMIS:
                System.out.println("Inserisci le cordinate in cui ti vuoi spostare: ");
                coordString = scanner.nextLine().split(",");
                column = Integer.parseInt(coordString[0]);
                row = Integer.parseInt(coordString[1]);
                while (!ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){
                    System.out.println("Inserisci delle coordinate valide!");
                    coordString = scanner.nextLine().split(",");
                    column = Integer.parseInt(coordString[0]);
                    row = Integer.parseInt(coordString[1]);
                }

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

                    if(answer.equals("yes") || answer.equals("Yes")){
                        System.out.println("Inserisci le NUOVE cordinate in cui ti vuoi spostare: ");
                        coordString = scanner.nextLine().split(",");
                        column = Integer.parseInt(coordString[0]);
                        row = Integer.parseInt(coordString[1]);
                        while (!ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){
                            if(x == column && y == row) {
                                System.out.println("Inserisci delle coordinate DIVERSE da quelle di partenza!");
                                coordString = scanner.nextLine().split(",");
                                column = Integer.parseInt(coordString[0]);
                                row = Integer.parseInt(coordString[1]);
                            }else {
                                System.out.println("Inserisci delle coordinate valide!");
                                coordString = scanner.nextLine().split(",");
                                column = Integer.parseInt(coordString[0]);
                                row = Integer.parseInt(coordString[1]);
                            }
                        }
                        workerSelected.changePosition(column, row);
                    }
                }else{
                    System.out.println(player.getNickname() + " NON può muoversi una seconda volta!"); //workerID
                }

                if(workerSelected.canBuild()) {
                    System.out.println("Inserisci le cordinate dove vuoi costruire: ");
                    coordString = scanner.nextLine().split(",");
                    column = Integer.parseInt(coordString[0]);
                    row = Integer.parseInt(coordString[1]);
                    while (!ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)) {
                        System.out.println("Inserisci delle coordinate valide!");
                        coordString = scanner.nextLine().split(",");
                        column = Integer.parseInt(coordString[0]);
                        row = Integer.parseInt(coordString[1]);
                    }
                    workerSelected.buildBlock(column, row);
                }else{
                    System.out.println(player.getNickname() + " NON può costruire!"); //workerID
                }
                break;

            case ATLAS:
                System.out.println("Inserisci le cordinate in cui ti vuoi spostare: ");
                coordString = scanner.nextLine().split(",");
                column = Integer.parseInt(coordString[0]);
                row = Integer.parseInt(coordString[1]);
                while (!ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){
                    System.out.println("Inserisci delle coordinate valide!");
                    coordString = scanner.nextLine().split(",");
                    column = Integer.parseInt(coordString[0]);
                    row = Integer.parseInt(coordString[1]);
                }

                workerSelected.changePosition(column, row);

                if(workerSelected.canBuild()) {
                    System.out.println("Vuoi costruire una CUPOLA? (Yes o No)");
                    answer = scanner.nextLine();
                    while (!answer.equals("yes") && !answer.equals("no") && !answer.equals("Yes") && !answer.equals("No")) {
                        System.out.println("Puoi rispondere solo con yes, Yes, no, No.");
                        answer = scanner.nextLine();
                    }

                    if(answer.equals("yes") || answer.equals("Yes")){
                        System.out.println("Inserisci le cordinate dove vuoi costruire: ");
                        coordString = scanner.nextLine().split(",");
                        column = Integer.parseInt(coordString[0]);
                        row = Integer.parseInt(coordString[1]);
                        while (!ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)) {
                            System.out.println("Inserisci delle coordinate valide!");
                            coordString = scanner.nextLine().split(",");
                            column = Integer.parseInt(coordString[0]);
                            row = Integer.parseInt(coordString[1]);
                        }
                        workerSelected.buildBlock(true, column, row);
                    }else{
                        System.out.println("Inserisci le cordinate dove vuoi costruire: ");
                        coordString = scanner.nextLine().split(",");
                        column = Integer.parseInt(coordString[0]);
                        row = Integer.parseInt(coordString[1]);
                        while (!ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)) {
                            System.out.println("Inserisci delle coordinate valide!");
                            coordString = scanner.nextLine().split(",");
                            column = Integer.parseInt(coordString[0]);
                            row = Integer.parseInt(coordString[1]);
                        }
                        workerSelected.buildBlock(false, column, row);
                    }
                }else{
                    System.out.println(player.getNickname() + " NON può costruire!"); //workerID
                }

                break;

            case DEMETER:
                System.out.println("Inserisci le cordinate in cui ti vuoi spostare: ");
                coordString = scanner.nextLine().split(",");
                column = Integer.parseInt(coordString[0]);
                row = Integer.parseInt(coordString[1]);
                while (!ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){
                    System.out.println("Inserisci delle coordinate valide!");
                    coordString = scanner.nextLine().split(",");
                    column = Integer.parseInt(coordString[0]);
                    row = Integer.parseInt(coordString[1]);
                }

                workerSelected.changePosition(column, row);

                if(workerSelected.canBuild()) {
                    System.out.println("Inserisci le cordinate dove vuoi costruire: ");
                    coordString = scanner.nextLine().split(",");
                    column = Integer.parseInt(coordString[0]);
                    row = Integer.parseInt(coordString[1]);
                    while (!ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)) {
                        System.out.println("Inserisci delle coordinate valide!");
                        coordString = scanner.nextLine().split(",");
                        column = Integer.parseInt(coordString[0]);
                        row = Integer.parseInt(coordString[1]);
                    }
                    x = column;
                    y = row;

                    workerSelected.buildBlock(column, row);

                    System.out.println("Vuoi costruire ancora? (Yes o No)");
                    answer = scanner.nextLine();
                    while (!answer.equals("yes") && !answer.equals("no") && !answer.equals("Yes") && !answer.equals("No")) {
                        System.out.println("Puoi rispondere solo con yes, Yes, no, No.");
                        answer = scanner.nextLine();
                    }

                    if(answer.equals("yes") || answer.equals("Yes")){
                        System.out.println("Inserisci le NUOVE cordinate in cui vuoi costruire: ");
                        coordString = scanner.nextLine().split(",");
                        column = Integer.parseInt(coordString[0]);
                        row = Integer.parseInt(coordString[1]);
                        while (!ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){
                            if(x == column && y == row) {
                                System.out.println("Inserisci delle coordinate DIVERSE da quelle dove hai costruito prima!");
                                coordString = scanner.nextLine().split(",");
                                column = Integer.parseInt(coordString[0]);
                                row = Integer.parseInt(coordString[1]);
                            }else {
                                System.out.println("Inserisci delle coordinate valide!");
                                coordString = scanner.nextLine().split(",");
                                column = Integer.parseInt(coordString[0]);
                                row = Integer.parseInt(coordString[1]);
                            }
                        }
                        workerSelected.buildBlock(column, row);
                    }
                }else{
                    System.out.println(player.getNickname() + " NON può costruire!"); //workerID
                }
                break;

            case HEPHAESTUS:
                System.out.println("Inserisci le cordinate in cui ti vuoi spostare: ");
                coordString = scanner.nextLine().split(",");
                column = Integer.parseInt(coordString[0]);
                row = Integer.parseInt(coordString[1]);
                while (!ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){
                    System.out.println("Inserisci delle coordinate valide!");
                    coordString = scanner.nextLine().split(",");
                    column = Integer.parseInt(coordString[0]);
                    row = Integer.parseInt(coordString[1]);
                }

                workerSelected.changePosition(column, row);

                if(workerSelected.canBuild()) {
                    System.out.println("Vuoi costruire 2 volte? (Yes o No)");
                    answer = scanner.nextLine();
                    while (!answer.equals("yes") && !answer.equals("no") && !answer.equals("Yes") && !answer.equals("No")) {
                        System.out.println("Puoi rispondere solo con yes, Yes, no, No.");
                        answer = scanner.nextLine();
                    }

                    if(answer.equals("yes") || answer.equals("Yes")){
                        if(workerSelected.canBuild(true)) {
                            System.out.println("Inserisci le cordinate in cui vuoi costruire: ");
                            coordString = scanner.nextLine().split(",");
                            column = Integer.parseInt(coordString[0]);
                            row = Integer.parseInt(coordString[1]);
                            while (!ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)) {
                                System.out.println("Inserisci delle coordinate valide!");
                                coordString = scanner.nextLine().split(",");
                                column = Integer.parseInt(coordString[0]);
                                row = Integer.parseInt(coordString[1]);
                            }
                            workerSelected.buildBlock(true, column, row);
                        }else{
                            System.out.println(player.getNickname() + " NON può costruire 2 volte!"); //workerID
                            System.out.println("Inserisci le cordinate in cui vuoi costruire: ");
                            coordString = scanner.nextLine().split(",");
                            column = Integer.parseInt(coordString[0]);
                            row = Integer.parseInt(coordString[1]);
                            while (!ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){
                                System.out.println("Inserisci delle coordinate valide!");
                                coordString = scanner.nextLine().split(",");
                                column = Integer.parseInt(coordString[0]);
                                row = Integer.parseInt(coordString[1]);
                            }
                            workerSelected.buildBlock(false, column, row);
                        }
                    }else{
                        System.out.println("Inserisci le cordinate in cui vuoi costruire: ");
                        coordString = scanner.nextLine().split(",");
                        column = Integer.parseInt(coordString[0]);
                        row = Integer.parseInt(coordString[1]);
                        while (!ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){
                            System.out.println("Inserisci delle coordinate valide!");
                            coordString = scanner.nextLine().split(",");
                            column = Integer.parseInt(coordString[0]);
                            row = Integer.parseInt(coordString[1]);
                        }
                        workerSelected.buildBlock(false, column, row);
                    }
                }else{
                    System.out.println(player.getNickname() + " NON può costruire!"); //workerID
                }
                break;

            case PROMETHEUS:
                if (workerSelected.canBuild()) {
                    System.out.println("Vuoi costruire prima di muoverti? (Yes o No)");
                    answer = scanner.nextLine();
                    while (!answer.equals("yes") && !answer.equals("no") && !answer.equals("Yes") && !answer.equals("No")) {
                        System.out.println("Puoi rispondere solo con yes, Yes, no, No.");
                        answer = scanner.nextLine();
                    }

                    if (answer.equals("yes") || answer.equals("Yes")) {
                        setAllowHeightPrometheus(false);
                        if (workerSelected.canMove()) {
                            System.out.println("Inserisci le cordinate in cui vuoi costruire: ");
                            coordString = scanner.nextLine().split(",");
                            column = Integer.parseInt(coordString[0]);
                            row = Integer.parseInt(coordString[1]);
                            while (!ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)) {
                                System.out.println("Inserisci delle coordinate valide!");
                                coordString = scanner.nextLine().split(",");
                                column = Integer.parseInt(coordString[0]);
                                row = Integer.parseInt(coordString[1]);
                            }
                            workerSelected.buildBlock(column, row);
                        } else {
                            System.out.println(player.getNickname() + " NON può costruire prima!"); //workerID
                            setAllowHeightPrometheus(true);
                        }
                    }else{
                        setAllowHeightPrometheus(true);
                    }
                }else {
                    System.out.println(player.getNickname() + " NON può costruire!"); //workerID
                }

                System.out.println("Inserisci le cordinate in cui ti vuoi spostare: ");
                coordString = scanner.nextLine().split(",");
                column = Integer.parseInt(coordString[0]);
                row = Integer.parseInt(coordString[1]);
                while (!ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){
                    System.out.println("Inserisci delle coordinate valide!");
                    coordString = scanner.nextLine().split(",");
                    column = Integer.parseInt(coordString[0]);
                    row = Integer.parseInt(coordString[1]);
                }

                workerSelected.changePosition(column, row);

                if(workerSelected.canBuild()) {
                    System.out.println("Inserisci le cordinate dove vuoi costruire: ");
                    coordString = scanner.nextLine().split(",");
                    column = Integer.parseInt(coordString[0]);
                    row = Integer.parseInt(coordString[1]);
                    while (!ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)) {
                        System.out.println("Inserisci delle coordinate valide!");
                        coordString = scanner.nextLine().split(",");
                        column = Integer.parseInt(coordString[0]);
                        row = Integer.parseInt(coordString[1]);
                    }
                    workerSelected.buildBlock(column, row);
                }else{
                    System.out.println(player.getNickname() + " NON può costruire!"); //workerID
                }
                break;

            default:    //APOLLO, ATHENA, ATLAS, MINOTAUR, PAN
                System.out.println("Inserisci le cordinate in cui ti vuoi spostare: ");
                coordString = scanner.nextLine().split(",");
                column = Integer.parseInt(coordString[0]);
                row = Integer.parseInt(coordString[1]);
                while (!ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)){
                    System.out.println("Inserisci delle coordinate valide!");
                    coordString = scanner.nextLine().split(",");
                    column = Integer.parseInt(coordString[0]);
                    row = Integer.parseInt(coordString[1]);
                }

                workerSelected.changePosition(column, row);

                if(workerSelected.canBuild()) {
                    System.out.println("Inserisci le cordinate dove vuoi costruire: ");
                    coordString = scanner.nextLine().split(",");
                    column = Integer.parseInt(coordString[0]);
                    row = Integer.parseInt(coordString[1]);
                    while (!ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), column, row)) {
                        System.out.println("Inserisci delle coordinate valide!");
                        coordString = scanner.nextLine().split(",");
                        column = Integer.parseInt(coordString[0]);
                        row = Integer.parseInt(coordString[1]);
                    }
                    workerSelected.buildBlock(column, row);
                }else{
                    System.out.println(player.getNickname() + " NON può costruire!"); //workerID
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