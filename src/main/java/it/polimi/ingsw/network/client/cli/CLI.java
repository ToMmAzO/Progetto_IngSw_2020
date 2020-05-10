package it.polimi.ingsw.network.client.cli;

import it.polimi.ingsw.network.message.*;

public class CLI {

    private GameState playerState;

    public void setPlayerState(GameState state){
        this.playerState = state;
        runState(state);
    }

    public void runState(GameState state){
        Message message;
        switch (state){
            case WELCOME_FIRST -> message = new Message_WelcomeFirst();
            case WAIT_PLAYERS -> message = new Message_WaitPlayers();
            case WAIT_CARD_CHOICE -> message = new Message_WaitCardChoice();
            case WAIT_TURN -> message = new Message_WaitTurn();
            case CARD_CHOICE -> message = new Message_CardChoice();
            case SET_WORKER -> message = new Message_SetWorker();
            case WORKER_CHOICE -> message = new Message_WorkerChoice();
            case QUESTION_PROMETHEUS -> message = new Message_QuestionPrometheus();
            case PREBUILD_PROMETHEUS -> message = new Message_PrebuildPrometheus();
            case MOVEMENT -> message = new Message_Movement();
            case QUESTION_ARTEMIS -> message = new Message_QuestionArtemis();
            case QUESTION_ATLAS -> message = new Message_QuestionAtlas();
            case CONSTRUCTION_CUPOLA -> message = new Message_ConstructionCupola();
            case SECOND_MOVE -> message = new Message_SecondMove();
            case QUESTION_HEPHAESTUS -> message = new Message_QuestionHephaestus();
            case DOUBLE_CONSTRUCTION -> message = new Message_DoubleConstruction();
            case QUESTION_DEMETER -> message = new Message_QuestionDemeter();
            case SECOND_CONSTRUCTION -> message = new Message_SecondConstruction();
            case CONSTRUCTION -> message = new Message_Construction();
            default -> message = new Message_Error();
        }
        message.printMessage();
    }

}
