package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_QuestionHestia extends Message{

    private final GameState gameState = GameState.QUESTION_HESTIA;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public void printMessage(){
        System.out.print("Vuoi costruire ancora?\nRicorda: non puoi costruire sul perimetro. Scrivi Yes o No: ");
    }

}
