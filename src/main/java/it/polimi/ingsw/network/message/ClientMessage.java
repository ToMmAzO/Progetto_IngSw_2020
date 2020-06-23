package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

/**
 * This class is used to add a target to simple string messages received from client, according to the game state memorized.
 */
public class ClientMessage {

    private GameState gameState;
    private String content;

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
