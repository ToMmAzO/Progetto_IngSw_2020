package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.Worker;

public class Map {

    private static Cell[][] map;

    /*
    public static void main(String[] args){
        Map m = new Map();
        printMap();
        System.out.println();
        setCellBlockType(4, 2, BlockType.BLOCK1);
        printMap();
    }
    */

    public Map(){
        map = new Cell[5][5];
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                map[i][j] = new Cell();
            }
        }
    }

    public static void setCellBlockType(int row, int column, BlockType block){
        map[row][column].setBlock(block);
    }

    public static BlockType getCellBlockType(int row, int column){
        return map[row][column].getBlock();
    }

    public static void setWorkerInCell(int row, int column, Worker worker){
        map[row][column].setWorkerPresence(worker);
    }

    public static Worker getWorkerInCell(int row, int column){
        return map[row][column].getWorkerPresence();
    }

    public static Boolean noWorkerHere(int row, int column){
        return map[row][column].getWorkerPresence() == null;
    }

    public static void printMap(){
        System.out.printf("%9d%8d%8d%8d%8d\n\n", 0, 1, 2, 3, 4);
        for (int i = 0; i < 5; i++){
            System.out.printf("%d  ", i);
            for (int j = 0; j < 5; j++){
                System.out.printf("%8s", map[i][j].getBlock().toString());
            }
            System.out.println("\n");
        }
    }
    
}
