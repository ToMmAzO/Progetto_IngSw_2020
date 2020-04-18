package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Workers.Worker;

import java.util.Scanner;

public class ActionManager {

    public static boolean isAround(int row, int column, int newRow, int newColumn){
        return newRow >= row - 1 && newRow <= row + 1 && newColumn >= column - 1 && newColumn <= column + 1;
    }
    
    public static boolean validCoords(int i, int j){
        return (i >= 0 && i <= 4) && (j >= 0 && j <= 4);
    }

    public static int[] insertCoordinateMovement(Worker w){
        Scanner scanner = new Scanner(System.in);
        String[] coordString;
        int coordRow;
        int coordColumn;
        while(true) {
            try{
                System.out.print("Inserisci delle coordinate x, y: ");
                coordString = scanner.nextLine().replace(" ", "").split(",");
                coordRow = Integer.parseInt(coordString[0]);
                coordColumn = Integer.parseInt(coordString[1]);
                if (validCoords(coordRow, coordColumn)) {
                    if(isAround(w.getCoordX(), w.getCoordY(), coordRow, coordColumn)) {
                        if(w.getCoordX() != coordRow || w.getCoordY() != coordColumn) {
                            if (Map.noWorkerHere(coordRow, coordColumn)) {
                                if (Map.getCellBlockType(coordRow, coordColumn) != BlockType.CUPOLA) {
                                    if (TurnManager.cannotGoUp()) {//AGGIUNGERE POTERE PROMETHEUS
                                        if (Map.getCellBlockType(coordRow, coordColumn).getAbbreviation() <= w.getCoordZ()){
                                            return new int[]{coordRow, coordColumn};
                                        } else{
                                            System.out.print("ATTENTO, c'è attivo il potere di ATHENA! ");
                                        }
                                    } else{
                                        if (Map.getCellBlockType(coordRow, coordColumn).getAbbreviation() <= w.getCoordZ() + 1){
                                            return new int[]{coordRow, coordColumn};
                                        } else{
                                            System.out.print("ATTENTO, non puoi salire di due livelli! ");
                                        }
                                    }
                                } else{
                                    System.out.print("ATTENTO, c'è una cupola! ");
                                }
                            } else{
                                System.out.print("ATTENTO, c'è un altro worker! ");
                            }
                        } else{
                            System.out.print("ATTENTO, ci sei già tu! ");
                        }
                    } else{
                        System.out.print("Devi selezionare una delle 8 caselle intorno a worker! ");
                    }
                } else{
                    System.out.print("Puoi inserire solo interi da 0 a 4! ");
                }
            } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                System.out.print("Formato Input scorretto! ");
            }
        }
    }

    public static int[] insertCoordinateConstruction(Worker w){
        Scanner scanner = new Scanner(System.in);
        String[] coordString;
        int coordRow;
        int coordColumn;
        while(true) {
            try {
                System.out.print("Inserisci delle coordinate x, y: ");
                coordString = scanner.nextLine().replace(" ", "").split(",");
                coordRow = Integer.parseInt(coordString[0]);
                coordColumn = Integer.parseInt(coordString[1]);
                if (validCoords(coordRow, coordColumn)) {
                    if (isAround(w.getCoordX(), w.getCoordY(), coordRow, coordColumn)) {
                        if (w.getCoordX() != coordRow || w.getCoordY() != coordColumn) {
                            if (Map.noWorkerHere(coordRow, coordColumn)) {
                                if (Map.getCellBlockType(coordRow, coordColumn) != BlockType.CUPOLA) {
                                    return new int[]{coordRow, coordColumn};
                                } else{
                                    System.out.print("ATTENTO, c'è una cupola! ");
                                }
                            } else {
                                System.out.print("ATTENTO, c'è un altro worker! ");
                            }
                        } else{
                            System.out.print("ATTENTO, ci sei già tu! ");
                        }
                    } else{
                        System.out.print("Devi selezionare una delle 8 caselle intorno a worker! ");
                    }
                } else{
                    System.out.print("Puoi inserire solo interi da 0 a 4! ");
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
