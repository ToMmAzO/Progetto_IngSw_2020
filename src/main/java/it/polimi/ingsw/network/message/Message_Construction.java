package it.polimi.ingsw.network.message;

public class Message_Construction extends Message{

    private final GameState gameState = GameState.CONSTRUCTION;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("COSTRUZIONE: \nInserisci delle cordinate (x, y):");
    }

}
