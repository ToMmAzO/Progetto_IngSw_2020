package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Cards.God;
import it.polimi.ingsw.model.Workers.*;

public class Player {

    private String nickname;
    private Color color;
    private Worker[] workers = new Worker[2];
    private God godChoice;

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
        this.godChoice = god;
    }


    public boolean setWorker1(int row, int column) {
        String id = color.toString().substring(0, 3).concat("1");
        if(Map.noWorkerHere(row, column)) {
            switch (this.getGodChoice()) {
                case PAN:
                    workers[0] = new WorkerPan(id, row, column);
                    break;
                case ATLAS:
                    workers[0] = new WorkerAtlas(id, row, column);
                    break;
                case APOLLO:
                    workers[0] = new WorkerApollo(id, row, column);
                    break;
                case ATHENA:
                    workers[0] = new WorkerAthena(id, row, column);
                    break;
                case ARTEMIS:
                    workers[0] = new WorkerArtemis(id, row, column);
                    break;
                case DEMETER:
                    workers[0] = new WorkerDemeter(id, row, column);
                    break;
                case MINOTAUR:
                    workers[0] = new WorkerMinotaur(id, row, column);
                    break;
                case HEPHAESTUS:
                    workers[0] = new WorkerHephaestus(id, row, column);
                    break;
                case PROMETHEUS:
                    workers[0] = new WorkerPrometheus(id, row, column);
                    break;
            }
            return true;
        } else
            return false;
    }

    public boolean setWorker2(int row, int column) {
        String id = color.toString().substring(0, 3).concat("2");
        if(Map.noWorkerHere(row, column)) {
            switch (this.getGodChoice()) {
                case PAN:
                    workers[1] = new WorkerPan(id, row, column);
                    break;
                case ATLAS:
                    workers[1] = new WorkerAtlas(id, row, column);
                    break;
                case APOLLO:
                    workers[1] = new WorkerApollo(id, row, column);
                    break;
                case ATHENA:
                    workers[1] = new WorkerAthena(id, row, column);
                    break;
                case ARTEMIS:
                    workers[1] = new WorkerArtemis(id, row, column);
                    break;
                case DEMETER:
                    workers[1] = new WorkerDemeter(id, row, column);
                    break;
                case MINOTAUR:
                    workers[1] = new WorkerMinotaur(id, row, column);
                    break;
                case HEPHAESTUS:
                    workers[1] = new WorkerHephaestus(id, row, column);
                    break;
                case PROMETHEUS:
                    workers[1] = new WorkerPrometheus(id, row, column);
                    break;
            }
            return true;
        } else
            return false;
    }

    public String getNickname() {
        return nickname;
    }

    public Color getColor() {
        return color;
    }

    public God getGodChoice() {
        return godChoice;
    }

    public Worker getWorkerSelected(int number){
        return workers[number - 1];
    }

}
