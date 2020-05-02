package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumerations.GameState;

public class Message_QuestionHephaestus extends Message{

    private final GameState gameState = GameState.QUESTION_HEPHAESTUS;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("Vuoi costruire 2 blocchi? (Yes o No)");
    }

}
