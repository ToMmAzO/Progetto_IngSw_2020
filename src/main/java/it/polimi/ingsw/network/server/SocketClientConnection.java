package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameState;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.ClientMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketClientConnection implements Runnable {

    private final Socket socket;
    private ObjectOutputStream out;
    private final Server server;
    private SocketRemoteView viewSocket;
    private boolean active = true;
    Player player;

    public SocketClientConnection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }

    public void setViewSocket(SocketRemoteView view){
        viewSocket = view;
    }

    public SocketRemoteView getViewSocket(){
        return viewSocket;
    }

    public synchronized void asyncSend(Object message){
        try{
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch(IOException ignore){}
    }

    public synchronized void closeConnection(){
        asyncSend("Connection closed!");
        try{
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    @Override
    public void run(){
        try {
            Scanner in = new Scanner(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            String read;
            while(player == null) {
                read = in.nextLine();
                String clientName = read;
                player = server.lobby(clientName, this);
                if(player == null){
                    asyncSend(GameState.WELCOME);
                }
            }
            ClientMessage message = new ClientMessage();
            while(active) {
                read = in.nextLine();
                message.setGameState(Game.getInstance().getGameState(player));
                message.setContent(read);
                viewSocket.messageReceiver(message);
            }
        } catch (IOException | NoSuchElementException e) {
            System.err.println("Closing socket for " + player.getNickname() + "...");
            GameManager.getInstance().disconnectedPlayer(player);
        }
    }

}
