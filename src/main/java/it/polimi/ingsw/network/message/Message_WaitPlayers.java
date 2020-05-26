package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_WaitPlayers extends Message{

    private final GameState gameState = GameState.WAIT_PLAYERS;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("Attendi gli altri giocatori!");
    }

}
