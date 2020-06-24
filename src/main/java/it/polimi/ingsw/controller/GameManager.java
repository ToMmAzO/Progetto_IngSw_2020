package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.SystemMessage;
import it.polimi.ingsw.model.player.Color;
import it.polimi.ingsw.model.game.GameState;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.SocketClientConnection;
import it.polimi.ingsw.network.server.SocketRemoteView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class is the principal controller of entire program.
 * In every its methods players are updated on their game state.
 */
public class GameManager {

    private static GameManager gameManager;
    private final ArrayList<Player> players = new ArrayList<>();
    private final HashMap<Player, SocketClientConnection> playerConnections = new HashMap<>();
    private Player currPlayer;
    private int numberOfPlayers;

    /**
     * When GameManager is created, it initializes all others classes.
     * The server uses this constructor to create a new game.
     */
    public GameManager(){
        gameManager = this;
        new TurnManager();
        new ActionManager();
        new Game();
        new SystemMessage();
        new Map();
        Color.initialization();
    }

    public static GameManager getInstance(){
        return gameManager;
    }

    /**
     * This method verifies that the playerConnections's HashMap is empty.
     *
     * @return true if it is empty.
     */
    public boolean mapEmpty(){
        return playerConnections.isEmpty();
    }

    /**
     * This method adds new players in game and initializes them like deck's observers.
     *
     * @param player is the player to add.
     * @param c is the socket connection of him.
     */
    public void addPlayerConnection(Player player, SocketClientConnection c){
        if(players.isEmpty()){
            Server.setServerAvailability(false);
            players.add(player);
            playerConnections.put(player, c);
            c.setViewSocket(new SocketRemoteView(player, c));
            Game.getInstance().setGameState(player, GameState.WELCOME_FIRST);
        } else{
            players.add(player);
            playerConnections.put(player, c);
            currPlayer = player;
            c.setViewSocket(new SocketRemoteView(player, c));
            Game.getInstance().setGameState(player, GameState.WAIT_PLAYERS);
        }
        if(players.size() == numberOfPlayers){
            Server.setServerAvailability(false);
            for(Player p1: players){
                for(Player p2: players){
                    p1.addObserver(playerConnections.get(p2).getViewSocket().createChangeInDeck());
                }
            }
            nextPlayer(currPlayer);
        }
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    /**
     * @return all players actually in game.
     */
    public Player[] getPlayersInGame(){
        Player[] listOfPlayer = new Player[players.size()];
        listOfPlayer = players.toArray(listOfPlayer);
        return listOfPlayer;
    }

    /**
     * This method sets the number of players that this game will have and initializes a new deck with this number of card.
     *
     * @param numberOfPlayers is the number chosen by the first player.
     */
    public void setNumberOfPlayers(int numberOfPlayers){
        if (numberOfPlayers == 2 || numberOfPlayers == 3) {
            this.numberOfPlayers = numberOfPlayers;
            new Deck(numberOfPlayers);
            Server.setServerAvailability(true);
            Game.getInstance().setGameState(currPlayer, GameState.WAIT_PLAYERS);
        } else{
            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().contentError);
        }
    }

    /**
     * This method assigns the card chosen by a player in his class.
     * The first number available is 1 and not 0 like in array indexes in deck.
     *
     * @param cardNumber is the number of card selected.
     */
    public void choiceOfCard(int cardNumber){
        if (Deck.getInstance().isAvailable(cardNumber)) {
            currPlayer.setGodChoice(Deck.getInstance().getCardToPlayer(cardNumber));
            Game.getInstance().setGameState(currPlayer, GameState.WAIT_CARD_CHOICE);
            nextPlayer(currPlayer);
        } else {
            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cardNotAvailable);
        }

    }

    /**
     * This method sets workers on map for every players in game.
     *
     * @param coordRow is the row coordinate.
     * @param coordColumn is column coordinate.
     */
    public void setWorker(int coordRow, int coordColumn){
        if (currPlayer.getWorkerSelected(1) == null) {
            if (ActionManager.getInstance().validCoords(coordRow, coordColumn)) {
                if (currPlayer.setWorker1(coordRow, coordColumn)) {
                    Game.getInstance().setGameState(currPlayer, GameState.SET_WORKER);
                } else{
                    SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().workerPresence);
                }
            } else{
                SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().exceedMap);
            }
        } else{
            if (ActionManager.getInstance().validCoords(coordRow, coordColumn)) {
                if (currPlayer.setWorker2(coordRow, coordColumn)) {
                    Game.getInstance().setGameState(currPlayer, GameState.WAIT_TURN);
                    nextPlayer(currPlayer);
                } else{
                    SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().workerPresence);
                }
            } else{
                SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().exceedMap);
            }
        }
    }

    /**
     * This method is used to know who is the next player.
     *
     * @param player is the current player.
     */
    public void nextPlayer(Player player){
        Iterator<Player> i = players.iterator();
        Player nextPlayer = null;
        while (i.hasNext()) {
            Player currPlayer = i.next();
            if (currPlayer.equals(player)){
                if(i.hasNext()) {
                    nextPlayer = i.next();
                    break;
                }
                break;
            }
        }
        if(nextPlayer != null){
            currPlayer = nextPlayer;
        } else{
            currPlayer = players.get(0);
        }
        switch(Game.getInstance().getGameState(currPlayer)){
            case WAIT_PLAYERS -> Game.getInstance().setGameState(currPlayer, GameState.CARD_CHOICE);
            case WAIT_CARD_CHOICE -> Game.getInstance().setGameState(currPlayer, GameState.SET_WORKER);
            case WAIT_TURN -> Game.getInstance().setGameState(currPlayer, GameState.WORKER_CHOICE);
        }
    }

    /**
     * This method eliminates a player from the game and updates the player list.
     *
     * @param player is the player to delete.
     */
    public void deletePlayer(Player player){
        int index = players.indexOf(player);
        Map.getInstance().deleteWorkerInCell(player.getWorkerSelected(1));
        Map.getInstance().deleteWorkerInCell(player.getWorkerSelected(2));
        Game.getInstance().setGameState(player, GameState.LOSE);
        players.remove(player);
        playerConnections.get(player).closeConnection();
        playerConnections.remove(player);
        if(index >= players.size()){
            currPlayer = players.get(0);
        } else{
            currPlayer = players.get(index);
        }
        if(players.size() == 1){
            endGame(currPlayer);
        } else{
            Game.getInstance().setGameState(currPlayer, GameState.WORKER_CHOICE);
        }
    }

    /**
     * This method is used when a player wins the game.
     * After notifying all players, the server is cleaned.
     *
     * @param winnerPlayer is the winner player.
     */
    public void endGame(Player winnerPlayer){
        currPlayer = winnerPlayer;
        Game.getInstance().setGameState(winnerPlayer, GameState.WIN);
        Server.refresh();
    }

    /**
     * This method is used to invalidate the game because a player in game has disconnected from the server.
     * After notifying all players, the server is cleaned.
     *
     * @param player is the player that has left the game.
     */
    public void disconnectedPlayer(Player player){
        currPlayer = player;
        Game.getInstance().setGameState(player, GameState.INVALIDATION);
        Server.refresh();
    }

}
