package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_WaitPlayers extends Message{

    private final GameState gameState = GameState.WAIT_PLAYERS;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public String getMessage(){
        return "Waiting for other players!\n";
    }

}
