package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;

public abstract class Worker {

    private String idWorker;
    private int coordX, coordY, coordZ;

    public Worker(String idWorker, int coordX, int coordY) {
        this.idWorker = idWorker;
        this.coordX = coordX;
        this.coordY = coordY;
        this.coordZ = 0;
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

    public boolean canMove(){
        boolean canMove = false;

        for(int i = coordX - 1; i <= coordX + 1 || canMove; i++){           //se no funziona --> while
            for(int j = coordY - 1; j <= coordY + 1 || canMove; j++) {
                if ((i != coordX && j != coordY) && Map.noWorkerHere(i, j) && (TurnManager.cannotGoUp() && Map.getCellBlockType(i, j).getAbbreviation() <= coordZ) && Map.getCellBlockType(i, j).getAbbreviation() < coordZ + 2 && Map.getCellBlockType(i, j) != BlockType.CUPOLA) {
                        canMove = true;
                }
            }
        }
        return canMove;
    }

    public void changePosition(int newX, int newY){
        if(coordZ == 2 && Map.getCellBlockType(newX, newY).getAbbreviation() == 3){
            coordX = newX;
            coordY = newY;
            coordZ = Map.getCellBlockType(newX, newY).getAbbreviation();
            GameManager.setVictory();
        }else{
            coordX = newX;
            coordY = newY;
            coordZ = Map.getCellBlockType(newX, newY).getAbbreviation();
        }
    }

    public boolean canBuild(){
        boolean canBuild = false;

        for(int i = coordX - 1; i <= coordX + 1 || canBuild; i++){
            for(int j = coordY - 1; j <= coordY + 1 || canBuild; j++) {
                if ((i != coordX && j != coordY) && Map.noWorkerHere(i, j) && Map.getCellBlockType(i, j) != BlockType.CUPOLA) {
                        canBuild = true;
                }
            }
        }
        return canBuild;
    }

    public boolean canBuild(boolean buildAgain) {       //solo per sottoclassi se no dava fastidio l'override HEPHAESTUS
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

    public void buildBlock(boolean build, int buildX, int buildY) {         //solo per sottoclassi se no dava fastidio l'override HEPHAESTUS e ATLAS

    }

}
