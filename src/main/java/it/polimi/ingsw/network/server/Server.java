package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.view.VirtualView;

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
    private static boolean serverReady;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        serverReady = true;
    }

    public static void blockServer(){
        serverReady = false;
    }

    private synchronized void registerConnection(SocketClientConnection c){
        connections.add(c);
    }

    public synchronized void deregisterConnection(SocketClientConnection c){
        connections.remove(c);
    }

    public synchronized void lobby(Player player, SocketClientConnection c){
        if(serverReady){
            if(VirtualView.mapEmpty()){
                VirtualView.addPlayerConnection(player, c);
                VirtualView.setCurrPlayer(player);
            } else {
                VirtualView.addPlayerConnection(player, c);
            }
        } else{
            c.asyncSend("Sorry. È già in corso una partita, riprova più tardi.");
            c.closeConnection();
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
