package it.polimi.ingsw.model;

import java.util.Scanner;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Cards.Divinity;
import it.polimi.ingsw.model.Cards.God;

public class Player {

    private String nickname;
    private Color color;
    private Worker[] workers;
    private Divinity godChoice;

    public Player(String nickname){
        this.nickname = nickname;
        if (Color.getAvailability(Color.RED)){
            this.color = Color.RED;
            Color.setAvailabilityToFalse(Color.RED);
        } else {
            if (Color.getAvailability(Color.YELLOW)){
                this.color = Color.YELLOW;
                Color.setAvailabilityToFalse(Color.YELLOW);
            } else {
                this.color = Color.BLUE;
                Color.setAvailabilityToFalse(Color.BLUE);
            }
        }
    }

    public void setGodChoice(God god) {
        this.godChoice = god.getGodName();
    }

    public void setWorkers() {
        Scanner scanner = new Scanner(System.in);
        int row , column;
        System.out.println("In order to start the game put your workers on the map.");
        System.out.println("Choose a cell for each worker; insert raw and column number (from 1 to 5) ");
        System.out.println(" ");
        System.out.println("- WORKER 1 : ");
        System.out.println("column: ");
        column = scanner.nextInt();
        System.out.println("row: ");
        row = scanner.nextInt();
        Map.setWorkerInCell(row,column,workers[0]);
        System.out.println(" ");
        System.out.println("- WORKER 2 : ");
        System.out.println("column: ");
        column = scanner.nextInt();
        System.out.println("row: ");
        row = scanner.nextInt();
        Map.setWorkerInCell(row,column,workers[1]);


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
            correct = selection == '1' || selection == '2';

            switch (selection) {
                case 1:
                    break;
                case 2:
                    break;
                default:
                    System.out.println("you can only insert '1' or '2'");
                    break;
            }
        }while(!correct);
    return workers[selection-1];
    }

}
