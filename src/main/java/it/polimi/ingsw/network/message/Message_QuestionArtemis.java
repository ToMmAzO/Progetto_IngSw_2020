package it.polimi.ingsw.network.message;

public class Message_QuestionArtemis extends Message{

    private final GameState gameState = GameState.QUESTION_ARTEMIS;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.print("Vuoi muovere ancora?/\nRicorda: non puoi tornare alla casella precedente. Scrivi Yes o No: ");
    }

}
