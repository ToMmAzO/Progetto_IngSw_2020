package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.ClientMessage;
import it.polimi.ingsw.view.RemoteView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection implements Runnable {

    private final Socket socket;
    private ObjectOutputStream out;
    private final Server server;
    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive(){
        return active;
    }

    public void asyncSend(final Object message){
        /*
        if(message instanceof Message){
            currGameState = ((Message) message).getGameState();
        }
        */
        new Thread(() -> send(message)).start();
    }

    private synchronized void send(Object message){
        try{
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    public synchronized void closeConnection(){
        send("Connection closed!");
        try{
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    private void close(){
        closeConnection();
        System.out.println("Deregistering client...");
        System.out.println("Done!");
    }

    @Override
    public void run(){
        try{
            Scanner in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send("Welcome!\nWhat is your name?");
            String read = in.nextLine();
            String name = read;
            Player player = server.lobby(name, this);
            RemoteView viewSocket = new RemoteView(player, this);
            ClientMessage message = new ClientMessage();
            while(isActive()){
                read = in.nextLine();
                message.setGameState(GameManager.getInstance().getGameState(player));
                message.setContent(read);
                viewSocket.messageReceiver(message);
            }
        } catch (IOException | NoSuchElementException | InterruptedException e) {
            System.err.println("Error!" + e.getMessage());
        } finally{
            close();
        }
    }

}
