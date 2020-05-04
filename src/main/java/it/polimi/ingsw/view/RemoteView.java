package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.SocketClientConnection;

public class RemoteView {

    private final SocketClientConnection clientConnection;
    private final Player player;

    public RemoteView(Player player, SocketClientConnection c) {
        this.player = player;
        this.clientConnection = c;
    }

    public void messageReceiver(ClientMessage message){
        if(VirtualView.verifyActivePlayer(player)){
            VirtualView.readMessage(message);
        } else{
            clientConnection.asyncSend("Non è il tuo turno! Attendi.");
        }
    }

}
