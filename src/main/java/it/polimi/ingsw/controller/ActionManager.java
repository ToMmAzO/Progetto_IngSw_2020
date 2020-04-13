package it.polimi.ingsw.controller;

public class ActionManager {

    public static boolean movementManager(int row, int column, int newRow, int newColumn){
        if (newRow >= 0) if (newRow <= 4) if (newColumn >= 0) if (newColumn <= 4) if (newRow >= row - 1)
            if (newRow <= row + 1) if (newColumn >= column - 1) if (newColumn <= column + 1) return true;
        return false;
    }

    public static boolean buildManager(int row, int column, int buildRow, int buildColumn){
        return buildRow >= 0 && buildRow <= 4 && buildColumn >= 0 && buildColumn <= 4 && buildRow >= row - 1 && buildRow <= row + 1 && buildColumn >= column - 1 && buildColumn <= column + 1;
    }

}
