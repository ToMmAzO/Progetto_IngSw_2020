package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_QuestionDemeter extends Message{

    private final GameState gameState = GameState.QUESTION_DEMETER;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public String getMessage(){
        return "Do you want to build again?\nRemember: you cannot build where you have already built. Type Yes or No: ";
    }

}
