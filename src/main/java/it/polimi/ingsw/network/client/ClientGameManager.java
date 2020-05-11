package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.message.GameState;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.Message_LoadState;



/*COSE IN CLIENT

(inputObject instanceof Message_LoadState){
                        //Cli.loadState(((Message_LoadState) inputObject).getToLoad());
                        //Cli.runState();

                        /*quando il client stabilisce la connessione il server manda come primo messaggio di ritorno un Message_LoadState con il
                        * quale dice al Client come impostare il proprio GameState.
                        * se la richiesta di connessione è la prima, il server risponderà con un Message_LoadState.toLoad = WELCOME_FIRST,
                        * altrimenti WAIT.
                        * inoltre, in questo modo possiamo:
                        * 1) una volta che il primo ha scelto il numero di giocatori far cambiare lo stato ai Client facendogli ricevere un
                        *    Message_LoadState.toLoad = CARD_CHOICE, in questo modo i Client che riceveranno il messaggio aggiorneranno il proprio
                        *    playerState a CARD_CHOICE
                        * 2) nel caso in cui ci fosse un errore durante il gioco perchè il playerState lato Client fosse diverso dal GameState lato server,
                        *    il server potrebbe notificare al client quale stato ha lui in memoria, così da risintonizzarsi nello stesso stato
                        *
                    } else if(inputObject instanceof Message){
                            cli.setPlayerState(((Message)inputObject).getGameState());
                            ((Message)inputObject).printMessage();
                            } else


                            } else if(inputObject instanceof Map){
                        ((Map)inputObject).print();
                        cli.runState();
                        cli.nextState();
                    }


                    } else if(inputObject instanceof God){
                        God.printCardChosen((God)inputObject);
                        cli.setGodChoice((God)inputObject);

*/


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
