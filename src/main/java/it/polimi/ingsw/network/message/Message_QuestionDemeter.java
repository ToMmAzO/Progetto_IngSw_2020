package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_QuestionDemeter extends Message{

    private final GameState gameState = GameState.QUESTION_DEMETER;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.print("Vuoi costruire ancora?\nRicorda: non puoi costruire dove hai già costruito. Scrivi Yes o No: ");
    }

}
