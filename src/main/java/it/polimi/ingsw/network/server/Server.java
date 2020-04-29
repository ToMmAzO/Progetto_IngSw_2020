package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameManager;

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
    private final List<SocketClientConnection> connections = new ArrayList<>();
    private final Map<String, SocketClientConnection> playerConnection = new HashMap<>();

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    private synchronized void registerConnection(SocketClientConnection c){
        connections.add(c);
    }

    public synchronized void deregisterConnection(SocketClientConnection c){
        connections.remove(c);
    }

    public synchronized void lobby(SocketClientConnection c, String name){
        playerConnection.put(name, c);
        if(playerConnection.size() == GameManager.getNumberOfPlayers()){

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
