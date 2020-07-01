package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.workers.Worker;

import java.io.Serializable;

public class MapCopy implements Serializable {

    private final Cell[][] map;

    /**
     * create an exact copy of the map (wich is Singleton on the Server) in order to send it to the Clients
     * Being only a copy it doesn't create problems even if modified by the Client
     */
    public MapCopy(){
        map = Map.getInstance().getMap();
    }

    public BlockType getCellBlockType(int row, int column){
        return map[row][column].getBlockType();
    }

    public Worker getWorkerInCell(int row, int column){
        return map[row][column].getWorkerPresence();
    }

    /**
     * controls if there's a worker in the block placed in row,column on the map
     *
     * @param row row of the cell to controll on the map
     * @param column column of the cell to controll on the map
     * @return 'true' if the cell is free (there's no worker in it) else 'false'
     */
    public boolean noWorkerHere(int row, int column){
        return map[row][column].getWorkerPresence() == null;
    }

    /**
     * used by the Cli to print the map grid
     */
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
