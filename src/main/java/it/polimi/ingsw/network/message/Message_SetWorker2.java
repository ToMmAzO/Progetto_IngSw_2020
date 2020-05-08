package it.polimi.ingsw.network.message;

public class Message_SetWorker2 extends Message{

    private final GameState gameState = GameState.SET_WORKER2;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("Posizionamento del lavoratore 2. Inserisci delle cordinate (x, y):");
    }

}