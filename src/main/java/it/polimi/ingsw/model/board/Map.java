package it.polimi.ingsw.model.board;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.workers.Worker;
import it.polimi.ingsw.observer.Observable;

public class Map extends Observable<Player> {

    private static Map board;
    private final Cell[][] map;
    private int numberOfCompleteTurrets = 0;

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

    protected Cell[][] getMap(){
        return map;
    }

    public void setCellBlockType(int row, int column, BlockType block){
        map[row][column].setBlockType(block);
        notify(GameManager.getInstance().getCurrPlayer());
    }

    public BlockType getCellBlockType(int row, int column){
        return map[row][column].getBlockType();
    }

    public void setWorkerInCell(int row, int column, Worker worker){
        map[row][column].setWorkerPresence(worker);
        notify(GameManager.getInstance().getCurrPlayer());
    }

    public Worker getWorkerInCell(int row, int column){
        return map[row][column].getWorkerPresence();
    }

    /**
     * from the beginning of the game, each time a tower is completed (a dome is placed over a block3) the
     * numberOfCompleteTurrets is raised by 1
     *
     *    *needed for the Chronus' God power*
     */
    public void addNumberOfCompleteTurrets(){
        numberOfCompleteTurrets++;
    }

    public int getNumberOfCompleteTurrets(){
        return numberOfCompleteTurrets;
    }

    /**
     * given a worker, this method deletes it from the cell where it is
     *
     * @param worker the worker you want to be deleted
     */
    public void deleteWorkerInCell(Worker worker){
        map[worker.getCoordX()][worker.getCoordY()].setWorkerPresence(null);
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

}
