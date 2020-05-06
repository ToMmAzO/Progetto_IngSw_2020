package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Player.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT= 12345;
    private final ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newFixedThreadPool(128);
    private final ArrayList<SocketClientConnection> connections = new ArrayList<>();
    private static boolean serverReady;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        serverReady = true;
    }

    public static void setServerAvailability(boolean choice){
        serverReady = choice;
    }

    private synchronized void registerConnection(SocketClientConnection c){
        connections.add(c);
    }

    public synchronized void deregisterConnection(SocketClientConnection c){
        connections.remove(c);
    }

    public synchronized void lobby(Player player, SocketClientConnection c) throws InterruptedException {
        if(serverReady){
            if(GameManager.mapEmpty()) {
                GameManager.setCurrPlayer(player);
            }
            GameManager.addPlayerConnection(player, c);
        } else{
            c.asyncSend("Il server non è ancora pronto per accettare nuovi giocatori, riprova più tardi.");
            wait(10);
            c.closeConnection();
        }
    }

    public void run(){
        System.out.println("Server listening on port: " + PORT);
        while(true){
            try{
                Socket socket = serverSocket.accept();
                SocketClientConnection connection = new SocketClientConnection(socket, this);
                registerConnection(connection);
                executor.submit(connection);
            } catch(IOException e){
                System.err.println("Connection error!");
            }
        }
    }

}
