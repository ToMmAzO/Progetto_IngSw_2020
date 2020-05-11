package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.workers.*;
import it.polimi.ingsw.observer.Observable;

public class Player extends Observable<God> {

    private final String nickname;
    private final Color color;
    private final Worker[] workers = new Worker[2];
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
        notify(god);
    }

    public boolean setWorker1(int row, int column) {
        String id = color.toString().substring(0, 3).concat("1");
        if(Map.getInstance().noWorkerHere(row, column)) {
            switch (this.getGodChoice()) {
                case APOLLO -> workers[0] = new WorkerApollo(id, row, column);
                case ARTEMIS -> workers[0] = new WorkerArtemis(id, row, column);
                case ATHENA -> workers[0] = new WorkerAthena(id, row, column);
                case ATLAS -> workers[0] = new WorkerAtlas(id, row, column);
                case CHRONUS -> workers[0] = new WorkerChronus(id, row, column);
                case DEMETER -> workers[0] = new WorkerDemeter(id, row, column);
                case HEPHAESTUS -> workers[0] = new WorkerHephaestus(id, row, column);
                case HERA -> workers[0] = new WorkerHera(id, row, column);
                case HESTIA -> workers[0] = new WorkerHestia(id, row, column);
                case MINOTAUR -> workers[0] = new WorkerMinotaur(id, row, column);
                case PAN -> workers[0] = new WorkerPan(id, row, column);
                case PROMETHEUS -> workers[0] = new WorkerPrometheus(id, row, column);
                case TRITON -> workers[0] = new WorkerTriton(id, row, column);
                case ZEUS -> workers[0] = new WorkerZeus(id, row, column);
            }
            return true;
        } else
            return false;
    }

    public boolean setWorker2(int row, int column) {
        String id = color.toString().substring(0, 3).concat("2");
        if(Map.getInstance().noWorkerHere(row, column)) {
            switch (this.getGodChoice()) {
                case APOLLO -> workers[1] = new WorkerApollo(id, row, column);
                case ARTEMIS -> workers[1] = new WorkerArtemis(id, row, column);
                case ATHENA -> workers[1] = new WorkerAthena(id, row, column);
                case ATLAS -> workers[1] = new WorkerAtlas(id, row, column);
                case CHRONUS -> workers[1] = new WorkerChronus(id, row, column);
                case DEMETER -> workers[1] = new WorkerDemeter(id, row, column);
                case HEPHAESTUS -> workers[1] = new WorkerHephaestus(id, row, column);
                case HERA -> workers[1] = new WorkerHera(id, row, column);
                case HESTIA -> workers[1] = new WorkerHestia(id, row, column);
                case MINOTAUR -> workers[1] = new WorkerMinotaur(id, row, column);
                case PAN -> workers[1] = new WorkerPan(id, row, column);
                case PROMETHEUS -> workers[1] = new WorkerPrometheus(id, row, column);
                case TRITON -> workers[1] = new WorkerTriton(id, row, column);
                case ZEUS -> workers[1] = new WorkerZeus(id, row, column);
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

    public void printWorkersPositions(){
        System.out.printf("%s: %s (livello %d), %s (livello %d).\n", getNickname(),
                getWorkerSelected(1).getIdWorker(), getWorkerSelected(1).getCoordZ(),
                getWorkerSelected(2).getIdWorker(), getWorkerSelected(2).getCoordZ()
        );
    }

}
