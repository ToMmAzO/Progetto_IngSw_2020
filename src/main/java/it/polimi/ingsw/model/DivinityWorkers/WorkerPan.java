package it.polimi.ingsw.model.DivinityWorkers;


import it.polimi.ingsw.model.BlockType;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Worker;


public class WorkerPan extends Worker {
    @Override
    public void changePosition(int newX, int newY) {
        super.changePosition(newX, newY);       //G cosa fa super?
        if((coordZ == 3 && Map[newX][newY].Cell == BlockType.BLOCK1) || (coordZ == 3 && Map[newX][newY].Cell == BlockType.EMPTY) || (coordZ == 2 && Map[newX][newY].Cell == BlockType.EMPTY)){
            coordX = newX;
            coordY = newY;
            coordZ = Map[newX][newY].getCell.blockType;
            Game.endGame();
        }else{
            coordX = newX;
            coordY = newY;
            coordZ = Map[newX][newY].getCell.blockType;
        }
    }
}
