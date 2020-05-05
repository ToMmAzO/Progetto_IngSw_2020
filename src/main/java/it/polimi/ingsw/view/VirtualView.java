package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.Cards.Deck;
import it.polimi.ingsw.model.Cards.God;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.SocketClientConnection;

import java.util.HashMap;
import java.util.Map;

public class VirtualView {

    private static final Map<Player, SocketClientConnection> playerConnection = new HashMap<>();
    private static Player currPlayer;

    public static boolean mapEmpty(){
        return playerConnection.isEmpty();
    }

    public static void addPlayerConnection(Player player, SocketClientConnection c){
        if(playerConnection.isEmpty()){
            playerConnection.put(player, c);
            c.asyncSend(new Message_WelcomeFirst());
        } else{
            playerConnection.put(player, c);
            c.asyncSend(new Message_Welcome());
            c.asyncSend("Avrai il colore " + player.getColor().toString() + ".");
            GameManager.addPlayer(player);
            c.asyncSend(new Message_Wait());
        }
        if (playerConnection.size() == GameManager.getNumberOfPlayers()){
            Server.blockServer();
            playerConnection.get(currPlayer).asyncSend(Deck.getInstance());
            playerConnection.get(currPlayer).asyncSend(new Message_CardChoice());
        }
    }

    public static void nextPlayer(){
        currPlayer = GameManager.nextPlayer(currPlayer);

        playerConnection.get(currPlayer).asyncSend(Deck.getInstance());
        playerConnection.get(currPlayer).asyncSend(new Message_CardChoice());
    }

    public static void setCurrPlayer(Player currPlayer) {
        VirtualView.currPlayer = currPlayer;
    }

    public static boolean verifyActivePlayer(Player player){
        return player == currPlayer;
    }

