package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;

public class WorkerApollo extends Worker {

    public WorkerApollo(String idWorker, int coordX, int coordY) {
        super(idWorker, coordX, coordY);
    }

    @Override
    public boolean canMove() {
        for (int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {           //se no funziona --> while
            for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
                if (!(i == getCoordX() && j == getCoordY()) && ActionManager.isAcceptable(i, j) && Map.getCellBlockType(i, j) != BlockType.CUPOLA){
                    if(TurnManager.cannotGoUp()){
                        if(Map.getCellBlockType(i, j).getAbbreviation() <= getCoordZ()) {
                            return true;
                        }
                    }else{
                        if(Map.getCellBlockType(i, j).getAbbreviation() <= getCoordZ() + 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void changePosition(int newX, int newY){
        if(!Map.noWorkerHere(newX, newY)){
            int x,y,z;
            x = this.getCoordX();
            y = this.getCoordY();
            z = this.getCoordZ();
            Map.deleteWorkerInCell(this);       //rimuove il WorkerApollo selezionato dalla mappa

            Map.getWorkerInCell(newX, newY).setCoordX(x);     //prende il worker in (newX, newY) e gli aggiorna le coord con quelle del WorkerApollo che ha chiamato la changePosition
            Map.getWorkerInCell(newX, newY).setCoordY(y);
            Map.getWorkerInCell(newX, newY).setCoordZ(z);

            Map.setWorkerInCell(x, y, Map.getWorkerInCell(newX, newY));    //sposta nella mappa la workerPresence dell'altro worker sul posto del WorkerApollo
            //Map.deleteWorkerInCell(Map.getWorkerInCell(newX, newY));     //non necessaria(?) viene sovrascritto con il comando successivo
            Map.setWorkerInCell(newX, newY, this);                 //sovrascrive la workerPresence della cella in cui workerApollo vuole spostarsi con WorkerApollo stesso

            setCoordX(newX);           //aggiorna anche le coordinate di WorkerApollo
            setCoordY(newY);
            setCoordZ(Map.getCellBlockType(newX, newY).getAbbreviation());

        }else {
            super.changePosition(newX, newY);
        }
    }

}
