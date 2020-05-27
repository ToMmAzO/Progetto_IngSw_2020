package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.observer.Observable;

import java.util.HashMap;

public class Game extends Observable<GameState> {

    private static Game game;
    private final HashMap<Player, GameState> playerStates = new HashMap<>();

    public Game(){
        game = this;
    }

    public static Game getInstance(){
        return game;
    }

    public void setGameState(Player player, GameState gameState){
        playerStates.put(player, gameState);
        notify(gameState);
    }

    public GameState getGameState(Player player){
        return playerStates.get(player);
    }

}