    protected static void readMessage(ClientMessage message){
        switch (message.getGameState()){
            case WAIT -> {
                playerConnection.get(currPlayer).asyncSend("Attendi!");
            }
            case WELCOME_FIRST -> {
                try {
                    int numberChosen = Integer.parseInt(message.getContent());
                    if (numberChosen == 2 || numberChosen == 3) {
                        GameManager.addFirstPlayer(currPlayer, numberChosen);
                        playerConnection.get(currPlayer).asyncSend("Avrai il colore " + currPlayer.getColor().toString() + ".");
                        playerConnection.get(currPlayer).asyncSend(new Message_Wait());
                    } else {
                        playerConnection.get(currPlayer).asyncSend("Numero scorretto, scrivi 2 oppure 3:");
                    }
                } catch (IllegalArgumentException e) {
                    playerConnection.get(currPlayer).asyncSend("Formato Input scorretto! Scrivi 2 oppure 3:");
                }
            }
            case WELCOME -> {
                GameManager.addPlayer(currPlayer);
                playerConnection.get(currPlayer).asyncSend(new Message_Wait());
            }
            case CARD_CHOICE -> {
                try {
                    int cardNumber = Integer.parseInt(message.getContent());
                    if (Deck.getInstance().isAvailable(cardNumber)) {
                        GameManager.choiceOfCard(currPlayer, cardNumber);
                        playerConnection.get(currPlayer).asyncSend(currPlayer.getGodChoice());
                        playerConnection.get(currPlayer).asyncSend(new Message_Wait());
                        VirtualView.nextPlayer();
                    } else {
                        playerConnection.get(currPlayer).asyncSend("Carta non disponibile, scegline una disponibile.");
                    }
                } catch (IllegalArgumentException e) {
                    playerConnection.get(currPlayer).asyncSend("Formato Input scorretto!");
                }
            }
            case SET_WORKER -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    if (currPlayer.getWorkerSelected(1) == null) {
                        GameManager.setWorker(currPlayer, Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]), 1);
                        playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                        playerConnection.get(currPlayer).asyncSend(new Message_SetWorker());
                    } else {
                        GameManager.setWorker(currPlayer, Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]), 2);
                        playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                        playerConnection.get(currPlayer).asyncSend(new Message_WorkerChoice());
                    }
                    //fine turno, aggiornare il client dopo ogni posizionamento avversario e mandare "il gioco comincia"
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    playerConnection.get(currPlayer).asyncSend("Formato Input scorretto!");
                }
            }
            default -> {
                turn(message);
            }
        }
    }

    private static void turn(ClientMessage message){
        int coordX, coordY;

        switch (message.getGameState()) {
            case WORKER_CHOICE -> {
                try {
                    int selectionWorker = Integer.parseInt(message.getContent());
                    if(selectionWorker == 1 || selectionWorker == 2){
                        if(TurnManager.verifyRegularity(currPlayer, selectionWorker)){
                            //send message action (es. E' il tuo turno!)

                            if(currPlayer.getGodChoice() == God.PROMETHEUS){
                                playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                playerConnection.get(currPlayer).asyncSend(new Message_QuestionPrometheus());
                            } else{
                                playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                playerConnection.get(currPlayer).asyncSend(new Message_Movement());
                            }
                        } else{
                            GameManager.deletePlayer(currPlayer);
                        }
                    } else{
                        playerConnection.get(currPlayer).asyncSend("Numero scorretto! Scrivi 1 oppure 2: ");
                    }
                } catch(IllegalArgumentException e){
                    playerConnection.get(currPlayer).asyncSend("Formato Input scorretto! Scrivi 1 oppure 2: ");
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
                    playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                    playerConnection.get(currPlayer).asyncSend(new Message_Movement());
                } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                    playerConnection.get(currPlayer).asyncSend("Formato Input scorretto!");
                }
            }
            case MOVEMENT -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    coordX = Integer.parseInt(coordString[0]);
                    coordY = Integer.parseInt(coordString[1]);
                    if (ActionManager.verifyCoordinateMovement(TurnManager.getWorkerSelected(), currPlayer.getGodChoice(), coordX, coordY)) {
                        if(currPlayer.getGodChoice() == God.ARTEMIS){
                            TurnManager.setStartX(TurnManager.getWorkerSelected().getCoordX());
                            TurnManager.setStartY(TurnManager.getWorkerSelected().getCoordX());
                        }

                        if(currPlayer.getGodChoice() == God.PAN){
                            if(TurnManager.movementPan(coordX, coordY)) {
                                GameManager.setVictory();
                            }
                        }else{
                            if (TurnManager.movement(coordX, coordY)) {
                                GameManager.setVictory();
                            } else {
                                switch (currPlayer.getGodChoice()) {
                                    case ARTEMIS -> {
                                        playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                        playerConnection.get(currPlayer).asyncSend(new Message_QuestionArtemis());
                                    }
                                    case ATLAS -> {
                                        playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                        playerConnection.get(currPlayer).asyncSend(new Message_QuestionAtlas());
                                    }
                                    case HEPHAESTUS -> {
                                        playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                        playerConnection.get(currPlayer).asyncSend(new Message_QuestionHephaestus());
                                    }
                                    default -> {
                                        playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                        playerConnection.get(currPlayer).asyncSend(new Message_Construction());
                                    }
                                }
                            }
                        }
                    }
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    playerConnection.get(currPlayer).asyncSend("Formato Input scorretto!");
                }
            }
            case SECOND_MOVE -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    coordX = Integer.parseInt(coordString[0]);
                    coordY = Integer.parseInt(coordString[1]);
                    if (TurnManager.getStartX() == coordX && TurnManager.getStartY() == coordY) {
                        playerConnection.get(currPlayer).asyncSend("ATTENTO, non puoi tornare indietro! ");
                    } else {
                        if (ActionManager.verifyCoordinateMovement(TurnManager.getWorkerSelected(), currPlayer.getGodChoice(), coordX, coordY)) {
                            if (TurnManager.movement(coordX, coordY)) {
                                GameManager.setVictory();
                            } else {
                                playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                playerConnection.get(currPlayer).asyncSend(new Message_Construction());
                            }
                        }
                    }
                }catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                    playerConnection.get(currPlayer).asyncSend("Formato Input scorretto!");
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
                    playerConnection.get(currPlayer).asyncSend("Formato Input scorretto!");
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
                    playerConnection.get(currPlayer).asyncSend("Formato Input scorretto!");
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
                            if (currPlayer.getGodChoice() == God.DEMETER) {
                                TurnManager.setBuildX(coordX);
                                TurnManager.setBuildY(coordY);
                                playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                playerConnection.get(currPlayer).asyncSend(new Message_QuestionDemeter());
                            }
                        }
                        //STOP TURN
                    } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                        playerConnection.get(currPlayer).asyncSend("Formato Input scorretto!");
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
                    playerConnection.get(currPlayer).asyncSend("Formato Input scorretto!");
                }
            }
            default -> {
                String answer = message.getContent().toLowerCase().replace(" ", "");
                if (answer.equals("yes")) {
                    switch (message.getGameState()) {
                        case QUESTION_ARTEMIS -> {
                            if(TurnManager.getWorkerSelected().canMove(TurnManager.getStartX(), TurnManager.getStartY())){
                                playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                playerConnection.get(currPlayer).asyncSend(new Message_SecondMove());
                            }else{
                                playerConnection.get(currPlayer).asyncSend("Mi dispiace! " + TurnManager.getWorkerSelected().getIdWorker() + " NON può muoversi una seconda volta!");
                                playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                playerConnection.get(currPlayer).asyncSend(new Message_Construction());
                            }
                        }
                        case QUESTION_ATLAS -> {
                            playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                            playerConnection.get(currPlayer).asyncSend(new Message_ConstructionCupola());
                        }
                        case QUESTION_DEMETER -> {
                            if(TurnManager.getWorkerSelected().canBuild(TurnManager.getBuildX(), TurnManager.getBuildY())){
                                playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                playerConnection.get(currPlayer).asyncSend(new Message_SecondConstruction());
                            }else{
                                playerConnection.get(currPlayer).asyncSend("Mi dispiace! " + TurnManager.getWorkerSelected().getIdWorker() + " NON può costruire una seconda volta!");
                                //STOP TURN
                            }
                        }
                        case QUESTION_HEPHAESTUS -> {
                            if(TurnManager.getWorkerSelected().canBuild(true)) {
                                playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                playerConnection.get(currPlayer).asyncSend(new Message_DoubleConstruction());
                            } else {
                                playerConnection.get(currPlayer).asyncSend(TurnManager.getWorkerSelected().getIdWorker() + " NON può costruire 2 blocchi!");
                                playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                playerConnection.get(currPlayer).asyncSend(new Message_Construction());
                            }
                        }
                        case QUESTION_PROMETHEUS -> {
                            if(TurnManager.getWorkerSelected().canBuild()) {
                                playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                playerConnection.get(currPlayer).asyncSend(new Message_PrebuildPrometheus());
                            } else{
                                System.out.println(TurnManager.getWorkerSelected().getIdWorker() + " NON può costruire!");
                                playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                                playerConnection.get(currPlayer).asyncSend(new Message_Movement());
                            }

                        }
                    }
                } else if (answer.equals("no")) {
                    switch (message.getGameState()) {
                        case QUESTION_DEMETER -> {
                            //STOP TURN
                        }
                        case QUESTION_PROMETHEUS -> {
                            playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                            playerConnection.get(currPlayer).asyncSend(new Message_Movement());
                        }
                        default -> {    //QUESTION_ARTEMIS, QUESTION_ATLAS, QUESTION_HEPHAESTUS
                            playerConnection.get(currPlayer).asyncSend(it.polimi.ingsw.model.Board.Map.getInstance());
                            playerConnection.get(currPlayer).asyncSend(new Message_Construction());
                        }
                    }
                } else {
                    playerConnection.get(currPlayer).asyncSend("Puoi rispondere solo con yes o no!");
                }
            }
        }

    }




}
