package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_WelcomeFirst extends Message{

    private final GameState gameState = GameState.WELCOME_FIRST;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public String getMessage(){
        return "You are the first player to join the lobby.\nChoose how many players will play the game. Write 2 or 3: ";
    }

}
