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
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GameManager {

    private static GameManager gameManager;
    private final ArrayList<Player> players = new ArrayList<>();
    private final HashMap<Player, SocketClientConnection> playerConnections = new HashMap<>();
    private Player currPlayer;
    private int numberOfPlayers;

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

    public boolean mapEmpty(){
        return playerConnections.isEmpty();
    }

    public void addPlayerConnection(Player player, SocketClientConnection c){
        if(players.isEmpty()){
            Server.setServerAvailability(false);
            players.add(0, player);
            playerConnections.put(player, c);
            c.setViewSocket(new RemoteView(player, c));
            Game.getInstance().setGameState(player, GameState.WELCOME_FIRST);
        } else{
            players.add(player);
            playerConnections.put(player, c);
            currPlayer = player;
            c.setViewSocket(new RemoteView(player, c));
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

    public Player[] getPlayersInGame(){
        Player[] listOfPlayer = new Player[players.size()];
        listOfPlayer = players.toArray(listOfPlayer);
        return listOfPlayer;
    }

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

    public void choiceOfCard(int cardNumber){
        if (Deck.getInstance().isAvailable(cardNumber)) {
            currPlayer.setGodChoice(Deck.getInstance().getCardToPlayer(cardNumber));
            Game.getInstance().setGameState(currPlayer, GameState.WAIT_CARD_CHOICE);
            nextPlayer(currPlayer);
        } else {
            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cardNotAvailable);
        }

    }

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

    public void deletePlayer(Player player){
        Map.getInstance().deleteWorkerInCell(player.getWorkerSelected(1));
        Map.getInstance().deleteWorkerInCell(player.getWorkerSelected(2));
        SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().youLose);
        int index = players.indexOf(player);
        players.remove(player);
        playerConnections.get(player).closeConnection();
        playerConnections.remove(player);
        if(index > players.size()){
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

    public void endGame(Player winnerPlayer){
        SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().youWin);
        players.remove(winnerPlayer);
        playerConnections.get(winnerPlayer).closeConnection();
        playerConnections.remove(winnerPlayer);
        if(players.size() > 0) {
            for (Player player : players) {
                currPlayer = player;
                SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().gameOver);
                players.remove(player);
                playerConnections.get(player).closeConnection();
                playerConnections.remove(player);
            }
        }
        Server.refresh();
    }

}
