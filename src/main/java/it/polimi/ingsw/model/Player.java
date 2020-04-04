package it.polimi.ingsw.model;

import java.util.Scanner;

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
        boolean correct;
        int selection;
        System.out.println("select the worker you want to use: ");
        System.out.println("write '1' to use worker number 1 ");
        System.out.println("write '2' to use worker number 2 ");
        System.out.println("than press Enter ");
        do {
            Scanner scanner = new Scanner(System.in);
            selection = scanner.nextInt();
            if (selection == '1' || selection == '2')
                correct = true;
            else
                correct = false;

            switch (selection) {
                case 1:
                    break;
                case 2:
                    break;
                default:
                    System.out.println("you can only insert '1' or '2'");
                    break;
            }
        }while(correct == false);
    return workers[selection-1];
    }

}
