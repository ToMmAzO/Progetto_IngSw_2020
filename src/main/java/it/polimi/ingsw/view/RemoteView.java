package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.Cards.God;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.SocketClientConnection;

public class RemoteView {

    private final SocketClientConnection clientConnection;
    private final Player player;

    public RemoteView(Player player, SocketClientConnection c) {
        this.player = player;
        this.clientConnection = c;
    }

    public void messageReceiver(ClientMessage message){
        if(GameManager.verifyActivePlayer(player)){
            readMessage(message);
        } else{
            clientConnection.asyncSend("Non è il tuo turno! Attendi.");
        }
    }

    protected void readMessage(ClientMessage message){
        switch (message.getGameState()){
            case WELCOME_FIRST -> {
                try {
                    int numberChosen = Integer.parseInt(message.getContent());
                    GameManager.setNumberOfPlayers(numberChosen);
                } catch (IllegalArgumentException e) {
                    clientConnection.asyncSend("Formato Input scorretto! Scrivi 2 oppure 3:");
                }
            }
            case WAIT -> clientConnection.asyncSend("Attendi!");
            case CARD_CHOICE -> {
                try {
                    int cardNumber = Integer.parseInt(message.getContent());
                    GameManager.choiceOfCard(cardNumber);
                } catch (IllegalArgumentException e) {
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            case SET_WORKER -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    GameManager.setWorker(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            default -> turn(message);
        }
    }

    //da qua in poi modificare nel turn manager

    private void turn(ClientMessage message){
        int coordX, coordY;

        switch (message.getGameState()) {
            case WORKER_CHOICE -> {
                try {
                    int selectionWorker = Integer.parseInt(message.getContent());
                    if(selectionWorker == 1 || selectionWorker == 2){
                        if(TurnManager.verifyRegularity(player, selectionWorker)){
                            //send message action (es. E' il tuo turno!)

                            if(player.getGodChoice() == God.PROMETHEUS){
                                clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                clientConnection.asyncSend(new Message_QuestionPrometheus());
                            } else{
                                clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                clientConnection.asyncSend(new Message_Movement());
                            }
                        } else{
                            GameManager.deletePlayer(player);
                        }
                    } else{
                        clientConnection.asyncSend("Numero scorretto! Scrivi 1 oppure 2: ");
                    }
                } catch(IllegalArgumentException e){
                    clientConnection.asyncSend("Formato Input scorretto! Scrivi 1 oppure 2: ");
                }
            }
            case PREBUILD_PROMETHEUS -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    coordX = Integer.parseInt(coordString[0]);
                    coordY = Integer.parseInt(coordString[1]);
                    if(ActionManager.verifyCoordinateConstruction(TurnManager.getWorkerSelected(), false, coordX, coordY)){
                        TurnManager.construction(coordX, coordY);
                        TurnManager.setAllowHeightPrometheus(false);
                    }
                    clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                    clientConnection.asyncSend(new Message_Movement());
                } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            case MOVEMENT -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    coordX = Integer.parseInt(coordString[0]);
                    coordY = Integer.parseInt(coordString[1]);
                    if (ActionManager.verifyCoordinateMovement(TurnManager.getWorkerSelected(), player.getGodChoice(), coordX, coordY)) {
                        if(player.getGodChoice() == God.ARTEMIS){
                            TurnManager.setStartX(TurnManager.getWorkerSelected().getCoordX());
                            TurnManager.setStartY(TurnManager.getWorkerSelected().getCoordX());
                        }

                        if(player.getGodChoice() == God.PAN){
                            if(TurnManager.movementPan(coordX, coordY)) {
                                GameManager.setVictory();
                            }
                        }else{
                            if (TurnManager.movement(coordX, coordY)) {
                                GameManager.setVictory();
                            } else {
                                switch (player.getGodChoice()) {
                                    case ARTEMIS -> {
                                        clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                        clientConnection.asyncSend(new Message_QuestionArtemis());
                                    }
                                    case ATLAS -> {
                                        clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                        clientConnection.asyncSend(new Message_QuestionAtlas());
                                    }
                                    case HEPHAESTUS -> {
                                        clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                        clientConnection.asyncSend(new Message_QuestionHephaestus());
                                    }
                                    default -> {
                                        clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                        clientConnection.asyncSend(new Message_Construction());
                                    }
                                }
                            }
                        }
                    }
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            case SECOND_MOVE -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    coordX = Integer.parseInt(coordString[0]);
                    coordY = Integer.parseInt(coordString[1]);
                    if (TurnManager.getStartX() == coordX && TurnManager.getStartY() == coordY) {
                        clientConnection.asyncSend("ATTENTO, non puoi tornare indietro! ");
                    } else {
                        if (ActionManager.verifyCoordinateMovement(TurnManager.getWorkerSelected(), player.getGodChoice(), coordX, coordY)) {
                            if (TurnManager.movement(coordX, coordY)) {
                                GameManager.setVictory();
                            } else {
                                clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                clientConnection.asyncSend(new Message_Construction());
                            }
                        }
                    }
                }catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            case CONSTRUCTION_CUPOLA -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    coordX = Integer.parseInt(coordString[0]);
                    coordY = Integer.parseInt(coordString[1]);
                    if(ActionManager.verifyCoordinateConstruction(TurnManager.getWorkerSelected(), false, coordX, coordY)){                            //return new int[]{coordX, coordY};
                        TurnManager.getWorkerSelected().buildBlock(true, coordX, coordY);
                    }
                    //STOP TURN
                } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            case DOUBLE_CONSTRUCTION -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    coordX = Integer.parseInt(coordString[0]);
                    coordY = Integer.parseInt(coordString[1]);
                    if(ActionManager.verifyCoordinateConstruction(TurnManager.getWorkerSelected(), true, coordX, coordY)){                            //return new int[]{coordX, coordY};
                        TurnManager.getWorkerSelected().buildBlock(true, coordX, coordY);
                    }
                    //STOP TURN
                } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            case CONSTRUCTION -> {
                if(TurnManager.getWorkerSelected().canBuild()){
                    try {
                        String[] coordString = message.getContent().replace(" ", "").split(",");
                        coordX = Integer.parseInt(coordString[0]);
                        coordY = Integer.parseInt(coordString[1]);
                        if(ActionManager.verifyCoordinateConstruction(TurnManager.getWorkerSelected(), false, coordX, coordY)){
                            TurnManager.construction(coordX, coordY);
                            if (player.getGodChoice() == God.DEMETER) {
                                TurnManager.setBuildX(coordX);
                                TurnManager.setBuildY(coordY);
                                clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                clientConnection.asyncSend(new Message_QuestionDemeter());
                            }
                        }
                        //STOP TURN
                    } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                        clientConnection.asyncSend("Formato Input scorretto!");
                    }
                } else{
                    System.out.println(TurnManager.getWorkerSelected().getIdWorker() + " NON può costruire!");
                    //send message stopturn
                }
            }
            case SECOND_CONSTRUCTION -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    coordX = Integer.parseInt(coordString[0]);
                    coordY = Integer.parseInt(coordString[1]);
                    if (TurnManager.getBuildX() == coordX && TurnManager.getBuildY() == coordY) {
                        System.out.print("ATTENTO, non puoi costruire nello stesso punto di prima!");
                    } else {
                        if(ActionManager.verifyCoordinateConstruction(TurnManager.getWorkerSelected(), false, coordX, coordY)){
                            TurnManager.construction(coordX, coordY);
                        }
                        //STOP TURN
                    }
                }catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            default -> {
                String answer = message.getContent().toLowerCase().replace(" ", "");
                if (answer.equals("yes")) {
                    switch (message.getGameState()) {
                        case QUESTION_ARTEMIS -> {
                            if(TurnManager.getWorkerSelected().canMove(TurnManager.getStartX(), TurnManager.getStartY())){
                                clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                clientConnection.asyncSend(new Message_SecondMove());
                            }else{
                                clientConnection.asyncSend("Mi dispiace! " + TurnManager.getWorkerSelected().getIdWorker() + " NON può muoversi una seconda volta!");
                                clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                clientConnection.asyncSend(new Message_Construction());
                            }
                        }
                        case QUESTION_ATLAS -> {
                            clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                            clientConnection.asyncSend(new Message_ConstructionCupola());
                        }
                        case QUESTION_DEMETER -> {
                            if(TurnManager.getWorkerSelected().canBuild(TurnManager.getBuildX(), TurnManager.getBuildY())){
                                clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                clientConnection.asyncSend(new Message_SecondConstruction());
                            }else{
                                clientConnection.asyncSend("Mi dispiace! " + TurnManager.getWorkerSelected().getIdWorker() + " NON può costruire una seconda volta!");
                                //STOP TURN
                            }
                        }
                        case QUESTION_HEPHAESTUS -> {
                            if(TurnManager.getWorkerSelected().canBuild(true)) {
                                clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                clientConnection.asyncSend(new Message_DoubleConstruction());
                            } else {
                                clientConnection.asyncSend(TurnManager.getWorkerSelected().getIdWorker() + " NON può costruire 2 blocchi!");
                                clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                clientConnection.asyncSend(new Message_Construction());
                            }
                        }
                        case QUESTION_PROMETHEUS -> {
                            if(TurnManager.getWorkerSelected().canBuild()) {
                                clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                clientConnection.asyncSend(new Message_PrebuildPrometheus());
                            } else{
                                System.out.println(TurnManager.getWorkerSelected().getIdWorker() + " NON può costruire!");
                                clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                clientConnection.asyncSend(new Message_Movement());
                            }

                        }
                    }
                } else if (answer.equals("no")) {
                    switch (message.getGameState()) {
                        case QUESTION_DEMETER -> {
                            //STOP TURN
                        }
                        case QUESTION_PROMETHEUS -> {
                            clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                            clientConnection.asyncSend(new Message_Movement());
                        }
                        default -> {    //QUESTION_ARTEMIS, QUESTION_ATLAS, QUESTION_HEPHAESTUS
                            clientConnection.asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                            clientConnection.asyncSend(new Message_Construction());
                        }
                    }
                } else {
                    clientConnection.asyncSend("Puoi rispondere solo con yes o no!");
                }
            }
        }

    }

}
