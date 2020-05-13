package it.polimi.ingsw.network;

import it.polimi.ingsw.network.server.Server;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args){
        Server server;
        try{
            server = new Server(12345);
            server.run();
        } catch(IOException e){
            System.err.println("Impossible to start the server!\n" + e.getMessage());
        }
    }

}
