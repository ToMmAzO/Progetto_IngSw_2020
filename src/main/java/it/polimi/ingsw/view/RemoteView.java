package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.Cards.Deck;
import it.polimi.ingsw.model.Cards.God;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.network.message.ClientMessage;
import it.polimi.ingsw.network.server.ClientConnection;

public class RemoteView {

    private final ClientConnection clientConnection;
    private final String nickname;

    public RemoteView(String nickname, ClientConnection c) {
        this.nickname = nickname;
        this.clientConnection = c;
        new MessageReceiver();
    }

    private class MessageReceiver {

        private Player player;

        public void read(ClientMessage message) {
            switch (message.getGameState()) {
                case WELCOME_FIRST -> {
                    try {
                        int numberChosen = Integer.parseInt(message.getContent());
                        if(numberChosen == 2 || numberChosen == 3){
                            GameManager.addFirstPlayer(nickname, numberChosen);
                            this.player = GameManager.getPlayersInGame()[0];
                            clientConnection.asyncSend("Avrai il colore " + player.getColor().toString() + ".");
                            //send message CardChoice
                        } else{
                            clientConnection.asyncSend("Numero scorretto, scrivi 2 oppure 3:");
                        }
                    } catch(IllegalArgumentException e){
                        clientConnection.asyncSend("Formato Input scorretto! Scrivi 2 oppure 3:");
                    }
                }
                case WELCOME -> {
                    GameManager.addPlayer(nickname);
                    if (GameManager.getPlayersInGame()[1].getNickname().equals(nickname)) {
                        this.player = GameManager.getPlayersInGame()[1];
                    } else{
                        this.player = GameManager.getPlayersInGame()[2];
                    }
                    clientConnection.asyncSend("Avrai il colore " + player.getColor().toString() + ".");
                    //send message CardChoice
                }
                case CARD_CHOICE -> {
                    try {
                        int cardNumber = Integer.parseInt(message.getContent());
                        if(Deck.getInstance().isAvailable(cardNumber)){
                            GameManager.choiceOfCard(player, cardNumber);
                            clientConnection.asyncSend(player.getGodChoice());
                            //send message SetWorker
                        } else{
                            clientConnection.asyncSend("Carta non disponibile, scegline una disponibile.");
                        }
                    } catch(IllegalArgumentException e){
                        clientConnection.asyncSend("Formato Input scorretto!");
                    }
                }
                case SET_WORKER -> {
                    try {
                        String[] coordString = message.getContent().replace(" ", "").split(",");
                        if (player.getWorkerSelected(1) == null) {
                            GameManager.setWorker(player, Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]), 1);
                        } else{
                            GameManager.setWorker(player, Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]), 2);
                        }
                        //fine turno, aggiornare il client dopo ogni posizionamento avversario e mandare "il gioco comincia"
                        //send message workerchoice
                    } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                        clientConnection.asyncSend("Formato Input scorretto!");
                    }
                }
                default -> {
                    turn(message);
                }
            }
        }

        /*
                case MOVEMENT -> {
                    int coordX, coordY;
                    try {
                        String[] coordString = message.getContent().replace(" ", "").split(",");
                        coordX = Integer.parseInt(coordString[0]);
                        coordY = Integer.parseInt(coordString[1]);
                        if(ActionManager.verifyCoordinateMovement(player.getWorkerSelected(selectionWorker), player.getGodChoice(), coordX, coordY)){
                            //return new int[]{coordX, coordY};
                        }
                    } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                        clientConnection.asyncSend("Formato Input scorretto!");
                    }
                }
                case CONSTRUCTION -> {
                    int coordX, coordY;
                    try {
                        String[] coordString = message.getContent().replace(" ", "").split(",");
                        coordX = Integer.parseInt(coordString[0]);
                        coordY = Integer.parseInt(coordString[1]);
                        if(ActionManager.verifyCoordinateConstruction(player.getWorkerSelected(selectionWorker), coordX, coordY)){                            //return new int[]{coordX, coordY};
                            //return new int[]{coordX, coordY};
                        }
                    } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                        clientConnection.asyncSend("Formato Input scorretto!");
                    }
                }
        */

        private void turn(ClientMessage message){
            switch (message.getGameState()) {
                case WORKER_CHOICE -> {
                    try {
                        int selectionWorker = Integer.parseInt(message.getContent());
                        if(selectionWorker == 1 || selectionWorker == 2){
                            if(TurnManager.verifyRegularity(player, selectionWorker)){
                                if(player.getGodChoice() == God.PROMETHEUS){
                                    //send message questionprometheus
                                } else{
                                    //send message movement
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

                    //send message movement
                }
                case MOVEMENT -> {

                    switch (player.getGodChoice()) {
                        case ARTEMIS -> {
                            //send message questionartemis
                        }
                        case ATLAS -> {
                            //send message questionatlas
                        }
                        case HEPHAESTUS -> {
                            //send message questionhephaestus
                        }
                        default -> {
                            //send message construction
                        }
                    }
                }
                case SECOND_MOVE -> {

                    //send message construction
                }
                case CONSTRUCTION_CUPOLA -> {

                    //send message stopturn
                }
                case DOUBLE_CONSTRUCTION -> {

                    //send message stopturn
                }
                case CONSTRUCTION -> {

                    if (player.getGodChoice() == God.DEMETER) {
                        //send message questiondemeter
                    } else {
                        //send message stopturn
                    }
                }
                case SECOND_CONSTRUCTION -> {

                    //send message stopturn
                }
                default -> {
                    String answer = message.getContent().toLowerCase().replace(" ", "");
                    if (answer.equals("yes")) {
                        switch (message.getGameState()) {
                            case QUESTION_PROMETHEUS -> {
                                //send message prebuildprometheus
                            }
                            case QUESTION_ARTEMIS -> {
                                //send message movement2
                            }
                            case QUESTION_ATLAS -> {
                                //send message constructioncupola
                            }
                            case QUESTION_HEPHAESTUS -> {
                                //send message constructiondouble
                            }
                            case QUESTION_DEMETER -> {
                                //send message construction2
                            }
                        }
                    } else if (answer.equals("no")) {
                        switch (message.getGameState()) {
                            case QUESTION_PROMETHEUS -> {
                                //send message movement
                            }
                            case QUESTION_DEMETER -> {
                                //send message stopturn
                            }
                            default -> {//QUESTION_ARTEMIS, QUESTION_ATLAS, QUESTION_HEPHAESTUS
                                //send message construction
                            }
                        }
                    } else {
                        clientConnection.asyncSend("Puoi rispondere solo con yes o no!");
                    }
                }
            }

        }

    }

}
