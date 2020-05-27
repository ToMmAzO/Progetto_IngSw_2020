package it.polimi.ingsw.model.game;

import it.polimi.ingsw.observer.Observable;

public class SystemMessage extends Observable<String> {

    private static SystemMessage systemError;
    public String contentError = "Input scorreto!";
    public String cardNotAvailable = "Carta non più disponibile!";
    public String workerPresence = "È già presente un lavoratore quì!";
    public String exceedMap = "Puoi inserire solo interi da 0 a 4!";
    public String youLose = "Hai perso!";
    public String youWin = "Congratulazioni, hai vinto la partita!";
    public String gameInvalidation = "\nPARTITA ANNULLATA! Un giocatore ha abbandonato il gioco.";
    public String powerAthena = "ATTENTO, c'è attivo il potere di ATHENA!";
    public String powerPrometheus = "ATTENTO, hai appena costruito e sei PROMETHEUS!";
    public String doubleGoUp = "ATTENTO, non puoi salire di due livelli!";
    public String cupolaPresence = "ATTENTO, c'è una cupola!";
    public String yourWorker = "ATTENTO, non è un worker avversario!";
    public String cantPush = "ATTENTO, non puoi spingere quel worker!";
    public String yourPosition = "ATTENTO, ci sei già tu!";
    public String distantCell = "Devi selezionare una delle 8 caselle intorno al worker!";
    public String youHephaestus = "ATTENTO, sei Hephaestus!";
    public String cantMove = "Non può muovere.";
    public String cantBuild = "Non può costruire.";
    public String cantDoNothing = "Nemmeno l'altro worker può muoversi. ";
    public String cantComeBack = "ATTENTO, non puoi tornare indietro!";
    public String cantDoubleBuild = "Non può costruire due blocchi.";
    public String cantRebuildDemeter = "ATTENTO, non puoi ricostruire nello stesso punto di prima!";
    public String cantRebuildHestia = "ATTENTO, non puoi costruire sul perimetro!";

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
