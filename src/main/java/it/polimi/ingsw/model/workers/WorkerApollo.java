package it.polimi.ingsw.model.workers;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;

public class WorkerApollo extends Worker {

    public WorkerApollo(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public boolean canMove() {
        for (int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {
            for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
                if (!(i == getCoordX() && j == getCoordY()) && ActionManager.getInstance().validCoords(i, j) && Map.getInstance().getCellBlockType(i, j) != BlockType.CUPOLA){
                    if(!Map.getInstance().noWorkerHere(i, j) && (!Map.getInstance().getWorkerInCell(i, j).getIdWorker().substring(0, 3).equals(getIdWorker().substring(0, 3)))){
                        if (TurnManager.getInstance().cannotGoUp()) {
                            if (Map.getInstance().getCellBlockType(i, j).getAbbreviation() <= getCoordZ()) {
                                return true;
                            }
                        } else {
                            if (Map.getInstance().getCellBlockType(i, j).getAbbreviation() <= getCoordZ() + 1) {
                                return true;
                            }
                        }
                    }
                    if(Map.getInstance().noWorkerHere(i, j)){
                        if (TurnManager.getInstance().cannotGoUp()) {
                            if (Map.getInstance().getCellBlockType(i, j).getAbbreviation() <= getCoordZ()) {
                                return true;
                            }
                        } else {
                            if (Map.getInstance().getCellBlockType(i, j).getAbbreviation() <= getCoordZ() + 1) {
                                return true;
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
        if(!Map.getInstance().noWorkerHere(newX, newY)){
            int x,y,z;
            x = this.getCoordX();
            y = this.getCoordY();
            z = this.getCoordZ();
            Map.getInstance().deleteWorkerInCell(this);       //rimuove il WorkerApollo selezionato dalla mappa

            Map.getInstance().getWorkerInCell(newX, newY).setCoordX(x);     //prende il worker in (newX, newY) e gli aggiorna le coord con quelle del WorkerApollo che ha chiamato la changePosition
            Map.getInstance().getWorkerInCell(newX, newY).setCoordY(y);
            Map.getInstance().getWorkerInCell(newX, newY).setCoordZ(z);

            Map.getInstance().setWorkerInCell(x, y, Map.getInstance().getWorkerInCell(newX, newY));    //sposta nella mappa la workerPresence dell'altro worker sul posto del WorkerApollo
            //Map.deleteWorkerInCell(Map.getWorkerInCell(newX, newY));     //non necessaria(?) viene sovrascritto con il comando successivo
            Map.getInstance().setWorkerInCell(newX, newY, this);                 //sovrascrive la workerPresence della cella in cui workerApollo vuole spostarsi con WorkerApollo stesso

            setCoordX(newX);           //aggiorna anche le coordinate di WorkerApollo
            setCoordY(newY);
            setCoordZ(Map.getInstance().getCellBlockType(newX, newY).getAbbreviation());
        }else {
            super.changePosition(newX, newY);
        }
    }

}
