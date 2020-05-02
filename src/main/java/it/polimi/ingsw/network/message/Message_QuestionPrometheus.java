package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumerations.GameState;

public class Message_QuestionPrometheus extends Message{

    private final GameState gameState = GameState.QUESTION_PROMETHEUS;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("Vuoi costruire prima di muoverti? (Yes o No)\n Ricorda: se construisci ora non potrai salire di livello.");
    }

}
