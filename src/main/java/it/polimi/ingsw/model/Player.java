package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Cards.Divinity;

public class Player {

    private String nickname;
    private Color color;
    private Worker[] workers;
    private Divinity godChoice;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setGodChoice(Divinity godChoice) {
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

    public Divinity getGodChoice() {
        return godChoice;
    }

    public Worker getWorkerSelected(){
        //codice
        return workers[0];//esempio
    }

}
