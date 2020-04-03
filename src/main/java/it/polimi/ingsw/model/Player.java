package it.polimi.ingsw.model;

public class Player {

    private String nickname;
    private Color color;
    private Worker[] workers;
    private Effect godChoice;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setGodChoice(Effect godChoice) {
        this.godChoice = godChoice;
    }

    private void setWorkers(Worker[] workers) {
        this.workers = workers;
    }

    public String getNickname() {
        return nickname;
    }

    public Color getColor() {
        return color;
    }

    public Effect getGodChoice() {
        return godChoice;
    }

    public Worker getWorkerSelected(){
        //codice
        return workers[0];//esempio
    }

}
