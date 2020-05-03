package it.polimi.ingsw.network.massage_prova;

public class BuildResponse extends Message{
    private static final long serialVersionUID = 8L;
    boolean accepted;

    public BuildResponse(boolean accepted,String senderUsername){
        super(senderUsername,MessageType.BUILD_RESPONSE);
        this.accepted = accepted;
    }
}
