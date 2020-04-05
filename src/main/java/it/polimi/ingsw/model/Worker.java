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

    public int getCoordY() {
        return coordY;
    }

    public int getCoordZ() {
        return coordZ;
    }

    public Color getColor() {
        return color;
    }

    public boolean canMove(Map field){
        for(int i = coordX - 1; i < coordX + 1; i++){
            for(int j = coordY - 1; j < coordY + 1; j++) {
                if ((i != coordX && j != coordY) || field.noWorkerHere(i, j) || field.getCellBlockType(i, j).getAbbreviation() >= coordZ + 2 || field.getCellBlockType(i, j) == BlockType.CUPOLA) {
                        return false;
                }
            }
        }
        return true;
    }

    public void changePosition(Map field, int newX, int newY){
        if(coordZ == 2 && field.getCellBlockType(newX, newY).getAbbreviation() == 3){
            coordX = newX;
            coordY = newY;
            coordZ = field.getCellBlockType(newX, newY).getAbbreviation();
            GameManager.victory();
        }else{
            coordX = newX;
            coordY = newY;
            coordZ = field.getCellBlockType(newX, newY).getAbbreviation();
        }
    }

    public boolean canBuild(Map field){
        for(int i = coordX - 1; i < coordX + 1; i++){
            for(int j = coordY - 1; j < coordY + 1; j++) {
                if ((i != coordX && j != coordY) || field.noWorkerHere(i, j) || field.getCellBlockType(i, j) == BlockType.CUPOLA) {
                        return false;
                }
            }
        }
        return true;
    }

    public void buildBlock(Map field, int buildX, int buildY){
        if(field.getCellBlockType(buildX, buildY) == BlockType.EMPTY){
            field.setCellBlockType(buildX, buildY, BlockType.BLOCK1);
        }else if(field.getCellBlockType(buildX, buildY) == BlockType.BLOCK1){
            field.setCellBlockType(buildX, buildY, BlockType.BLOCK2);
        }else if(field.getCellBlockType(buildX, buildY) == BlockType.BLOCK2){
            field.setCellBlockType(buildX, buildY, BlockType.BLOCK3);
        }else if(field.getCellBlockType(buildX, buildY) == BlockType.BLOCK3){
            field.setCellBlockType(buildX, buildY, BlockType.CUPOLA);
        }
    }

}
