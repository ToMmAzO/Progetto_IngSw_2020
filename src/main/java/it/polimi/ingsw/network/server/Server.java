package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message_Welcome;
import it.polimi.ingsw.network.message.Message_WelcomeFirst;

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
        if(playerConnection.isEmpty()){
            playerConnection.put(name, c);
            c.asyncSend(new Message_WelcomeFirst());
        } else {
            playerConnection.put(name, c);
            c.asyncSend(new Message_Welcome());
        }
        //va gestita la coda e migliorata la lobby
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
