package it.polimi.ingsw.network.message;

public class Message_Wait extends Message{

    private final GameState gameState = GameState.WAIT;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("Attendi!");
    }

}
