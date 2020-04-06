package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.Worker;

public class Map {

    private Cell[][] map;

    /*
    public static void main(String[] args){
        Map m = new Map();
        m.printMap();
        System.out.println();
        m.setCellBlockType(4, 2, BlockType.BLOCK1);
        m.printMap();
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

    public void setWorkerInCell(int row, int column, Worker worker){
        map[row][column].setWorkerPresence(worker);
    }

    public static Worker getWorkerInCell(int row, int column){
        return map[row][column].getWorkerPresence();
    }

    public void printMap(){
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                System.out.printf("%10s", map[i][j].getBlock().toString());
            }
            System.out.println();
        }
    }

    public static Boolean noWorkerHere(int row, int column){
        return map[row][column].getWorkerPresence() == null;
    }
}
