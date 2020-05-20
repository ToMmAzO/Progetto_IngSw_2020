package it.polimi.ingsw.model.game;

import it.polimi.ingsw.observer.Observable;

public class SystemMessage extends Observable<String> {

    private static SystemMessage systemError = null;
    public String contentError = "Input scorreto!\n";
    public String cardNotAvailable = "Carta non disponibile, scegline un'altra: ";
    public String workerPresence = "È già presente un lavoratore quì!\n";
    public String exceedMap = "Puoi inserire solo interi da 0 a 4!\n";
    public String youLose = "Hai perso!\n";
    public String youWin = "Congratulazioni, hai vinto la partita!\n";
    public String gameOver = "GAME OVER!\n";
    public String powerAthena = "ATTENTO, c'è attivo il potere di ATHENA!\n";
    public String powerPrometheus = "ATTENTO, hai appena costruito e sei PROMETHEUS!\n";
    public String doubleGoUp = "ATTENTO, non puoi salire di due livelli!\n";
    public String cupolaPresence = "ATTENTO, c'è una cupola!\n";
    public String yourWorker = "ATTENTO, non è un worker avversario!\n";
    public String cantPush = "ATTENTO, non puoi spingere quel worker!\n";
    public String yourPosition = "ATTENTO, ci sei già tu!\n";
    public String distantCell = "Devi selezionare una delle 8 caselle intorno al worker!";
    public String youHephaestus = "ATTENTO, sei Hephaestus!\n";
    public String cantMove = "Non può muovere.\n";
    public String cantBuild = "Non può costruire.\n";
    public String cantDoNothing = "Nemmeno l'altro worker può muoversi. ";
    public String cantComeBack = "ATTENTO, non puoi tornare indietro!\n";
    public String cantDoubleBuild = "Non può costruire due blocchi.\n";
    public String cantRebuildDemeter = "ATTENTO, non puoi ricostruire nello stesso punto di prima!\n";
    public String cantRebuildHestia = "ATTENTO, non puoi costruire sul perimetro!\n";

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
