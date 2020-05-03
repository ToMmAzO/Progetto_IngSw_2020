package it.polimi.ingsw.network.massage_prova;

public class MoveRequest extends Message{
    private static final long serialVersionUID = 11L;
    int x,y;

    public MoveRequest(int x,int y,String senderUsername){
        super(senderUsername,MessageType.MOVE_REQUEST);
        this.x = x;
        this.y = y;
    }
}
