package it.polimi.ingsw.apps;

import it.polimi.ingsw.network.server.Server;

import java.io.IOException;

/**
 * This is the executable server app.
 * By default the port used is 12345.
 */
public class ServerApp {

    public static void main(String[] args){
        Server server;
        try{
            server = new Server(12345);
            server.run();
        } catch(IOException e){
            System.err.println("Impossible to start the server! " + e.getMessage());
        }
    }

}
