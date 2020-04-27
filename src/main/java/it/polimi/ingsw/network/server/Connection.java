package it.polimi.ingsw.network.server;

import it.polimi.ingsw.view.ViewGame;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection implements Runnable {

    private final Socket socket;
    private static Scanner in;
    private static PrintWriter out;
    private final Server server;
    private boolean active = true;

    public Connection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive(){
        return active;
    }


    public static void send(String message){
        out.println(message);
        out.flush();
    }

    public synchronized void closeConnection(){
        send("Connection closed from the server side");
        try{
            socket.close();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        active = false;
    }

    private void close(){
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    @Override
    public void run() {
        try{
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
            send("Welcome! What's your name?");
            String name = in.nextLine();
            server.lobby(this, name);

            /*
            boolean requiredPlayers = server.lobby(this, name);

            if(requiredPlayers){
                ViewGame.joinGameFirst(name);
            } else{
                ViewGame.joinGame(name);
            }
            */

            while(isActive()){
                String read = in.nextLine();



            }
        } catch(IOException e){
            System.err.println(e.getMessage());
        } finally{
            close();
        }
    }

}
