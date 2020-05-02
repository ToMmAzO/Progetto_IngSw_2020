package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.Cards.Deck;
import it.polimi.ingsw.model.Cards.God;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Workers.Worker;
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
            Worker workerSelected = null;

            switch (message.getGameState()) {
                case WORKER_CHOICE -> {
                    try {
                        int selectionWorker = Integer.parseInt(message.getContent());
                        if(selectionWorker == 1 || selectionWorker == 2){
                            if(TurnManager.verifyRegularity(player, selectionWorker)){
                                workerSelected = player.getWorkerSelected(selectionWorker);
                                //send message action (es. E' il tuo turno!)

                                /*if(player.getGodChoice() == God.PROMETHEUS){
                                    //send message questionprometheus
                                } else{
                                    //send message movement
                                }
                                 */
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
                case ACTION -> {
                    switch (player.getGodChoice()){
                        case ARTEMIS -> actionArtemis(message, player, workerSelected);
                        case ATLAS -> actionAtlas(message, player, workerSelected);
                        case DEMETER -> actionDemeter(message, player, workerSelected);
                        case HEPHAESTUS -> actionHephaestus(message, player, workerSelected);
                        case PAN -> actionPan(message, player, workerSelected);
                        case PROMETHEUS -> actionPrometheus(message, player, workerSelected);
                        default -> actionDefault(message, player, workerSelected);//APOLLO, ATHENA, ATLAS, MINOTAUR
                    }
                }

                /*
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
                 */
            }

        }

        private void actionArtemis(ClientMessage message, Player player, Worker workerSelected){
            int coordX, coordY;
            int startX = workerSelected.getCoordX();
            int startY = workerSelected.getCoordY();

            switch (message.getGameState()) {
                case MOVEMENT -> {
                    try {
                        String[] coordString = message.getContent().replace(" ", "").split(",");
                        coordX = Integer.parseInt(coordString[0]);
                        coordY = Integer.parseInt(coordString[1]);
                        if(ActionManager.verifyCoordinateMovement(workerSelected, player.getGodChoice(), coordX, coordY)){
                            if(TurnManager.movement(coordX, coordY)){
                                //send message win;
                            }else{
                                //send message construction;
                            }
                        }
                    } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                        clientConnection.asyncSend("Formato Input scorretto!");
                    }
                }
                case QUESTION_ARTEMIS -> {
                    String answer = message.getContent().toLowerCase().replace(" ", "");
                    if(answer.equals("yes")) {
                        if(workerSelected.canMove(startX, startY)){
                            //send message second_move;
                        }else{
                            clientConnection.asyncSend("Mi dispiace! " + workerSelected.getIdWorker() + " NON può muoversi una seconda volta!");
                        }
                    }
                    //send message construction;
                }
                case SECOND_MOVE -> {
                    try {
                        String[] coordString = message.getContent().replace(" ", "").split(",");
                        coordX = Integer.parseInt(coordString[0]);
                        coordY = Integer.parseInt(coordString[1]);
                        if (startX == coordX && startY == coordY) {
                            System.out.print("Non puoi tornare indietro! ");
                        } else {
                            if (ActionManager.verifyCoordinateMovement(workerSelected, player.getGodChoice(), coordX, coordY)) {
                                if (TurnManager.movement(coordX, coordY)) {
                                    //send message win;
                                } else {
                                    //send message construction;
                                }
                            }
                        }
                    }catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                        clientConnection.asyncSend("Formato Input scorretto!");
                    }
                }
                case CONSTRUCTION -> {
                    if(workerSelected.canBuild()){
                        try {
                            String[] coordString = message.getContent().replace(" ", "").split(",");
                            coordX = Integer.parseInt(coordString[0]);
                            coordY = Integer.parseInt(coordString[1]);
                            if(ActionManager.verifyCoordinateConstruction(workerSelected, coordX, coordY)){
                                TurnManager.construction(coordX, coordY);
                            }
                        } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                            clientConnection.asyncSend("Formato Input scorretto!");
                        }
                    } else{
                        System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
                    }
                    //send message next turn;
                }
            }
        }

        private void actionAtlas(ClientMessage message, Player player, Worker workerSelected){
            int coordX, coordY;

            switch (message.getGameState()) {
                case MOVEMENT -> {
                    try {
                        String[] coordString = message.getContent().replace(" ", "").split(",");
                        coordX = Integer.parseInt(coordString[0]);
                        coordY = Integer.parseInt(coordString[1]);
                        if(ActionManager.verifyCoordinateMovement(workerSelected, player.getGodChoice(), coordX, coordY)){
                            if(TurnManager.movement(coordX, coordY)){
                                //send message win;
                            }else{
                                //send message construction;
                            }
                        }
                    } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                        clientConnection.asyncSend("Formato Input scorretto!");
                    }
                }
                case QUESTION_ATLAS -> {
                    if(workerSelected.canBuild()){
                        String answer = message.getContent().toLowerCase().replace(" ", "");
                        if(answer.equals("yes")) {
                            //send message construction_cupola;
                        } else{
                            //send message construction;
                        }
                    } else{
                        System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
                        //send message next turn;
                    }
                }
                case CONSTRUCTION_CUPOLA -> {
                    try {
                        String[] coordString = message.getContent().replace(" ", "").split(",");
                        coordX = Integer.parseInt(coordString[0]);
                        coordY = Integer.parseInt(coordString[1]);
                        if(ActionManager.verifyCoordinateConstruction(workerSelected, coordX, coordY)){                            //return new int[]{coordX, coordY};
                            workerSelected.buildBlock(true, coordX, coordY);
                        }
                    } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                        clientConnection.asyncSend("Formato Input scorretto!");
                    }
                    //send message next turn;
                }
                case CONSTRUCTION -> {
                    try {
                        String[] coordString = message.getContent().replace(" ", "").split(",");
                        coordX = Integer.parseInt(coordString[0]);
                        coordY = Integer.parseInt(coordString[1]);
                        if(ActionManager.verifyCoordinateConstruction(workerSelected, coordX, coordY)){                            //return new int[]{coordX, coordY};
                            workerSelected.buildBlock(false, coordX, coordY);
                        }
                    } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                        clientConnection.asyncSend("Formato Input scorretto!");
                    }
                    //send message next turn;
                }
            }
        }

        private void actionDemeter(ClientMessage message, Player player, Worker workerSelected){
            int coordX, coordY;
            int buildX = 0;
            int buildY = 0;

            switch (message.getGameState()) {
                case MOVEMENT -> {
                    try {
                        String[] coordString = message.getContent().replace(" ", "").split(",");
                        coordX = Integer.parseInt(coordString[0]);
                        coordY = Integer.parseInt(coordString[1]);
                        if(ActionManager.verifyCoordinateMovement(workerSelected, player.getGodChoice(), coordX, coordY)){
                            if(TurnManager.movement(coordX, coordY)){
                                //send message win;
                            }else{
                                //send message construction;
                            }
                        }
                    } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                        clientConnection.asyncSend("Formato Input scorretto!");
                    }
                }
                case CONSTRUCTION -> {
                    if(workerSelected.canBuild()){
                        try {
                            String[] coordString = message.getContent().replace(" ", "").split(",");
                            coordX = Integer.parseInt(coordString[0]);
                            coordY = Integer.parseInt(coordString[1]);
                            if(ActionManager.verifyCoordinateConstruction(workerSelected, coordX, coordY)){
                                buildX = coordX;
                                buildY = coordY;
                                TurnManager.construction(coordX, coordY);
                            }
                        } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                            clientConnection.asyncSend("Formato Input scorretto!");
                        }
                    } else{
                        System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
                    }
                    //send message next turn;
                }
                case QUESTION_DEMETER -> {
                    String answer = message.getContent().toLowerCase().replace(" ", "");
                    if(answer.equals("yes")) {
                        if(workerSelected.canMove(buildX, buildY)){
                            //send message second_construction;
                        }else{
                            clientConnection.asyncSend("Mi dispiace! " + workerSelected.getIdWorker() + " NON può costruire una seconda volta!");
                        }
                    }
                    //send message next turn;
                }
                case SECOND_CONSTRUCTION -> {
                    try {
                        String[] coordString = message.getContent().replace(" ", "").split(",");
                        coordX = Integer.parseInt(coordString[0]);
                        coordY = Integer.parseInt(coordString[1]);
                        if (buildX == coordX && buildY == coordY) {
                            System.out.print("Non puoi costruire nello stesso punto di prima!");
                        } else {
                            if(ActionManager.verifyCoordinateConstruction(workerSelected, coordX, coordY)){
                                TurnManager.construction(coordX, coordY);
                            }
                        }
                    }catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                        clientConnection.asyncSend("Formato Input scorretto!");
                    }
                    //send message next turn;
                }
            }

        }

        private void actionHephaestus(ClientMessage message, Player player, Worker workerSelected){
            int coordX, coordY;

            switch (message.getGameState()) {
                case MOVEMENT -> {
                    try {
                        String[] coordString = message.getContent().replace(" ", "").split(",");
                        coordX = Integer.parseInt(coordString[0]);
                        coordY = Integer.parseInt(coordString[1]);
                        if(ActionManager.verifyCoordinateMovement(workerSelected, player.getGodChoice(), coordX, coordY)){
                            if(TurnManager.movement(coordX, coordY)){
                                //send message win;
                            }else{
                                //send message construction;
                            }
                        }
                    } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                        clientConnection.asyncSend("Formato Input scorretto!");
                    }
                }
                case QUESTION_HEPHAESTUS -> {
                    if(workerSelected.canBuild()){
                        String answer = message.getContent().toLowerCase().replace(" ", "");
                        if(answer.equals("yes")) {
                            if(workerSelected.canBuild(true)) {
                                //send message double_construction;
                            } else {
                                clientConnection.asyncSend(workerSelected.getIdWorker() + " NON può costruire 2 blocchi!");
                                //send message construction;
                            }
                        } else{
                            //send message construction;
                        }
                    } else{
                        System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
                        //send message next turn;
                    }
                }
                case DOUBLE_CONSTRUCTION -> {
                    try {
                        String[] coordString = message.getContent().replace(" ", "").split(",");
                        coordX = Integer.parseInt(coordString[0]);
                        coordY = Integer.parseInt(coordString[1]);
                        if(ActionManager.verifyCoordinateConstruction(workerSelected, coordX, coordY)){                            //return new int[]{coordX, coordY};
                            workerSelected.buildBlock(true, coordX, coordY);
                        }
                    } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                        clientConnection.asyncSend("Formato Input scorretto!");
                    }
                    //send message next turn;
                }
                case CONSTRUCTION -> {
                    try {
                        String[] coordString = message.getContent().replace(" ", "").split(",");
                        coordX = Integer.parseInt(coordString[0]);
                        coordY = Integer.parseInt(coordString[1]);
                        if(ActionManager.verifyCoordinateConstruction(workerSelected, coordX, coordY)){                            //return new int[]{coordX, coordY};
                            workerSelected.buildBlock(false, coordX, coordY);
                        }
                    } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                        clientConnection.asyncSend("Formato Input scorretto!");
                    }
                    //send message next turn;
                }
            }
        }

    }

}
