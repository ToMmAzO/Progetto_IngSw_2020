package it.polimi.ingsw.model;

import java.util.Scanner;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Cards.Divinity;
import it.polimi.ingsw.model.Cards.God;
import it.polimi.ingsw.model.DivinityWorkers.*;

public class Player {

    private String nickname;
    private Color color;
    private Worker[] workers = new Worker[2];
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

    public boolean setWorker1(int row, int column) {
        boolean isValid = true;
        if(Map.getWorkerInCell(row,column)==null) {
            switch (this.getGodChoice()) {
                case PAN:
                    workers[0] = new WorkerPan(this.color, column, row);
                    Map.setWorkerInCell(row, column, workers[0]);
                    break;
                case ATLAS:
                    workers[0] = new WorkerAtlas(this.color, column, row);
                    Map.setWorkerInCell(row, column, workers[0]);
                    break;
                case APOLLO:
                    workers[0] = new WorkerApollo(this.color, column, row);
                    Map.setWorkerInCell(row, column, workers[0]);
                    break;
                case ATHENA:
                    workers[0] = new WorkerAthena(this.color, column, row);
                    Map.setWorkerInCell(row, column, workers[0]);
                    break;
                case ARTEMIS:
                    workers[0] = new WorkerArtemis(this.color, column, row);
                    Map.setWorkerInCell(row, column, workers[0]);
                    break;
                case DEMETER:
                    workers[0] = new WorkerDemeter(this.color, column, row);
                    Map.setWorkerInCell(row, column, workers[0]);
                    break;
                case MINOTAUR:
                    workers[0] = new WorkerMinotaur(this.color, column, row);
                    Map.setWorkerInCell(row, column, workers[0]);
                    break;
                case HEPHAESTUS:
                    workers[0] = new WorkerHephaestus(this.color, column, row);
                    Map.setWorkerInCell(row, column, workers[0]);
                    break;
                case PROMETHEUS:
                    workers[0] = new WorkerPrometheus(this.color, column, row);
                    Map.setWorkerInCell(row, column, workers[0]);
                    break;
                default:
                    System.out.println("invalid God choice!");
            }
        } else
            isValid = false;
    return isValid;
    }

    public boolean setWorker2(int row, int column) {
        boolean isValid = true;
        if(Map.getWorkerInCell(row,column)==null) {
            switch (this.getGodChoice()) {
                case PAN:
                    workers[1] = new WorkerPan(this.color, column, row);
                    Map.setWorkerInCell(row, column, workers[1]);
                    break;
                case ATLAS:
                    workers[1] = new WorkerAtlas(this.color, column, row);
                    Map.setWorkerInCell(row, column, workers[1]);
                    break;
                case APOLLO:
                    workers[1] = new WorkerApollo(this.color, column, row);
                    Map.setWorkerInCell(row, column, workers[1]);
                    break;
                case ATHENA:
                    workers[1] = new WorkerAthena(this.color, column, row);
                    Map.setWorkerInCell(row, column, workers[1]);
                    break;
                case ARTEMIS:
                    workers[1] = new WorkerArtemis(this.color, column, row);
                    Map.setWorkerInCell(row, column, workers[1]);
                    break;
                case DEMETER:
                    workers[1] = new WorkerDemeter(this.color, column, row);
                    Map.setWorkerInCell(row, column, workers[1]);
                    break;
                case MINOTAUR:
                    workers[1] = new WorkerMinotaur(this.color, column, row);
                    Map.setWorkerInCell(row, column, workers[1]);
                    break;
                case HEPHAESTUS:
                    workers[1] = new WorkerHephaestus(this.color, column, row);
                    Map.setWorkerInCell(row, column, workers[1]);
                    break;
                case PROMETHEUS:
                    workers[1] = new WorkerPrometheus(this.color, column, row);
                    Map.setWorkerInCell(row, column, workers[1]);
                    break;
                default:
                    System.out.println("invalid God choice!");
            }
        } else
            isValid = false;
        return isValid;
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
