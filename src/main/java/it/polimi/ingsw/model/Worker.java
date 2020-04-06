package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;

public abstract class Worker {

    private String idWorker;
    private int coordX, coordY, coordZ;
    private Color color;    //inutile?

    public Worker(Player p, int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.coordZ = 0;
        this.color = p.getColor();
        //Map.setWorkerInCell(coordRow, coordColumn, this);
    }

    public void setIdWorker(String idWorker) {
        this.idWorker = idWorker;
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

    public Color getColor() {
        return color;
    }

    public boolean canMove(){
        for(int i = coordX - 1; i < coordX + 1; i++){
            for(int j = coordY - 1; j < coordY + 1; j++) {
                if ((i == coordX && j == coordY) || !Map.noWorkerHere(i, j) || (!GameManager.getAllowHeight() && Map.getCellBlockType(i, j).getAbbreviation() > getCoordZ()) || Map.getCellBlockType(i, j).getAbbreviation() >= coordZ + 2 || Map.getCellBlockType(i, j) == BlockType.CUPOLA) {
                        return false;
                }
            }
        }
        return true;
    }

    public void changePosition(int newX, int newY){
        if(coordZ == 2 && Map.getCellBlockType(newX, newY).getAbbreviation() == 3){
            coordX = newX;
            coordY = newY;
            coordZ = Map.getCellBlockType(newX, newY).getAbbreviation();
            //setWorkerInCell NO?
            GameManager.victory();
        }else{
            coordX = newX;
            coordY = newY;
            coordZ = Map.getCellBlockType(newX, newY).getAbbreviation();
        }
    }

    public void changePosition(int newX, int newY, int newNewX, int newNewY){            //solo per sottoclassi se no dava fastidio l'override ARTEMIS

    }

    public boolean canBuild(){
        for(int i = coordX - 1; i < coordX + 1; i++){
            for(int j = coordY - 1; j < coordY + 1; j++) {
                if ((i == coordX && j == coordY) || !Map.noWorkerHere(i, j) || Map.getCellBlockType(i, j) == BlockType.CUPOLA) {
                        return false;
                }
            }
        }
        return true;
    }

    public boolean canBuild(boolean buildAgain) {       //solo per sottoclassi se no dava fastidio l'override
        return true;
    }

    public void buildBlock(int buildX, int buildY){
        if(Map.getCellBlockType(buildX, buildY) == BlockType.EMPTY){
            Map.setCellBlockType(buildX, buildY, BlockType.BLOCK1);
        }else if(Map.getCellBlockType(buildX, buildY) == BlockType.BLOCK1){
            Map.setCellBlockType(buildX, buildY, BlockType.BLOCK2);
        }else if(Map.getCellBlockType(buildX, buildY) == BlockType.BLOCK2){
            Map.setCellBlockType(buildX, buildY, BlockType.BLOCK3);
        }else if(Map.getCellBlockType(buildX, buildY) == BlockType.BLOCK3){
            Map.setCellBlockType(buildX, buildY, BlockType.CUPOLA);
        }
    }

    public void buildBlock(boolean build, int buildX, int buildY) {         //solo per sottoclassi se no dava fastidio l'override PROMETHEUS, HEPHAESTUS e ATLAS

    }

    public void buildBlock(int buildX, int buildY, int buildAgainX, int buildAgainY){       //solo per sottoclassi se no dava fastidio l'override DEMETER

    }

}
