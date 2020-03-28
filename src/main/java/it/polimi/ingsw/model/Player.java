package it.polimi.ingsw.model;

public class Player {

    private String nickname;
    private int age;
    private Color color;
    private Worker[] workers;
    private Effect godChoice;

    public String getNickname() {
        return nickname;
    }

    private void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Color getColor() {
        return color;
    }

    private void setColor(Color color) {
        this.color = color;
    }

    public int getAge() {
        return age;
    }

    private void setAge(int age) {
        this.age = age;
    }

    private void setWorkers(Worker[] workers) {
        this.workers = workers;
    }

    public Worker chooseWorker(){
        //codice
    }

    public Effect getGodChoice() {
        return godChoice;
    }

    public void setGodChoice(Effect godChoice) {
        this.godChoice = godChoice;
    }

}
