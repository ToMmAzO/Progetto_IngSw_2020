package it.polimi.ingsw.network.message;

public class Message_SecondMove extends Message{

    private final GameState gameState = GameState.SECOND_MOVE;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("Inserisci delle cordinate (x, y).");
    }

}
