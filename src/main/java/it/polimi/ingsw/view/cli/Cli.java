package it.polimi.ingsw.view.cli;

import it.polimi.ingsw.network.client.ClientGameManager;
import it.polimi.ingsw.network.message.*;


import java.util.Scanner;

public class Cli extends ClientGameManager {
    Scanner stdin = new Scanner(System.in);
    String choice;





    public void runState(){
        switch (getPlayerState()){
            case WELCOME_FIRST -> {
                Message_WelcomeFirst message = new Message_WelcomeFirst();
                message.printMessage();
            }
            case CARD_CHOICE -> {
                Message_CardChoice message = new Message_CardChoice();
                message.printMessage();
            }
            case SET_WORKER1 -> {
                Message_SetWorker message = new Message_SetWorker();
                message.printMessage();
            }
            case SET_WORKER2 -> {
                Message_SetWorker2 message = new Message_SetWorker2();
                message.printMessage();
            }
            case WORKER_CHOICE -> {
                Message_WorkerChoice message = new Message_WorkerChoice();
                message.printMessage();
            }
            case MOVEMENT -> {
                Message_Movement message = new Message_Movement();
                message.printMessage();
            }
            case CONSTRUCTION -> {
                Message_Construction message = new Message_Construction();
                message.printMessage();
            }
            case QUESTION_ARTEMIS -> {
                Message_QuestionArtemis message = new Message_QuestionArtemis();
                message.printMessage();
            }
            case SECOND_MOVE -> {
                Message_SecondMove message = new Message_SecondMove();
                message.printMessage();
            }
            case QUESTION_ATLAS -> {
                Message_QuestionAtlas message = new Message_QuestionAtlas();
                message.printMessage();
            }
            case CONSTRUCTION_CUPOLA -> {
                Message_ConstructionCupola message = new Message_ConstructionCupola();
                message.printMessage();
            }
            case QUESTION_DEMETER -> {
                Message_QuestionDemeter message = new Message_QuestionDemeter();
                message.printMessage();
            }
            case SECOND_CONSTRUCTION -> {
                Message_SecondConstruction message = new Message_SecondConstruction();
                message.printMessage();
            }
            case QUESTION_HEPHAESTUS -> {
                Message_QuestionHephaestus message = new Message_QuestionHephaestus();
                message.printMessage();
            }
            case DOUBLE_CONSTRUCTION -> {
                Message_DoubleConstruction message = new Message_DoubleConstruction();
                message.printMessage();
            }
            case QUESTION_PROMETHEUS -> {
                Message_QuestionPrometheus message = new Message_QuestionPrometheus();
                message.printMessage();
            }
            case PREBUILD_PROMETHEUS -> {
                Message_PrebuildPrometheus message = new Message_PrebuildPrometheus();
                message.printMessage();
            }
            default -> {
                Message_Wait message = new Message_Wait();
                message.printMessage();
            }


        }
    }

    @Override
    public void run(){

    }



}
