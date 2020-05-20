package it.polimi.ingsw.network.message;

public class Message_WaitCardChoice extends Message{

    private final GameState gameState = GameState.WAIT_CARD_CHOICE;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println();
    }

}
