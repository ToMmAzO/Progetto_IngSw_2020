package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.GameState;
import it.polimi.ingsw.observer.Observable;

import java.util.HashMap;

public class Game extends Observable<GameState> {

    private final HashMap<Player, GameState> playerStates = new HashMap<>();

    public void setGameState(Player player, GameState gameState){
        playerStates.put(player, gameState);
        notify(gameState);
    }

    public GameState getGameState(Player player){
        return playerStates.get(player);
    }

}
