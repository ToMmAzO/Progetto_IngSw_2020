package it.polimi.ingsw.network.message;

public class Message_Error extends Message{

    private final GameState gameState = GameState.ERROR;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("Errore! Stato di gioc non riconosciuto");
    }

}
