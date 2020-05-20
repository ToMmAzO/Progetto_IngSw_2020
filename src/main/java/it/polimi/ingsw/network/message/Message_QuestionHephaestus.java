package it.polimi.ingsw.network.message;

public class Message_QuestionHephaestus extends Message{

    private final GameState gameState = GameState.QUESTION_HEPHAESTUS;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.print("Vuoi costruire 2 blocchi? Scrivi Yes o No: ");
    }

}
