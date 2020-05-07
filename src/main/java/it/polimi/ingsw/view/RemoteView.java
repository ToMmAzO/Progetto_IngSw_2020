package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.player.Player;
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
        if(GameManager.getInstance().verifyActivePlayer(player)){
            readMessage(message);
        } else{
            clientConnection.asyncSend("Non è il tuo turno! Attendi.");
        }
    }

    private void readMessage(ClientMessage message){
        switch (message.getGameState()){
            case WELCOME_FIRST -> {
                try {
                    int numberChosen = Integer.parseInt(message.getContent());
                    GameManager.getInstance().setNumberOfPlayers(numberChosen);
                } catch (IllegalArgumentException e) {
                    clientConnection.asyncSend("Formato Input scorretto! Scrivi 2 oppure 3:");
                }
            }
            case WAIT -> clientConnection.asyncSend("Attendi!");
            case CARD_CHOICE -> {
                try {
                    int cardNumber = Integer.parseInt(message.getContent());
                    GameManager.getInstance().choiceOfCard(cardNumber);
                } catch (IllegalArgumentException e) {
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            case SET_WORKER -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    GameManager.getInstance().setWorker(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            default -> turn(message);
        }
    }

    private void turn(ClientMessage message){
        switch (message.getGameState()) {
            case WORKER_CHOICE -> {
                try {
                    int selectionWorker = Integer.parseInt(message.getContent());
                    TurnManager.getInstance().workerChoice(selectionWorker);
                } catch(IllegalArgumentException e){
                    clientConnection.asyncSend("Formato Input scorretto! Scrivi 1 oppure 2: ");
                }
            }
            case PREBUILD_PROMETHEUS -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().prebuildPrometheus(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            case MOVEMENT -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().movement(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            case SECOND_MOVE -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().secondMove(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                }catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            case CONSTRUCTION_CUPOLA -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().constructionCupola(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            case DOUBLE_CONSTRUCTION -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().doubleConstruction(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            case CONSTRUCTION -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().construction(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            case SECOND_CONSTRUCTION -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().secondConstruction(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                }catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            default -> {
                String answer = message.getContent().toLowerCase().replace(" ", "");
                if (answer.equals("yes")) {
                    switch (message.getGameState()) {
                        case QUESTION_ARTEMIS -> clientConnection.asyncSend(new Message_SecondMove());
                        case QUESTION_ATLAS -> clientConnection.asyncSend(new Message_ConstructionCupola());
                        case QUESTION_DEMETER -> clientConnection.asyncSend(new Message_SecondConstruction());
                        case QUESTION_HEPHAESTUS -> clientConnection.asyncSend(new Message_DoubleConstruction());
                        case QUESTION_PROMETHEUS -> clientConnection.asyncSend(new Message_PrebuildPrometheus());
                    }
                } else if (answer.equals("no")) {
                    switch (message.getGameState()) {
                        case QUESTION_DEMETER -> {} //STOP TURN
                        case QUESTION_PROMETHEUS -> clientConnection.asyncSend(new Message_Movement());
                        default -> clientConnection.asyncSend(new Message_Construction());   //QUESTION_ARTEMIS, QUESTION_ATLAS, QUESTION_HEPHAESTUS
                    }
                } else {
                    clientConnection.asyncSend("Puoi rispondere solo con yes o no!");
                }
            }
        }
    }

}
