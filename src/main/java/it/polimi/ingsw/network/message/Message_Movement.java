package it.polimi.ingsw.network.message;

public class Message_Movement extends Message{

    private final GameState gameState = GameState.MOVEMENT;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("MOVIMENTO: \nInserisci delle cordinate (x, y):");
    }

}
