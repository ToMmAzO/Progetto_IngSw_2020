package it.polimi.ingsw.network.message;

import it.polimi.ingsw.enumerations.GameState;

public class Message_QuestionDemeter extends Message{

    private final GameState gameState = GameState.QUESTION_DEMETER;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.println("Vuoi costruire ancora? (Yes o No)\n Ricorda: non puoi costruire dove hai già costruito.");
    }

}
