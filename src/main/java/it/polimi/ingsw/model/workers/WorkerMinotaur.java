package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;

public class WorkerMinotaur extends Worker {

    public WorkerMinotaur(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public boolean canPush(int x, int y) {
        int pushedX = getCoordX() - 2;
        int pushedY;
        for(int i = getCoordX() - 1; i < getCoordX() + 2; i++){
            pushedY = getCoordY() - 2;
            for(int j = getCoordY() - 1; j < getCoordY() + 2; j++){
                if((x == i) && (y == j)){
                    if(ActionManager.getInstance().validCoords(pushedX, pushedY) && Map.getInstance().noWorkerHere(pushedX, pushedY) && Map.getInstance().getCellBlockType(pushedX, pushedY) != BlockType.CUPOLA){
                        if(TurnManager.getInstance().cannotGoUp()){
                            if(Map.getInstance().getCellBlockType(pushedX, pushedY).getAbbreviation() <= Map.getInstance().getWorkerInCell(x,y).getCoordZ()){
                                return true;
                            }
                        } else{
                            if(Map.getInstance().getCellBlockType(pushedX, pushedY).getAbbreviation() <= Map.getInstance().getWorkerInCell(x,y).getCoordZ() + 1){
                                return true;
                            }
                        }
                    }
                }
                pushedY = pushedY + 2;
            }
            pushedX = pushedX + 2;
        }
        return false;
    }


    @Override
    public boolean canMove() {
        for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
            for (int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {
                if (!(i == getCoordX() && j == getCoordY()) && ActionManager.getInstance().validCoords(i, j) && Map.getInstance().getCellBlockType(i, j) != BlockType.CUPOLA){
                    if (TurnManager.getInstance().cannotGoUp()) {
                        if (Map.getInstance().getCellBlockType(i, j).getAbbreviation() <= getCoordZ()) {
                            if (Map.getInstance().noWorkerHere(i, j)) {
                                return true;
                            }
                        }
                        if (!Map.getInstance().noWorkerHere(i, j) && (!Map.getInstance().getWorkerInCell(i, j).getIdWorker().substring(0, 3).equals(getIdWorker().substring(0, 3)))) {
                            if (canPush(i, j)) {
                                return true;
                            }
                        }
                    } else{
                        if (Map.getInstance().getCellBlockType(i, j).getAbbreviation() <= getCoordZ() + 1) {
                            if (Map.getInstance().noWorkerHere(i, j)) {
                                return true;
                            }
                            if (!Map.getInstance().noWorkerHere(i, j) && (!Map.getInstance().getWorkerInCell(i, j).getIdWorker().substring(0, 3).equals(getIdWorker().substring(0, 3)))) {
                                if (canPush(i, j)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void changePosition(int newX, int newY){
        if(Map.getInstance().noWorkerHere(newX, newY)) {
            super.changePosition(newX, newY);
        } else {
            int pushedX = getCoordX() - 2;
            int pushedY;
            for (int i = getCoordX() - 1; i < getCoordX() + 2; i++) {
                pushedY = getCoordY() - 2;
                for (int j = getCoordY() - 1; j < getCoordY() + 2; j++) {
                    if ((newX == i) && (newY == j)) {
                        Map.getInstance().deleteWorkerInCell(this);

                        Map.getInstance().getWorkerInCell(newX, newY).setCoordX(pushedX);
                        Map.getInstance().getWorkerInCell(newX, newY).setCoordY(pushedY);
                        Map.getInstance().getWorkerInCell(newX, newY).setCoordZ(Map.getInstance().getCellBlockType(pushedX, pushedY).getAbbreviation());

                        Map.getInstance().setWorkerInCell(pushedX, pushedY, Map.getInstance().getWorkerInCell(newX, newY));
                        Map.getInstance().setWorkerInCell(newX, newY, this);

                        setCoordX(newX);
                        setCoordY(newY);
                        setCoordZ(Map.getInstance().getCellBlockType(newX, newY).getAbbreviation());
                    }
                    pushedY = pushedY + 2;
                }
                pushedX = pushedX + 2;
            }
        }
    }
}
