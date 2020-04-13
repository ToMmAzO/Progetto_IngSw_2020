package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.Workers.Worker;

public class Map {

    private static Cell[][] map;

    public Map(){
        map = new Cell[5][5];
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                map[i][j] = new Cell();
            }
        }
    }

    public static void setCellBlockType(int row, int column, BlockType block){
        map[row][column].setBlockType(block);
    }

    public static BlockType getCellBlockType(int row, int column){
        return map[row][column].getBlockType();
    }

    public static void setWorkerInCell(int row, int column, Worker worker){
        map[row][column].setWorkerPresence(worker);
    }

    public static Worker getWorkerInCell(int row, int column){
        return map[row][column].getWorkerPresence();
    }

    public static void deleteWorkerInCell(Worker worker){
        map[worker.getCoordX()][worker.getCoordY()].setWorkerPresence(null);
    }

    public static boolean noWorkerHere(int row, int column){
        if (map[row][column].getWorkerPresence() == null) return true;
        else return false;
    }

    public static boolean isAcceptable(int i, int j){
        if((i >= 0 && i <= 4) && (j >= 0 && j <= 4))
            return true;
        else
            return false;
    }

}
