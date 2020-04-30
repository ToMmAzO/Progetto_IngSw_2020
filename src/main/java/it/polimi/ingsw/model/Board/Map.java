package it.polimi.ingsw.model.Board;

import it.polimi.ingsw.model.Workers.Worker;

public class Map {

    private static Map board = null;
    private final Cell[][] map;

    public Map(){
        map = new Cell[5][5];
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                map[i][j] = new Cell();
            }
        }
        board = this;
    }

    public static Map getInstance(){
        return board;
    }

    public void setCellBlockType(int row, int column, BlockType block){
        map[row][column].setBlockType(block);
    }

    public BlockType getCellBlockType(int row, int column){
        return map[row][column].getBlockType();
    }

    public void setWorkerInCell(int row, int column, Worker worker){
        map[row][column].setWorkerPresence(worker);
    }

    public Worker getWorkerInCell(int row, int column){
        return map[row][column].getWorkerPresence();
    }

    public void deleteWorkerInCell(Worker worker){
        map[worker.getCoordX()][worker.getCoordY()].setWorkerPresence(null);
    }

    public boolean noWorkerHere(int row, int column){
        return map[row][column].getWorkerPresence() == null;
    }

    public void print(){
        System.out.printf("   |   %d  |   %d  |   %d  |   %d  |   %d  |\n", 0, 1, 2, 3, 4);
        System.out.println("---|------|------|------|------|------|");
        for (int i = 0; i < 5; i++){
            System.out.printf("%d  |", i);
            for (int j = 0; j < 5; j++){
                if(noWorkerHere(i, j)){
                    if(getCellBlockType(i, j) == BlockType.GROUND){
                        System.out.printf("%7c", '|');
                    } else{
                        System.out.printf("%s|", getCellBlockType(i, j).toString());
                    }
                } else{
                    System.out.printf("|%s||", getWorkerInCell(i, j).getIdWorker());
                }
            }
            System.out.println("\n---|------|------|------|------|------|");
        }
    }
}
