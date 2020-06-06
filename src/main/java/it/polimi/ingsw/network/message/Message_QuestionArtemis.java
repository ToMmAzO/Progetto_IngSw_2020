package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_QuestionArtemis extends Message{

    private final GameState gameState = GameState.QUESTION_ARTEMIS;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public String getMessage(){
        return "Do you want to move again?\nRemember: you cannot go back to the previous space. Type Yes or No: ";
    }

}
