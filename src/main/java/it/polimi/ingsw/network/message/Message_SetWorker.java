package it.polimi.ingsw.network.message;

public class Message_SetWorker extends Message{

    private final GameState gameState = GameState.SET_WORKER;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("Posizionamento il lavoratore. Inserisci delle cordinate (x, y):");
    }

}
