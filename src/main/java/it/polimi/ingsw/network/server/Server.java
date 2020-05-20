package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.player.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static int PORT;
    private final ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newFixedThreadPool(128);
    private static boolean serverReady;

    public Server(int port) throws IOException {
        PORT = port;
        this.serverSocket = new ServerSocket(PORT);
        serverReady = true;
    }

    public static void setServerAvailability(boolean choice){
        serverReady = choice;
    }

    public static void refresh(){
        new GameManager();
        setServerAvailability(true);
    }

    public synchronized Player lobby(String nickname, SocketClientConnection c) {
        if(serverReady){
            Player player = new Player(nickname);
            if(GameManager.getInstance().mapEmpty()) {
                GameManager.getInstance().setCurrPlayer(player);
            }
            GameManager.getInstance().addPlayerConnection(player, c);
            return player;
        } else{
            c.asyncSend("Il server non è ancora pronto per accettare nuovi giocatori, riprova più tardi.");
            c.closeConnection();
            return null;
        }
    }

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
