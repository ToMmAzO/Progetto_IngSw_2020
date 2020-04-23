package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;

public abstract class Worker {

    private final String idWorker;
    private int coordX, coordY, coordZ;

    public Worker(String idWorker, int coordX, int coordY) {
        this.idWorker = idWorker;
        this.coordX = coordX;
        this.coordY = coordY;
        this.coordZ = 0;
        Map.setWorkerInCell(coordX, coordY, this);
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

    public boolean canMove() {
        for (int i = coordX - 1; i <= coordX + 1; i++) {
            for (int j = coordY - 1; j <= coordY + 1; j++) {
                if (!(i == coordX && j == coordY) && ActionManager.validCoords(i, j) && Map.noWorkerHere(i, j) && Map.getCellBlockType(i, j) != BlockType.CUPOLA){
                    if(TurnManager.cannotGoUp()){
                        if(Map.getCellBlockType(i, j).getAbbreviation() <= coordZ) {
                            return true;
                        }
                    }else{
                        if(Map.getCellBlockType(i, j).getAbbreviation() <= coordZ + 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean canPush(int x, int y){       //MINOTAUR (ActionManager)
        return true;
    }

    public boolean canMove(int x, int y) {      //ARTEMIS
        return true;
    }

    public void changePosition(int newX, int newY){
            Map.deleteWorkerInCell(this);
            coordX = newX;
            coordY = newY;
            coordZ = Map.getCellBlockType(newX, newY).getAbbreviation();
            Map.setWorkerInCell(newX, newY, this);
    }

    public boolean canBuild(){
        for (int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {
            for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
                if (!(i == getCoordX() && j == getCoordY()) && ActionManager.validCoords(i, j) && Map.noWorkerHere(i, j) && Map.getCellBlockType(i, j) != BlockType.CUPOLA) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canBuild(int x, int y) {      //DEMETER
        return true;
    }

    public boolean canBuild(boolean buildAgain) {       //HEPHAESTUS
        return true;
    }

    public void buildBlock(int buildX, int buildY){
        if(Map.getCellBlockType(buildX, buildY) == BlockType.GROUND){
            Map.setCellBlockType(buildX, buildY, BlockType.BLOCK1);
        }else if(Map.getCellBlockType(buildX, buildY) == BlockType.BLOCK1){
            Map.setCellBlockType(buildX, buildY, BlockType.BLOCK2);
        }else if(Map.getCellBlockType(buildX, buildY) == BlockType.BLOCK2){
            Map.setCellBlockType(buildX, buildY, BlockType.BLOCK3);
        }else if(Map.getCellBlockType(buildX, buildY) == BlockType.BLOCK3){
            Map.setCellBlockType(buildX, buildY, BlockType.CUPOLA);
        }
    }

    public void buildBlock(boolean build, int buildX, int buildY) {         //HEPHAESTUS e ATLAS

    }

}
