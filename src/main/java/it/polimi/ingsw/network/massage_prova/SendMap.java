package it.polimi.ingsw.network.massage_prova;

import it.polimi.ingsw.model.Board.Map;

public class SendMap extends Message{
    private static final long serialVersionUID = 10L;
    Map map = new Map();

    public SendMap(Map board,String senderUsername){
        super(senderUsername,MessageType.SEND_MAP);
        this.map = board;
    }
}
