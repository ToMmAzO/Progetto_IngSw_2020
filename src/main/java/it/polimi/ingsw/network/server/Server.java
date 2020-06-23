package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.game.GameState;
import it.polimi.ingsw.model.player.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class starts the entire program and establishes new connections.
 */
public class Server {

    private static int PORT;
    private final ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newFixedThreadPool(128);
    private static boolean serverReady;

    /**
     * @param port is the server port.
     * @throws IOException for possible error when socket is opened.
     */
    public Server(int port) throws IOException {
        PORT = port;
        this.serverSocket = new ServerSocket(PORT);
        serverReady = true;
    }

    public static void setServerAvailability(boolean choice){
        serverReady = choice;
    }

    /**
     * This method is used for deleting the current game and replacing it with a new one.
     */
    public static void refresh(){
        new GameManager();
        setServerAvailability(true);
    }

    /**
     * This method accepts new connections and, if server is ready, add this player to the game.
     *
     * @param nickname is the name of the new player.
     * @param c is the player connection.
     * @return the player created or null if the new player can`t join the lobby.
     */
    public synchronized Player lobby(String nickname, SocketClientConnection c) {
        if(serverReady){
            Player[] players = GameManager.getInstance().getPlayersInGame();
            for(Player p: players){
                if(p.getNickname().equals(nickname)){
                    return null;
                }
            }
            Player player = new Player(nickname);
            if(GameManager.getInstance().mapEmpty()) {
                GameManager.getInstance().setCurrPlayer(player);
            }
            GameManager.getInstance().addPlayerConnection(player, c);
            return player;
        } else{
            c.asyncSend(GameState.ERROR);
            c.closeConnection();
            return null;
        }
    }

    /**
     * This method starts the execution and waits for new connections.
     */
    public void run(){
        System.out.println("Server listening on port " + PORT + ".");
        new GameManager();
        while(true){
            try{
                Socket socket = serverSocket.accept();
                SocketClientConnection connection = new SocketClientConnection(socket, this);
                executor.submit(connection);
            } catch(IOException e){
                System.err.println("Connection error!");
                break;
            }
        }
    }

}
