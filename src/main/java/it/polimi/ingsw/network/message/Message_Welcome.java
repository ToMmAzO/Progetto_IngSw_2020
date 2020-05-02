package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumerations.GameState;

public class Message_Welcome extends Message{

    private final GameState gameState = GameState.WELCOME;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("Attendi il tuo turno per scegliere la carta divinità.");
    }

}
