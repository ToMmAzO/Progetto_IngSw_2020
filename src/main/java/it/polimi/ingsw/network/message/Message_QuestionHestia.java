package it.polimi.ingsw.network.message;

public class Message_QuestionHestia extends Message{

    private final GameState gameState = GameState.QUESTION_HESTIA;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("Vuoi costruire ancora? (Yes o No)\n Ricorda: non puoi costruire sul perimetro.");
    }

}
