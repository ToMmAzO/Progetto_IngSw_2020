package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_QuestionPrometheus extends Message{

    private final GameState gameState = GameState.QUESTION_PROMETHEUS;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public String getMessage(){
        return "Do you want to build before you move?\nRemember: if you build now you will not be able to level up. Type Yes or No: ";
    }

}
