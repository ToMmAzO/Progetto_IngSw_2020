package it.polimi.ingsw.model.game;

import it.polimi.ingsw.observer.Observable;

public class SystemMessage extends Observable<String> {

    private static SystemMessage systemError;
    public String contentError = "Wrong input!";
    public String cardNotAvailable = "Card no longer available!";
    public String workerPresence = "There is a Worker here!";
    public String exceedMap = "You can only type integers from 0 to 4!";
    public String powerAthena = "ATTENTION, the ATHENA's power is active!";
    public String powerPrometheus = "ATTENTION, you have just built and you are PROMETHEUS!";
    public String doubleGoUp = "ATTENTION, you cannot go up two levels!";
    public String cupolaPresence = "ATTENTION, there is a Cupola!";
    public String yourWorker = "ATTENTION, he is not an opposing Worker!";
    public String cantPush = "ATTENTION, you cannot push that Worker!";
    public String yourPosition = "ATTENTION, you are already there!";
    public String distantCell = "You must select one of the 8 spaces around the Worker!";
    public String youHephaestus = "ATTENTION, you are Hephaestus!";
    public String cantMove = "He cannot move.";
    public String cantBuild = "He cannot build.";
    public String cantDoNothing = "Neither Workers can move.";
    public String cantComeBack = "ATTENTION, you cannot go back!";
    public String cantDoubleBuild = "You cannot build two blocks.";
    public String cantRebuildDemeter = "ATTENTION, you cannot rebuild in the same place as before!";
    public String cantRebuildHestia = "ATTENTION, you cannot build on the perimeter!";

    public SystemMessage(){
        systemError = this;
    }

    public static SystemMessage getInstance(){
        return systemError;
    }

    public void serverMessage(String error){
        notify(error);
    }

}
