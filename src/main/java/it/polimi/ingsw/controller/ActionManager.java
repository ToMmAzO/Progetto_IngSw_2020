package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Workers.Worker;

import java.util.Scanner;

public class ActionManager {

    public static boolean movementManager(int row, int column, int newRow, int newColumn){
        return newRow >= row - 1 && newRow <= row + 1 && newColumn >= column - 1 && newColumn <= column + 1;
    }

    public static boolean buildManager(int row, int column, int buildRow, int buildColumn){
        return buildRow >= row - 1 && buildRow <= row + 1 && buildColumn >= column - 1 && buildColumn <= column + 1;
    }

    public static boolean control(String k){
        return k.equals("0") || k.equals("1") || k.equals("2") || k.equals("3") || k.equals("4");
    }

    public static boolean isAcceptable(int i, int j){
        return (i >= 0 && i <= 4) && (j >= 0 && j <= 4);
    }

    public static String[] insertCoordinateMovement(Worker w){      //fare controllo su nuova posizione
        String[] coord;
        Scanner scanner = new Scanner(System.in);
        boolean ok = false;

        System.out.println("Inserisci le cordinate: (x, y)");
        coord = scanner.nextLine().split(",");

        while(ok) {

            if (control(coord[0]) && control(coord[1])) {
                if(movementManager(w.getCoordX(), w.getCoordY(), Integer.parseInt(coord[0]), Integer.parseInt(coord[1]))) {
                    if(w.getCoordX() != Integer.parseInt(coord[0]) && w.getCoordY() != Integer.parseInt(coord[1])) {
                        if (Map.noWorkerHere(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]))) {
                            if (Map.getCellBlockType(Integer.parseInt(coord[0]), Integer.parseInt(coord[1])) != BlockType.CUPOLA) {
                                if (TurnManager.cannotGoUp()) {         //AGGIUNGERE POTERE PROMETHEUS
                                    if (Map.getCellBlockType(Integer.parseInt(coord[0]), Integer.parseInt(coord[1])).getAbbreviation() <= w.getCoordZ()){
                                        ok = true;
                                    }else{
                                        System.out.println("ATTENTO, c'è attivo il potere di ATHENA!");
                                        System.out.println("Inserisci le cordinate: (x, y)");
                                        coord = scanner.nextLine().split(",");
                                    }
                                }else{
                                    if (Map.getCellBlockType(Integer.parseInt(coord[0]), Integer.parseInt(coord[1])).getAbbreviation() <= w.getCoordZ() + 1){
                                        ok = true;
                                    }else{
                                        System.out.println("ATTENTO, non puoi salire di due livelli!");
                                        System.out.println("Inserisci le cordinate: (x, y)");
                                        coord = scanner.nextLine().split(",");
                                    }
                                }
                            } else {
                                System.out.println("ATTENTO, c'è una cupola!");
                                System.out.println("Inserisci le cordinate: (x, y)");
                                coord = scanner.nextLine().split(",");
                            }
                        } else {
                            System.out.println("ATTENTO, c'è un altro worker!");
                            System.out.println("Inserisci le cordinate: (x, y)");
                            coord = scanner.nextLine().split(",");
                        }
                    }else{
                        System.out.println("ATTENTO, ci sei tu!");
                        System.out.println("Inserisci le cordinate: (x, y)");
                        coord = scanner.nextLine().split(",");
                    }
                }else{
                    System.out.println("Inserisci delle coordinate nell'intorno dell'worker!");
                    System.out.println("Inserisci le cordinate: (x, y)");
                    coord = scanner.nextLine().split(",");
                }
            } else {
                System.out.println("Puoi inserire solo interi da 0 a 4!");
                System.out.println("Inserisci le cordinate: (x, y)");
                coord = scanner.nextLine().split(",");
            }
        }

        return coord;
    }

    public static String[] insertCoordinateConstruction(Worker w){      //fare controllo su nuova posizione
        String[] coord;
        Scanner scanner = new Scanner(System.in);
        boolean ok = false;

        System.out.println("Inserisci le cordinate: (x, y)");
        coord = scanner.nextLine().split(",");

        while(ok) {
            if (control(coord[0]) && control(coord[1])) {
                if(movementManager(w.getCoordX(), w.getCoordY(), Integer.parseInt(coord[0]), Integer.parseInt(coord[1]))) {
                    if(w.getCoordX() != Integer.parseInt(coord[0]) && w.getCoordY() != Integer.parseInt(coord[1])) {
                        if (Map.noWorkerHere(Integer.parseInt(coord[0]), Integer.parseInt(coord[1]))) {
                            if (Map.getCellBlockType(Integer.parseInt(coord[0]), Integer.parseInt(coord[1])) != BlockType.CUPOLA) {
                                ok = true;
                            } else {
                                System.out.println("ATTENTO, c'è una cupola!");
                                System.out.println("Inserisci le cordinate: (x, y)");
                                coord = scanner.nextLine().split(",");
                            }
                        } else {
                            System.out.println("ATTENTO, c'è un altro worker!");
                            System.out.println("Inserisci le cordinate: (x, y)");
                            coord = scanner.nextLine().split(",");
                        }
                    }else{
                        System.out.println("ATTENTO, ci sei tu!");
                        System.out.println("Inserisci le cordinate: (x, y)");
                        coord = scanner.nextLine().split(",");
                    }
                }else{
                    System.out.println("Inserisci delle coordinate nell'intorno dell'worker!");
                    System.out.println("Inserisci le cordinate: (x, y)");
                    coord = scanner.nextLine().split(",");
                }
            } else {
                System.out.println("Puoi inserire solo interi da 0 a 4!");
                System.out.println("Inserisci le cordinate: (x, y)");
                coord = scanner.nextLine().split(",");
            }
        }

        return coord;
    }

    public static String yesOrNo(){
        String answer;
        Scanner scanner = new Scanner(System.in);

        answer = scanner.nextLine();
        while (!answer.equals("yes") && !answer.equals("no") && !answer.equals("Yes") && !answer.equals("No")) {
            System.out.println("Puoi rispondere solo con yes, Yes, no, No.");
            answer = scanner.nextLine();
        }

        return answer;
    }

}
