package it.polimi.ingsw.network.message;

public class Message_PrebuildPrometheus extends Message{

    private final GameState gameState = GameState.PREBUILD_PROMETHEUS;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.print("Inserisci delle cordinate (x, y): ");
    }

}
