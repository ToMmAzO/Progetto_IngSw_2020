package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.game.GameState;

public class Message_CardChoice extends Message{

    private final GameState gameState = GameState.CARD_CHOICE;

    public GameState getGameState(){
        return gameState;
    }

    @Override
    public String getMessage(){
        return "Choose the number of one card still available: ";
    }

}
