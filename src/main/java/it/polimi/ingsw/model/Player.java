package it.polimi.ingsw.model;

import java.util.Scanner;

import it.polimi.ingsw.model.Cards.Divinity;

public class Player {

    private String nickname;
    private Color color;
    private Worker[] workers;
    private Divinity godChoice;

    public Player(String nickname){
        this.nickname = nickname;
    }
    public static void main(String[]args){

        Player p1 = new Player("raffo");
        Player p2 = new Player("raffo2");
        Player p3 = new Player("raffo3");
        for(Color color: Color.values())
            color.init();
        p1.chooseColor();
        p2.chooseColor();
        p3.chooseColor();
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    //used in chooseColor() to initialize Player's attribute 'color'
    private void setColor(Color color) {
        this.color = color;
    }

    //let the user know wich colors are available and make him choose one
    public void chooseColor(){
        System.out.println("write 'RED' , 'YELLOW' or 'BLUE' to choose your color");
        for(Color color: Color.values()){
            Boolean x = false;
            x = color.isAvailiable();
            if(x==true)
                System.out.println("color:" + color + " is availiable");
            else
                System.out.println("color:" + color + " is NOT availiable");
        }

        System.out.println("what's your choice? : ");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        choice = choice.toUpperCase();
        System.out.println("ho letto: " + choice);
        switch (choice){
            case "RED":
                this.setColor(Color.RED);
                Color.RED.booked();
                break;
            case "YELLOW":
                this.setColor(Color.YELLOW);
                Color.YELLOW.booked();
                break;
            case "BLUE":
                this.setColor(Color.BLUE);
                Color.BLUE.booked();
                break;
            default:
                System.out.println("something's wrong!");
                break;
        }

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
