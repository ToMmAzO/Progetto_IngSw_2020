package it.polimi.ingsw.network.massage_prova;

public class BuildRequest extends Message{
    private static final long serialVersionUID = 2L;
    int x,y;

    public BuildRequest(int x, int y, String senderUsername){
        super(senderUsername,MessageType.BUILD_REQUEST);
        this.x = x;
        this.y = y;
    }
}
