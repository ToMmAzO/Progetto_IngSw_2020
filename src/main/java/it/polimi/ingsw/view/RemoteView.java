package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.SystemMessage;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.Deck;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.server.SocketClientConnection;
import it.polimi.ingsw.observer.Observer;

public class RemoteView {

    private final SocketClientConnection clientConnection;
    private final Player player;

    public RemoteView(Player player, SocketClientConnection c) {
        this.player = player;
        this.clientConnection = c;
        Game.getInstance().addObserver(new ChangeState());
        SystemMessage.getInstance().addObserver(new Error());
    }

    public ChangeInDeck createChangeInDeck(){
        return new ChangeInDeck();
    }

    public ChangeInMap createChangeInMap(){
        return new ChangeInMap();
    }

    public void messageReceiver(ClientMessage message) {
        if (player.equals(GameManager.getInstance().getCurrPlayer())){
            //inutile
            if(Game.getInstance().getGameState(player).equals(message.getGameState())){
                readMessage(message);
            } else{
                GameManager.getInstance().cheating();
            }
        } else{
            clientConnection.asyncSend("Non è il tuo turno! Attendi.");
        }
    }

    private void readMessage(ClientMessage message) {
        switch (message.getGameState()) {
            case WELCOME_FIRST -> {
                try {
                    int numberChosen = Integer.parseInt(message.getContent());
                    GameManager.getInstance().setNumberOfPlayers(numberChosen);
                } catch (IllegalArgumentException e) {
                    clientConnection.asyncSend("Formato Input scorretto! Scrivi 2 oppure 3:");
                }
            }
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

    private void turn(ClientMessage message) {
        switch (message.getGameState()) {
            case WORKER_CHOICE -> {
                try {
                    int selectionWorker = Integer.parseInt(message.getContent());
                    TurnManager.getInstance().workerChoice(selectionWorker);
                } catch (IllegalArgumentException e) {
                    clientConnection.asyncSend("Formato Input scorretto! Scrivi 1 oppure 2: ");
                }
            }
            case PREBUILD_PROMETHEUS -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().prebuildPrometheus(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
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
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            case CONSTRUCTION_CUPOLA -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().constructionCupola(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            case DOUBLE_CONSTRUCTION -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().doubleConstruction(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            case CONSTRUCTION -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().construction(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            case SECOND_CONSTRUCTION_DEMETER -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().secondConstructionDemeter(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            case SECOND_CONSTRUCTION_HESTIA -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().secondConstructionHestia(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend("Formato Input scorretto!");
                }
            }
            default -> {
                String answer = message.getContent().toLowerCase().replace(" ", "");
                if (answer.equals("yes")) {
                    switch (message.getGameState()) {   //BOH, prima message
                        case QUESTION_ARTEMIS -> Game.getInstance().setGameState(player, GameState.SECOND_MOVE);
                        case QUESTION_ATLAS -> Game.getInstance().setGameState(player, GameState.CONSTRUCTION_CUPOLA);
                        case QUESTION_DEMETER -> Game.getInstance().setGameState(player, GameState.SECOND_CONSTRUCTION_DEMETER);
                        case QUESTION_HEPHAESTUS -> Game.getInstance().setGameState(player, GameState.DOUBLE_CONSTRUCTION);
                        case QUESTION_HESTIA -> Game.getInstance().setGameState(player, GameState.SECOND_CONSTRUCTION_HESTIA);
                        case QUESTION_PROMETHEUS -> Game.getInstance().setGameState(player, GameState.PREBUILD_PROMETHEUS);
                        case QUESTION_TRITON -> Game.getInstance().setGameState(player, GameState.MOVEMENT);
                    }
                } else if (answer.equals("no")) {
                    switch (message.getGameState()) {
                        case QUESTION_DEMETER -> {
                            Game.getInstance().setGameState(player, GameState.WAIT_TURN);
                            GameManager.getInstance().nextPlayer(player);
                        }
                        case QUESTION_HESTIA -> {
                            Game.getInstance().setGameState(player, GameState.WAIT_TURN);
                            GameManager.getInstance().nextPlayer(player);
                        }
                        case QUESTION_PROMETHEUS -> clientConnection.asyncSend(new Message_Movement());
                        default -> Game.getInstance().setGameState(player, GameState.CONSTRUCTION);             //QUESTION_ARTEMIS, QUESTION_ATLAS, QUESTION_HEPHAESTUS, QUESTION_TRITON
                    }
                } else {
                    clientConnection.asyncSend("Puoi rispondere solo con yes o no!");
                }
            }
        }
    }

    private class ChangeState implements Observer<GameState> {

        @Override
        public void update(GameState message){

            if (player.equals(GameManager.getInstance().getCurrPlayer())){
                if(player.equals(GameManager.getInstance().getPlayersInGame()[0])){
                    switch(message){
                        case CARD_CHOICE -> clientConnection.asyncSend(Deck.getInstance());
                        case SET_WORKER -> clientConnection.asyncSend(Map.getInstance());
                    }
                }
                clientConnection.asyncSend(message);
                if(message.equals(GameState.WAIT_PLAYERS)){
                    clientConnection.asyncSend("Avrai il colore " + player.getColor().toString() + ".");
                }
            }
        }

    }

    private class Error implements Observer<String> {

        @Override
        public void update(String message){
            if (player.equals(GameManager.getInstance().getCurrPlayer())){
                clientConnection.asyncSend(message);
                if(message.equals(SystemMessage.getInstance().cheating)){
                    clientConnection.asyncSend("Hai decretato la fine della partita!");
                }
            } else{
                if(message.equals(SystemMessage.getInstance().cheating)){
                    clientConnection.asyncSend(message);
                    clientConnection.asyncSend(GameManager.getInstance().getCurrPlayer().getNickname() + " ha tentato di barare.\nGAME OVER!");
                }
            }
        }

    }

    private class ChangeInDeck implements Observer<God> {

        @Override
        public void update(God message){
            if(player.equals(GameManager.getInstance().getCurrPlayer())){
                clientConnection.asyncSend(message);
            } else{
                clientConnection.asyncSend(GameManager.getInstance().getCurrPlayer().getNickname() + " ha scelto la carta "
                + message.toString() + ".");
                clientConnection.asyncSend(Deck.getInstance());
            }
        }

    }

    private class ChangeInMap implements Observer<Player> {

        @Override
        public void update(Player message){
            if (!player.equals(message)){
                clientConnection.asyncSend("Azione di " + message.getNickname() + ".");
            }
            clientConnection.asyncSend(Map.getInstance());
            Player[] players = GameManager.getInstance().getPlayersInGame();
            for (Player value : players){
                if(value.getWorkerSelected(1) != null && value.getWorkerSelected(2) != null){
                    clientConnection.asyncSend(value);
                }
            }
        }

    }

}