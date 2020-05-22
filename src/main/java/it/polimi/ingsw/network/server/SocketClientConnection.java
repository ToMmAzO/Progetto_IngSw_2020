package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.game.Game;
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
    private RemoteView viewSocket;
    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }

    public void setViewSocket(RemoteView view){
        viewSocket = view;
    }

    public RemoteView getViewSocket(){
        return viewSocket;
    }

    private synchronized boolean isActive(){
        return active;
    }

    public synchronized void asyncSend(Object message){
        try{
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    public synchronized void closeConnection(){
        asyncSend("Connection closed!");
        try{
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
        System.out.println("Client deregistered!");
    }

    @Override
    public void run(){
        try{
            Scanner in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            asyncSend("What's your name?");
            String read = in.nextLine();
            String name = read;
            Player player = server.lobby(name, this);
            ClientMessage message = new ClientMessage();
            while(isActive()){
                read = in.nextLine();
                message.setGameState(Game.getInstance().getGameState(player));
                message.setContent(read);
                viewSocket.messageReceiver(message);
            }
        } catch (IOException | NoSuchElementException e) {
            System.err.println("Error!" + e.getMessage());
        } finally{
            closeConnection();
        }
    }

}
