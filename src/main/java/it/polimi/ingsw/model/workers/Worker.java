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
     * Initialize the worker's coordinates and insert him in the map
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

    /**
     * Verify that the worker can move (Artemis) or build (Demeter) again
     *
     * @param x row coordinate where the worker can't move/build
     * @param y column coordinate where the worker can't move/build
     *
     * @return true if can do again
     */
    public boolean canDoAgain(int x, int y){
        return false;
    }

    /**
     * Verify that the worker Minotaur can push the worker in that cell
     *
     * @param x row coordinate of the worker that must be pushed
     * @param y column coordinate of the worker that must be pushed
     *
     * @return true if can push the other worker
     */
    public boolean canPush(int x, int y){
        return false;
    }

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

    /**
     * Build a cupola (Atlas) or 2 blocks (Hephaestus) in the the map
     *
     * @param buildX row coordinate of the building
     * @param buildY column coordinate of the building
     */
    public void specialBuild(int buildX, int buildY){}

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

}
