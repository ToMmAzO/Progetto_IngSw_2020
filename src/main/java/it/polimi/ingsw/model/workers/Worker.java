package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;

import java.io.Serializable;

public abstract class Worker implements Serializable {

    private final String idWorker;
    private int coordX, coordY, coordZ;


    /**
     * Insert the worker, only for the first time, in the map
     *
     * @param idWorker worker name
     * @param coordX row coordinate of the worker in the map
     * @param coordY column coordinate of the worker in the map
     */
    public Worker(String idWorker, int coordX, int coordY) {
        this.idWorker = idWorker;
        this.coordX = coordX;
        this.coordY = coordY;
        this.coordZ = 0;
        Map.getInstance().setWorkerInCell(coordX, coordY, this);
    }

    public String getIdWorker() {
        return idWorker;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public int getCoordZ() {
        return coordZ;
    }

    public void setCoordZ(int coordZ) {
        this.coordZ = coordZ;
    }


    /**
     * Verify that the worker can move according to the rules of the game
     *
     * @return true if can move
     */
    public boolean canMove() {
        for (int i = coordX - 1; i <= coordX + 1; i++) {
            for (int j = coordY - 1; j <= coordY + 1; j++) {
                if (!(i == coordX && j == coordY) && ActionManager.getInstance().validCoords(i, j) && Map.getInstance().noWorkerHere(i, j) && Map.getInstance().getCellBlockType(i, j) != BlockType.CUPOLA){
                    if(TurnManager.getInstance().cannotGoUp()){
                        if(Map.getInstance().getCellBlockType(i, j).getAbbreviation() <= coordZ) {
                            return true;
                        }
                    }else{
                        if(Map.getInstance().getCellBlockType(i, j).getAbbreviation() <= coordZ + 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Verify that the worker can build (only for Apollo, Hephaestus, Hestia, Prometheus)
     *
     * @return true if can build
     */
    public boolean canBuild(){
        for (int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {
            for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
                if (!(i == getCoordX() && j == getCoordY()) && ActionManager.getInstance().validCoords(i, j) &&
                        Map.getInstance().noWorkerHere(i, j) && Map.getInstance().getCellBlockType(i, j) != BlockType.CUPOLA) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Change the worker position with the two new coordinates
     *
     * @param newX new row coordinate of the worker in the map
     * @param newY new column coordinate of the worker in the map
     */
    public void changePosition(int newX, int newY){
        Map.getInstance().deleteWorkerInCell(this);
        coordX = newX;
        coordY = newY;
        coordZ = Map.getInstance().getCellBlockType(newX, newY).getAbbreviation();
        Map.getInstance().setWorkerInCell(newX, newY, this);
    }

    public boolean canDoAgain(int x, int y){
        return false;
    }//Artemis e Demeter

    public boolean canPush(int x, int y){
        return false;
    }//Minotaur

    /**
     * Build a block in the the map
     *
     * @param buildX row coordinate of the building
     * @param buildY column coordinate of the building
     */
    public void buildBlock(int buildX, int buildY){
        if(Map.getInstance().getCellBlockType(buildX, buildY) == BlockType.GROUND){
            Map.getInstance().setCellBlockType(buildX, buildY, BlockType.BLOCK1);
        }else if(Map.getInstance().getCellBlockType(buildX, buildY) == BlockType.BLOCK1){
            Map.getInstance().setCellBlockType(buildX, buildY, BlockType.BLOCK2);
        }else if(Map.getInstance().getCellBlockType(buildX, buildY) == BlockType.BLOCK2){
            Map.getInstance().setCellBlockType(buildX, buildY, BlockType.BLOCK3);
        }else if(Map.getInstance().getCellBlockType(buildX, buildY) == BlockType.BLOCK3){
            Map.getInstance().setCellBlockType(buildX, buildY, BlockType.CUPOLA);
            Map.getInstance().addNumberOfCompleteTurrets();
        }
    }

    public void specialBuild(int buildX, int buildY){}//Hephaestus e Atlas

}
