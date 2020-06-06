package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_QuestionHestia extends Message{

    private final GameState gameState = GameState.QUESTION_HESTIA;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public String getMessage(){
        return "Do you want to build again?\nRemember: you cannot build into a perimeter space. Type Yes or No: ";
    }

}
