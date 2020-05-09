package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.message.GameState;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Message_LoadState;

public abstract class ClientGameManager implements Runnable{     //in questa classe metto tutte le cose che "regole di gioco" di cui è a conoscenza il client, poi Cli e Gui le ereditano e implementano i metodi per applicarle
    private String username;
    private God godChoice;
    private GameState playerState = GameState.LOAD_STATE;

    protected ClientGameManager() {
    }


    public void loadState(Message_LoadState message){
        this.playerState = message.getToLoad();
    }

    public void nextState(){
        GameState nextState;
        switch (godChoice){
            case ARTEMIS -> ArtemisNextState();
            case ATLAS -> AtlasNextState();
            case DEMETER -> DemeterNextState();
            case HEPHAESTUS -> HephaestusNextState();
            case PROMETHEUS -> PrometheusNextState();
            default -> DefaultNextState();
        }
    }

    public void DefaultNextState(){
        GameState nextPlayerState;
        switch (playerState){
            case WAIT -> nextPlayerState = GameState.WORKER_CHOICE;
            case WORKER_CHOICE -> nextPlayerState = GameState.MOVEMENT;
            case MOVEMENT -> nextPlayerState = GameState.CONSTRUCTION;
            //case CONSTRUCTION -> nextPlayerState = GameState.WAIT;
            default -> nextPlayerState = GameState.WAIT;
        }
        playerState = nextPlayerState;
    }

    public void ArtemisNextState(){
        GameState nextPlayerState;
        switch (playerState){
            case WAIT -> nextPlayerState = GameState.WORKER_CHOICE;
            case WORKER_CHOICE -> nextPlayerState = GameState.MOVEMENT;
            case MOVEMENT -> nextPlayerState = GameState.QUESTION_ARTEMIS;
            //case QUESTION_ARTEMIS -> nextPlayerState = GameState.SECOND_MOVE;
            case SECOND_MOVE -> nextPlayerState = GameState.CONSTRUCTION;
            default -> nextPlayerState = GameState.WAIT;
        }
        playerState = nextPlayerState;
    }

    public void AtlasNextState(){
        GameState nextPlayerState;
        switch (playerState){
            case WAIT -> nextPlayerState = GameState.WORKER_CHOICE;
            case WORKER_CHOICE -> nextPlayerState = GameState.MOVEMENT;
            case MOVEMENT -> nextPlayerState = GameState.QUESTION_ATLAS;
            /*case QUESTION_ATLAS -> {
                boolean cupola = askCupola();
                if(cupola) nextPlayerState = GameState.CONSTRUCTION_CUPOLA;
                else nextPlayerState = GameState.CONSTRUCTION;
            }*/
            default -> nextPlayerState = GameState.WAIT;
        }
        playerState = nextPlayerState;
    }

    public boolean askCupola(){
        return false;
    }

    public void DemeterNextState(){
        GameState nextPlayerState;
        switch (playerState){
            case WAIT -> nextPlayerState = GameState.WORKER_CHOICE;
            case WORKER_CHOICE -> nextPlayerState = GameState.MOVEMENT;
            case MOVEMENT -> nextPlayerState = GameState.CONSTRUCTION;
            case CONSTRUCTION -> nextPlayerState = GameState.QUESTION_DEMETER;
            /*case QUESTION_DEMETER -> {
                boolean secondConstruction = askSecondConstruction();
                if(secondConstruction) nextPlayerState = GameState.SECOND_CONSTRUCTION;
                else nextPlayerState = GameState.WAIT;
            }*/
            default -> nextPlayerState = GameState.WAIT;
        }
        playerState = nextPlayerState;
    }

    public boolean askSecondConstruction(){
        return false;
    }

    public void HephaestusNextState(){
        GameState nextPlayerState;
        switch (playerState){
            case WAIT -> nextPlayerState = GameState.WORKER_CHOICE;
            case WORKER_CHOICE -> nextPlayerState = GameState.MOVEMENT;
            case MOVEMENT -> nextPlayerState = GameState.QUESTION_HEPHAESTUS;
            /*case QUESTION_HEPHAESTUS -> {
                boolean doubleConstruction = askDoubleConstruction();
                if(doubleConstruction) nextPlayerState = GameState.DOUBLE_CONSTRUCTION;
                else nextPlayerState = GameState.CONSTRUCTION;
            }*/
            default -> nextPlayerState = GameState.WAIT;
        }
        playerState = nextPlayerState;
    }

    public boolean askDoubleConstruction(){
        return false;
    }

    public void PrometheusNextState(){
        GameState nextPlayerState;
        switch (playerState){
            case WAIT -> nextPlayerState = GameState.WORKER_CHOICE;
            case WORKER_CHOICE -> nextPlayerState = GameState.QUESTION_PROMETHEUS;
            /*case QUESTION_PROMETHEUS -> {
                boolean preBuild = askPreBuild();
                if(preBuild) nextPlayerState = GameState.PREBUILD_PROMETHEUS;
                else nextPlayerState = GameState.MOVEMENT;
            }*/
            case PREBUILD_PROMETHEUS -> nextPlayerState = GameState.MOVEMENT;
            case MOVEMENT -> nextPlayerState = GameState.CONSTRUCTION;
            default -> nextPlayerState = GameState.WAIT;
        }
        playerState = nextPlayerState;
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
