package it.polimi.ingsw.network.message;

public class Message_WelcomeFirst extends Message{

    private final Target target = Target.WELCOME_FIRST;

    public Target getTarget(){
        return target;
    }

    @Override
    public void printMessage(){
        System.out.println("Sei il primo giocatore ad unirsi alla lobby.\nScegli quanti giocatori avrà la partita. Scrivi 2 oppure 3: ");
    }

}
