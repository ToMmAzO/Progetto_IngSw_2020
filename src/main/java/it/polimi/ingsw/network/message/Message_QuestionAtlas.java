package it.polimi.ingsw.network.message;

public class Message_QuestionAtlas extends Message{

    private final GameState gameState = GameState.QUESTION_ATLAS;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.print("Vuoi costruire una CUPOLA? Scrivi Yes o No: ");
    }

}
