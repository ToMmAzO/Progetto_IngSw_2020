package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.observer.Observable;

import java.io.Serializable;

public abstract class Worker extends Observable<Player> implements Serializable {

    private final String idWorker;
    private int coordX, coordY, coordZ;

    public Worker(String idWorker, int coordX, int coordY) {
        this.idWorker = idWorker;
        this.coordX = coordX;
        this.coordY = coordY;
        this.coordZ = 0;
        Map.getInstance().setWorkerInCell(coordX, coordY, this);
        Player[] players = GameManager.getInstance().getPlayersInGame();
        for(Player p: players){
            this.addObserver(GameManager.getInstance().getPlayerConnection(p).getViewSocket().createChangeInMap());
        }
        notify(GameManager.getInstance().getCurrPlayer());
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

    public boolean canPush(int x, int y){       //MINOTAUR (ActionManager)
        return true;
    }

    public boolean canMove(int x, int y) {      //ARTEMIS
        return true;
    }

    public void changePosition(int newX, int newY){
        Map.getInstance().deleteWorkerInCell(this);
        coordX = newX;
        coordY = newY;
        coordZ = Map.getInstance().getCellBlockType(newX, newY).getAbbreviation();
        Map.getInstance().setWorkerInCell(newX, newY, this);
        notify(GameManager.getInstance().getCurrPlayer());
    }

    public boolean canBuild(){
        for (int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {
            for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
                if (!(i == getCoordX() && j == getCoordY()) && ActionManager.getInstance().validCoords(i, j) && Map.getInstance().noWorkerHere(i, j) && Map.getInstance().getCellBlockType(i, j) != BlockType.CUPOLA) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canBuild(int x, int y) {      //DEMETER
        return true;
    }

    public boolean canBuild(boolean buildTwoTimes) {       //HEPHAESTUS
        return true;
    }

    public void buildBlock(int buildX, int buildY){
        if(Map.getInstance().getCellBlockType(buildX, buildY) == BlockType.GROUND){
            Map.getInstance().setCellBlockType(buildX, buildY, BlockType.BLOCK1);
        }else if(Map.getInstance().getCellBlockType(buildX, buildY) == BlockType.BLOCK1){
            Map.getInstance().setCellBlockType(buildX, buildY, BlockType.BLOCK2);
        }else if(Map.getInstance().getCellBlockType(buildX, buildY) == BlockType.BLOCK2){
            Map.getInstance().setCellBlockType(buildX, buildY, BlockType.BLOCK3);
        }else if(Map.getInstance().getCellBlockType(buildX, buildY) == BlockType.BLOCK3){
            Map.getInstance().setCellBlockType(buildX, buildY, BlockType.CUPOLA);
        }
        notify(GameManager.getInstance().getCurrPlayer());
    }

    public void buildBlock(boolean build, int buildX, int buildY){}

}
