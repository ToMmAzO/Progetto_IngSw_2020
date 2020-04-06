package it.polimi.ingsw.model.DivinityWorkers;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class WorkerArtemis extends Worker {

    public WorkerArtemis(Player p, int coordX, int coordY) {
        super(p, coordX, coordY);
    }

    @Override
    public void changePosition(int newX, int newY, int newNewX, int newNewY){
        if(newNewX == newX && newNewY == newY){         //BRUTTO: meglio nel conroller: chiamata changePosition -> bollean MoveAgain -> chiamata changePosition con controlli sul =
            //ERRORE
        }else{
            super.changePosition(newX, newY);
            super.changePosition(newNewX, newNewY);
        }
    }

}
