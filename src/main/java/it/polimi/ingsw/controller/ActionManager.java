package it.polimi.ingsw.controller;

public class ActionManager {

    public static boolean movementManager(int row, int column, int newRow, int newColumn){
        return newRow >= 0 && newRow <= 4 && newColumn >= 0 && newColumn <= 4 && newRow >= row - 1 && newRow <= row + 1 && newColumn >= column - 1 && newColumn <= column + 1;
    }

    public static boolean buildManager(int row, int column, int buildRow, int buildColumn){
        return buildRow >= 0 && buildRow <= 4 && buildColumn >= 0 && buildColumn <= 4 && buildRow >= row - 1 && buildRow <= row + 1 && buildColumn >= column - 1 && buildColumn <= column + 1;
    }

}
