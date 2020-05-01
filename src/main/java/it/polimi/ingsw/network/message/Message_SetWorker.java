package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Board.Map;

public class Message_SetWorker extends Message{

    private final Target target = Target.SET_WORKER;

    public Target getTarget(){
        return target;
    }

    @Override
    public void printMessage(){
        Map.getInstance().print();
        System.out.println("Posizionamento lavoratori. Inserisci delle cordinate (x, y):");
    }

}
