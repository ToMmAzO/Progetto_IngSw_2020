package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumerations.GameState;

public class Message_WelcomeFirst extends Message{

    private final GameState gameState = GameState.WELCOME_FIRST;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("Sei il primo giocatore ad unirsi alla lobby.\nScegli quanti giocatori avrà la partita. Scrivi 2 oppure 3: ");
    }

}
