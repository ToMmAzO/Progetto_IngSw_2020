package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_QuestionHephaestus extends Message{

    private final GameState gameState = GameState.QUESTION_HEPHAESTUS;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public String getMessage(){
        return "Do you want to build 2 blocks? Type Yes or No: ";
    }

}
