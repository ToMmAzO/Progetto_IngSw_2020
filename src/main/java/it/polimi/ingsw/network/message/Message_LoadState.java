package it.polimi.ingsw.network.message;

public class Message_LoadState extends Message{

    private final GameState gameState = GameState.LOAD_STATE;
    private GameState toLoad;

    public GameState getGameState(){
        return gameState;
    }

    public GameState getToLoad(){
        return toLoad;
    }
    public void setToLoad(GameState toLoad) { this.toLoad = toLoad;}

    @Override
    public void printMessage(){
        System.out.println("Attendi.");
    }

}