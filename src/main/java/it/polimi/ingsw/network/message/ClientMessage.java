package it.polimi.ingsw.network.message;

public class ClientMessage {

    private Target target;
    private String content;

    public void setTarget(Target target) {
        this.target = target;
    }

    public Target getTarget() {
        return target;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
