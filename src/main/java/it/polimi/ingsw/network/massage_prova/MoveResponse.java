package it.polimi.ingsw.network.massage_prova;

public class MoveResponse extends Message{
    private static final long serialVersionUID = 12L;
    boolean accepted;

    public MoveResponse(boolean accepted,String senderUsername){
        super(senderUsername,MessageType.MOVE_RESPONSE);
        this.accepted = accepted;
    }
}
