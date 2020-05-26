package it.polimi.ingsw.model.board;

import java.io.Serializable;

public class MapCopy implements Serializable {

    private final Cell[][] map;

    public MapCopy(){
        map = Map.getInstance().getMap();
    }

    public void print(){
        System.out.printf("   |   %d  |   %d  |   %d  |   %d  |   %d  |\n", 0, 1, 2, 3, 4);
        System.out.println("---|------|------|------|------|------|");
        for (int i = 0; i < 5; i++){
            System.out.printf("%d  |", i);
            for (int j = 0; j < 5; j++){
                if(map[i][j].getWorkerPresence() == null){
                    if(map[i][j].getBlockType() == BlockType.GROUND){
                        System.out.printf("%7c", '|');
                    } else{
                        System.out.printf("%s|", map[i][j].getBlockType().toString());
                    }
                } else{
                    System.out.printf("|%s||", map[i][j].getWorkerPresence().getIdWorker());
                }
            }
            System.out.println("\n---|------|------|------|------|------|");
        }
    }

}
