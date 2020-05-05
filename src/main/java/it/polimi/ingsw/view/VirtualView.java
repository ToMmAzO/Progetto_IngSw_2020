package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Cards.Deck;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.SocketClientConnection;

import java.util.HashMap;
import java.util.Map;

public class VirtualView {

    private static final Map<Player, SocketClientConnection> playerConnection = new HashMap<>();
    private static Player currPlayer;

    public static boolean mapEmpty(){
        return playerConnection.isEmpty();
    }

    public static void addPlayerConnection(Player player, SocketClientConnection c){
        if(playerConnection.isEmpty()){
            playerConnection.put(player, c);
            c.asyncSend(new Message_WelcomeFirst());
        } else{
            playerConnection.put(player, c);
            c.asyncSend(new Message_Welcome());
            c.asyncSend("Avrai il colore " + player.getColor().toString() + ".");
            GameManager.addPlayer(player);
            c.asyncSend(new Message_Wait());
        }
        if (playerConnection.size() == GameManager.getNumberOfPlayers()){
            Server.blockServer();
            playerConnection.get(currPlayer).asyncSend(Deck.getInstance());
            playerConnection.get(currPlayer).asyncSend(new Message_CardChoice());
        }
    }

    public static void nextPlayer(){
        currPlayer = GameManager.nextPlayer(currPlayer);

        playerConnection.get(currPlayer).asyncSend(Deck.getInstance());
        playerConnection.get(currPlayer).asyncSend(new Message_CardChoice());
    }

    public static void setCurrPlayer(Player currPlayer) {
        VirtualView.currPlayer = currPlayer;
    }

    public static boolean verifyActivePlayer(Player player){
        return player == currPlayer;
    }

}
