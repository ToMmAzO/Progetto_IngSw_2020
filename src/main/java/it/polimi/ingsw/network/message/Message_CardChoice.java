package it.polimi.ingsw.network.message;

public class Message_CardChoice extends Message{

    private final GameState gameState = GameState.CARD_CHOICE;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.print("Scegli il numero di una delle carte ancora disponibili: ");
    }

}
