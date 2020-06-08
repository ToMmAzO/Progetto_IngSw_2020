package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_QuestionTriton extends Message{

    private final GameState gameState = GameState.QUESTION_TRITON;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public String getMessage(){
        return "Do you want to move again? ";
    }

}
