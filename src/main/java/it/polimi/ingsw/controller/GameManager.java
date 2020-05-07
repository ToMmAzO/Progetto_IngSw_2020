package it.polimi.ingsw.controller;

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
    private final HashMap<Player, GameState> playerStates = new HashMap<>();
    private final HashMap<Player, SocketClientConnection> playerConnections = new HashMap<>();
    public Player currPlayer;
    private int numberOfPlayers;

    public GameManager(){
        gameManager = this;
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
            setGameState(player, GameState.WELCOME_FIRST);
            c.asyncSend(new Message_WelcomeFirst());
        } else{
            players.add(player);
            playerConnections.put(player, c);
            setGameState(player, GameState.WAIT);
            c.asyncSend(new Message_Wait());
        }
        if (players.size() == getNumberOfPlayers()){
            Server.setServerAvailability(false);
            playerConnections.get(currPlayer).asyncSend(Deck.getInstance());
            playerConnections.get(currPlayer).asyncSend(new Message_CardChoice());
        }
    }

    public void setNumberOfPlayers(int numberOfPlayers){
        if (numberOfPlayers == 2 || numberOfPlayers == 3) {
            this.numberOfPlayers = numberOfPlayers;
            new Deck(numberOfPlayers);
            new Map();
            Server.setServerAvailability(true);
            playerConnections.get(currPlayer).asyncSend("Avrai il colore " + currPlayer.getColor().toString() + ".");
            playerConnections.get(currPlayer).asyncSend(new Message_Wait());
        } else{
            playerConnections.get(currPlayer).asyncSend("Numero scorretto, scrivi 2 oppure 3:");
        }
    }

    public int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }



    public Player getCurrPlayer() {
        return currPlayer;
    }

    public SocketClientConnection getCurrConnection() {
        return playerConnections.get(currPlayer);
    }



    public GameState getGameState(Player player){
        return playerStates.get(player);
    }

    public void setGameState(Player player, GameState gameState){
        playerStates.put(player, gameState);
    }



    public boolean verifyActivePlayer(Player player){
        return player == currPlayer;
    }

    public Player[] getPlayersInGame(){
        if(players.size() == 1){
            return new Player[]{players.get(0)};
        } else if(players.size() == 2){
            return new Player[]{players.get(0), players.get(1)};
        } else{
            return new Player[]{players.get(0), players.get(1), players.get(2)};
        }
    }

    public void choiceOfCard(int cardNumber){
        if (Deck.getInstance().isAvailable(cardNumber)) {
            currPlayer.setGodChoice(Deck.getInstance().getCardToPlayer(cardNumber));
            playerConnections.get(currPlayer).asyncSend(currPlayer.getGodChoice());

            playerConnections.get(currPlayer).asyncSend(Map.getInstance());

            playerConnections.get(currPlayer).asyncSend(new Message_Wait());//message waitlobby
            nextPlayer(currPlayer);

            //problema per passaggio del turno (messaggi wait diversi per gestirli)
            playerConnections.get(currPlayer).asyncSend(Deck.getInstance());
            playerConnections.get(currPlayer).asyncSend(new Message_CardChoice());
        } else {
            playerConnections.get(currPlayer).asyncSend("Carta non disponibile, scegline una disponibile.");
        }

    }

    public void setWorker(int coordRow, int coordColumn){
        if (currPlayer.getWorkerSelected(1) == null) {
            if (ActionManager.getInstance().validCoords(coordRow, coordColumn)) {
                if (currPlayer.setWorker1(coordRow, coordColumn)) {
                    playerConnections.get(currPlayer).asyncSend(it.polimi.ingsw.model.board.Map.getInstance());
                    playerConnections.get(currPlayer).asyncSend(new Message_SetWorker());
                } else{
                    System.out.print("È già presente un lavoratore quì! ");
                }
            } else{
                System.out.print("Puoi inserire solo interi da 0 a 4! ");
            }

        } else{
            if (ActionManager.getInstance().validCoords(coordRow, coordColumn)) {
                if (currPlayer.setWorker2(coordRow, coordColumn)) {
                    playerConnections.get(currPlayer).asyncSend(it.polimi.ingsw.model.board.Map.getInstance());
                    playerConnections.get(currPlayer).asyncSend(new Message_Wait());//message waitsetworker
                    nextPlayer(currPlayer);

                    //passaggio turno???

                } else{
                    System.out.print("È già presente un lavoratore quì! ");
                }
            } else{
                System.out.print("Puoi inserire solo interi da 0 a 4! ");
            }
        }
    }

    public void deletePlayer(Player player){
        playerConnections.get(currPlayer).asyncSend("Hai perso!");
        Map.getInstance().deleteWorkerInCell(player.getWorkerSelected(1));
        Map.getInstance().deleteWorkerInCell(player.getWorkerSelected(2));
        players.remove(player);
        playerConnections.get(player).closeConnection();
        playerConnections.remove(player);
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

    public void endGame(Player winnerPlayer){
        String winner = winnerPlayer.getNickname();
        System.out.println("Congratulazioni, hai vinto la partita!");
        players.remove(winnerPlayer);
        playerConnections.get(winnerPlayer).closeConnection();
        playerConnections.remove(winnerPlayer);
        if(players.size() > 1) {
            for (Player player : players) {
                System.out.println("GAME OVER! " + winner + " ha vinto la partita.");
                players.remove(player);
                playerConnections.get(player).closeConnection();
                playerConnections.remove(player);
            }
        }
        Server.refresh();
    }

}
