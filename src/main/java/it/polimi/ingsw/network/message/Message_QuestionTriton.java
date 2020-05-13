package it.polimi.ingsw.network.message;

public class Message_QuestionTriton extends Message{

    private final GameState gameState = GameState.QUESTION_TRITON;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("Vuoi muoverti ancora? (Yes o No)");
    }

}