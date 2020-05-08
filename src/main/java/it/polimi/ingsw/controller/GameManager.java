package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.SystemMessage;
import it.polimi.ingsw.network.message.GameState;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.SocketClientConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GameManager {

    private static GameManager gameManager = null;
    private final ArrayList<Player> players = new ArrayList<>();
    private final HashMap<Player, SocketClientConnection> playerConnections = new HashMap<>();
    private Player currPlayer;
    private int numberOfPlayers;

    public GameManager(){
        gameManager = this;
        new Game();
        new SystemMessage();
        new TurnManager();
        new ActionManager();
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
            Game.getInstance().setGameState(player, GameState.WELCOME_FIRST);
        } else{
            players.add(player);
            playerConnections.put(player, c);
            Game.getInstance().setGameState(player, GameState.WAIT_PLAYERS);
        }
        if (players.size() == numberOfPlayers){
            Server.setServerAvailability(false);


            playerConnections.get(currPlayer).asyncSend(Deck.getInstance());
            playerConnections.get(currPlayer).asyncSend(new Message_CardChoice());


        }
    }



    //serve ancora quando abbiamo fatto???

    public SocketClientConnection getCurrConnection() {
        return playerConnections.get(currPlayer);
    }

    //???



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
            new Map();
            Server.setServerAvailability(true);
            Game.getInstance().setGameState(currPlayer, GameState.WELCOME_FIRST);

            //playerConnections.get(currPlayer).asyncSend("Avrai il colore " + currPlayer.getColor().toString() + ".");
            playerConnections.get(currPlayer).asyncSend(new Message_WaitPlayers());


        } else{
            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().contentError);
        }
    }

    public void choiceOfCard(int cardNumber){
        if (Deck.getInstance().isAvailable(cardNumber)) {
            currPlayer.setGodChoice(Deck.getInstance().getCardToPlayer(cardNumber));



            playerConnections.get(currPlayer).asyncSend(Map.getInstance());
            playerConnections.get(currPlayer).asyncSend(new Message_WaitPlayers());//message waitlobby


            nextPlayer(currPlayer);


            //problema per passaggio del turno (messaggi wait diversi per gestirli)
            playerConnections.get(currPlayer).asyncSend(Deck.getInstance());
            playerConnections.get(currPlayer).asyncSend(new Message_CardChoice());


        } else {
            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cardNotAvailable);
        }

    }

    public void setWorker(int coordRow, int coordColumn){
        if (currPlayer.getWorkerSelected(1) == null) {
            if (ActionManager.getInstance().validCoords(coordRow, coordColumn)) {
                if (currPlayer.setWorker1(coordRow, coordColumn)) {


                    playerConnections.get(currPlayer).asyncSend(new Message_SetWorker());


                } else{
                    SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().workerPresence);
                }
            } else{
                SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().exceedMap);
            }

        } else{
            if (ActionManager.getInstance().validCoords(coordRow, coordColumn)) {
                if (currPlayer.setWorker2(coordRow, coordColumn)) {


                    playerConnections.get(currPlayer).asyncSend(new Message_WaitPlayers());//message waitsetworker


                    nextPlayer(currPlayer);
                    //passaggio turno???


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
    }

    public void deletePlayer(Player player){
        SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().youLose);
        Map.getInstance().deleteWorkerInCell(player.getWorkerSelected(1));
        Map.getInstance().deleteWorkerInCell(player.getWorkerSelected(2));
        players.remove(player);
        playerConnections.get(player).closeConnection();
        playerConnections.remove(player);
    }

    public void endGame(Player winnerPlayer){
        SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().youWin);
        players.remove(winnerPlayer);
        playerConnections.get(winnerPlayer).closeConnection();
        playerConnections.remove(winnerPlayer);
        if(players.size() > 1) {
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
