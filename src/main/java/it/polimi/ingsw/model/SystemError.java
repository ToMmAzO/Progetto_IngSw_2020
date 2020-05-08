package it.polimi.ingsw.model;

import it.polimi.ingsw.observer.Observable;

public class SystemError extends Observable<String> {

    private static SystemError systemError = null;

    public String formatError = "Formato scorreto.";
    public String contentError = "Input scorreto.";







    public SystemError(){
        systemError = this;
    }

    public static SystemError getInstance(){
        return systemError;
    }

    public void serverMessage(String error){
        notify(error);
    }

}
