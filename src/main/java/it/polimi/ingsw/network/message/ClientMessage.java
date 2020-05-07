package it.polimi.ingsw.network.message;

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
