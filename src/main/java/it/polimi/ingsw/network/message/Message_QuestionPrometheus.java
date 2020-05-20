package it.polimi.ingsw.network.message;

public class Message_QuestionPrometheus extends Message{

    private final GameState gameState = GameState.QUESTION_PROMETHEUS;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.print("Vuoi costruire prima di muoverti?\nRicorda: se construisci ora non potrai salire di livello. Scrivi Yes o No: ");
    }

}
