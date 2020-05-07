package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.message.GameState;
import it.polimi.ingsw.model.Cards.God;

public abstract class ClientGameManager implements Runnable{     //in questa classe metto tutte le cose che "regole di gioco" di cui è a conoscenza il client, poi Cli e Gui le ereditano e implementano i metodi per applicarle
    private String username;
    private God godChoice;
    private GameState playerState;

    protected ClientGameManager() {
    }

    public GameState nextState(){
        GameState nextState;
        switch (godChoice){
            case ARTEMIS -> nextState = ArtemisNextState(playerState);
            case ATLAS -> nextState = AtlasNextState(playerState);
            case DEMETER -> nextState = DemeterNextState(playerState);
            case HEPHAESTUS -> nextState = HephaestusNextState(playerState);
            case PROMETHEUS -> nextState = PrometheusNextState(playerState);
            default -> nextState = DefaultNextState(playerState);
        }
        return nextState;
    }

    public GameState DefaultNextState(GameState currPlayerState){
        GameState nextPlayerState;
        switch (currPlayerState){
            case WAIT -> nextPlayerState = GameState.WORKER_CHOICE;
            case WORKER_CHOICE -> nextPlayerState = GameState.MOVEMENT;
            case MOVEMENT -> nextPlayerState = GameState.CONSTRUCTION;
            //case CONSTRUCTION -> nextPlayerState = GameState.WAIT;
            default -> nextPlayerState = GameState.WAIT;
        }
        return nextPlayerState;
    }

    public GameState ArtemisNextState(GameState currPlayerState){
        GameState nextPlayerState;
        switch (currPlayerState){
            case WAIT -> nextPlayerState = GameState.WORKER_CHOICE;
            case WORKER_CHOICE -> nextPlayerState = GameState.MOVEMENT;
            case MOVEMENT -> nextPlayerState = GameState.QUESTION_ARTEMIS;
            case QUESTION_ARTEMIS -> nextPlayerState = GameState.SECOND_MOVE;
            case SECOND_MOVE -> nextPlayerState = GameState.CONSTRUCTION;
            default -> nextPlayerState = GameState.WAIT;
        }
        return nextPlayerState;
    }

    public GameState AtlasNextState(GameState currPlayerState){
        GameState nextPlayerState;
        switch (currPlayerState){
            case WAIT -> nextPlayerState = GameState.WORKER_CHOICE;
            case WORKER_CHOICE -> nextPlayerState = GameState.MOVEMENT;
            case MOVEMENT -> nextPlayerState = GameState.QUESTION_ATLAS;
            case QUESTION_ATLAS -> {
                boolean cupola = askCupola();
                if(cupola) nextPlayerState = GameState.CONSTRUCTION_CUPOLA;
                else nextPlayerState = GameState.CONSTRUCTION;
            }
            default -> nextPlayerState = GameState.WAIT;
        }
        return nextPlayerState;
    }

    public boolean askCupola(){
        return false;
    }

    public GameState DemeterNextState(GameState currPlayerState){
        GameState nextPlayerState;
        switch (currPlayerState){
            case WAIT -> nextPlayerState = GameState.WORKER_CHOICE;
            case WORKER_CHOICE -> nextPlayerState = GameState.MOVEMENT;
            case MOVEMENT -> nextPlayerState = GameState.CONSTRUCTION;
            case CONSTRUCTION -> nextPlayerState = GameState.QUESTION_DEMETER;
            case QUESTION_DEMETER -> {
                boolean secondConstruction = askSecondConstruction();
                if(secondConstruction) nextPlayerState = GameState.SECOND_CONSTRUCTION;
                else nextPlayerState = GameState.WAIT;
            }
            default -> nextPlayerState = GameState.WAIT;
        }
        return nextPlayerState;
    }

    public boolean askSecondConstruction(){
        return false;
    }

    public GameState HephaestusNextState(GameState currPlayerState){
        GameState nextPlayerState;
        switch (currPlayerState){
            case WAIT -> nextPlayerState = GameState.WORKER_CHOICE;
            case WORKER_CHOICE -> nextPlayerState = GameState.MOVEMENT;
            case MOVEMENT -> nextPlayerState = GameState.QUESTION_HEPHAESTUS;
            case QUESTION_HEPHAESTUS -> {
                boolean doubleConstruction = askDoubleConstruction();
                if(doubleConstruction) nextPlayerState = GameState.DOUBLE_CONSTRUCTION;
                else nextPlayerState = GameState.CONSTRUCTION;
            }
            default -> nextPlayerState = GameState.WAIT;
        }
        return nextPlayerState;
    }

    public boolean askDoubleConstruction(){
        return false;
    }

    public GameState PrometheusNextState(GameState currPlayerState){
        GameState nextPlayerState;
        switch (currPlayerState){
            case WAIT -> nextPlayerState = GameState.WORKER_CHOICE;
            case WORKER_CHOICE -> nextPlayerState = GameState.QUESTION_PROMETHEUS;
            case QUESTION_PROMETHEUS -> {
                boolean preBuild = askPreBuild();
                if(preBuild) nextPlayerState = GameState.PREBUILD_PROMETHEUS;
                else nextPlayerState = GameState.MOVEMENT;
            }
            case PREBUILD_PROMETHEUS -> nextPlayerState = GameState.MOVEMENT;
            case MOVEMENT -> nextPlayerState = GameState.CONSTRUCTION;
            default -> nextPlayerState = GameState.WAIT;
        }
        return nextPlayerState;
    }

    public boolean askPreBuild(){
        return false;
    }

    @Override
    public void run(){

    }

    public void setPlayerState(GameState State){
        this.playerState = State;
    }

    public GameState getPlayerState(){
        return playerState;
    }


    public void setGodChoice(God godChoice) {
        this.godChoice = godChoice;
    }

    public God getGodChoice(){
        return godChoice;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
