package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Workers.Worker;

import java.util.Scanner;

public class ActionManager {

    public static boolean movementManager(int row, int column, int newRow, int newColumn){
        return newRow >= 0 && newRow <= 4 && newColumn >= 0 && newColumn <= 4 && newRow >= row - 1 && newRow <= row + 1 && newColumn >= column - 1 && newColumn <= column + 1;
    }

    public static boolean buildManager(int row, int column, int buildRow, int buildColumn){
        return buildRow >= 0 && buildRow <= 4 && buildColumn >= 0 && buildColumn <= 4 && buildRow >= row - 1 && buildRow <= row + 1 && buildColumn >= column - 1 && buildColumn <= column + 1;
    }

    public static String[] insertCoordinate(Worker w){      //fare controllo su nuova posizione
        String[] coord;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Inserisci le cordinate: (x, y)");
        coord = scanner.nextLine().split(",");
        while (!movementManager(w.getCoordX(), w.getCoordY(), Integer.parseInt(coord[0]), Integer.parseInt(coord[1]))){
            System.out.println("Inserisci delle coordinate valide!");
            coord = scanner.nextLine().split(",");
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
