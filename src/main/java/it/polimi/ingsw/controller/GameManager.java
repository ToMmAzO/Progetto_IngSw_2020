package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Cards.Deck;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.SocketClientConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GameManager {

    private static final ArrayList<Player> players = new ArrayList<>();
    private static final HashMap<Player, SocketClientConnection> playerConnections = new HashMap<>();
    public static Player currPlayer;
    private static int numberOfPlayers;
    private static boolean victory = false;

    public static boolean mapEmpty(){
        return playerConnections.isEmpty();
    }

    public static void addPlayerConnection(Player player, SocketClientConnection c){
        if(players.isEmpty()){
            Server.setServerAvailability(false);
            players.add(0, player);
            playerConnections.put(player, c);
            c.asyncSend(new Message_WelcomeFirst());
        } else{
            players.add(player);
            playerConnections.put(player, c);
            c.asyncSend(new Message_Wait());
        }
        if (players.size() == getNumberOfPlayers()){
            Server.setServerAvailability(false);
            playerConnections.get(currPlayer).asyncSend(Deck.getInstance());
            playerConnections.get(currPlayer).asyncSend(new Message_CardChoice());
        }
    }

    public static void setNumberOfPlayers(int numberOfPlayers){
        if (numberOfPlayers == 2 || numberOfPlayers == 3) {
            GameManager.numberOfPlayers = numberOfPlayers;
            new Deck(numberOfPlayers);
            new Map();
            Server.setServerAvailability(true);
            playerConnections.get(currPlayer).asyncSend("Avrai il colore " + currPlayer.getColor().toString() + ".");
            playerConnections.get(currPlayer).asyncSend(new Message_Wait());
        } else{
            playerConnections.get(currPlayer).asyncSend("Numero scorretto, scrivi 2 oppure 3:");
        }
    }

    public static int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    public static void setCurrPlayer(Player currPlayer) {
        GameManager.currPlayer = currPlayer;
    }

    public static boolean verifyActivePlayer(Player player){
        return player == currPlayer;
    }

    public static void setVictory(){
        victory = true;
    }

    public static Player[] getPlayersInGame(){
        if(players.size() == 1){
            return new Player[]{players.get(0)};
        } else if(players.size() == 2){
            return new Player[]{players.get(0), players.get(1)};
        } else{
            return new Player[]{players.get(0), players.get(1), players.get(2)};
        }
    }

    public static void choiceOfCard(int cardNumber){
        if (Deck.getInstance().isAvailable(cardNumber)) {
            currPlayer.setGodChoice(Deck.getInstance().getCardToPlayer(cardNumber));
            playerConnections.get(currPlayer).asyncSend(currPlayer.getGodChoice());
            playerConnections.get(currPlayer).asyncSend(new Message_Wait());//message waitlobby
            nextPlayer(currPlayer);

            //problema per passaggio del turno (messaggi wait diversi per gestirli)
            playerConnections.get(currPlayer).asyncSend(Deck.getInstance());
            playerConnections.get(currPlayer).asyncSend(new Message_CardChoice());
        } else {
            playerConnections.get(currPlayer).asyncSend("Carta non disponibile, scegline una disponibile.");
        }

    }

    public static void setWorker(int coordRow, int coordColumn){
        if (currPlayer.getWorkerSelected(1) == null) {
            if (ActionManager.validCoords(coordRow, coordColumn)) {
                if (currPlayer.setWorker1(coordRow, coordColumn)) {
                    playerConnections.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                    playerConnections.get(currPlayer).asyncSend(new Message_SetWorker());
                } else{
                    System.out.print("È già presente un lavoratore quì! ");
                }
            } else{
                System.out.print("Puoi inserire solo interi da 0 a 4! ");
            }

        } else{
            if (ActionManager.validCoords(coordRow, coordColumn)) {
                if (currPlayer.setWorker2(coordRow, coordColumn)) {
                    playerConnections.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
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

    public static void deletePlayer(Player player){
        Iterator<Player> i = players.iterator();
        while (i.hasNext()) {
            Player currPlayer = i.next();
            if (currPlayer.equals(player)) {
                System.out.println("Hai perso!");
                Map.getInstance().deleteWorkerInCell(currPlayer.getWorkerSelected(1));
                Map.getInstance().deleteWorkerInCell(currPlayer.getWorkerSelected(2));
                i.remove();
                playerConnections.get(currPlayer).closeConnection();
                playerConnections.remove(currPlayer);
                break;
            }
        }
    }

    public static void nextPlayer(Player player){
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

    private static void endGame(Player winnerPlayer){
        String winner = null;
        Iterator<Player> i = players.iterator();
        while (i.hasNext()) {
            Player currPlayer = i.next();
            if (currPlayer.equals(winnerPlayer)) {
                winner = currPlayer.getNickname();
                System.out.println("Congratulazioni, hai vinto la partita!");
                i.remove();
                break;
            }
        }
        if(players.size() > 1) {
            for (Player player : players) {
                System.out.println("GAME OVER! " + winner + " ha vinto.");
            }
        }
    }

}
