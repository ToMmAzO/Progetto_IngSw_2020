package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.board.MapCopy;
import it.polimi.ingsw.model.cards.DeckCopy;
import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameState;
import it.polimi.ingsw.model.game.SystemMessage;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.observer.Observer;

/**
 * This class represents the client virtual view into the server and it's associated with a specific socket.
 */
public class SocketRemoteView {

    private final SocketClientConnection clientConnection;
    private final Player player;

    /**
     * When a SocketRemoteView is created, observers of the model are also initialized.
     *
     * @param player is the entity of client in this game.
     * @param c is the client socket.
     */
    public SocketRemoteView(Player player, SocketClientConnection c) {
        this.player = player;
        this.clientConnection = c;
        Game.getInstance().addObserver(new ChangeState());
        SystemMessage.getInstance().addObserver(new Error());
        Map.getInstance().addObserver(new ChangeInMap());
    }

    /**
     * This method initializes the Deck observer, that can`t be initialized before knowing the number of players in game.
     */
    public ChangeInDeck createChangeInDeck(){
        return new ChangeInDeck();
    }

    /**
     * @param message is the messages received from the client.
     */
    public void messageReceiver(ClientMessage message) {
        if (player.equals(GameManager.getInstance().getCurrPlayer())){
            readMessage(message);
        } else{
            clientConnection.asyncSend("It's not your turn! Wait.");
        }
    }

    /**
     * This method analyzes all client messages and consult the controller.
     *
     * @param message is a client message.
     */
    private void readMessage(ClientMessage message) {
        switch (message.getGameState()) {
            case WELCOME_FIRST -> {
                try {
                    int numberChosen = Integer.parseInt(message.getContent());
                    GameManager.getInstance().setNumberOfPlayers(numberChosen);
                } catch (IllegalArgumentException e) {
                    clientConnection.asyncSend(SystemMessage.getInstance().contentError);
                }
            }
            case WAIT_PLAYERS -> clientConnection.asyncSend("Wait your turn!");
            case CARD_CHOICE -> {
                try {
                    int cardNumber = Integer.parseInt(message.getContent());
                    GameManager.getInstance().choiceOfCard(cardNumber);
                } catch (IllegalArgumentException e) {
                    clientConnection.asyncSend(SystemMessage.getInstance().contentError);
                }
            }
            case SET_WORKER -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    GameManager.getInstance().setWorker(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend(SystemMessage.getInstance().contentError);
                }
            }
            default -> turn(message);
        }
    }

    /**
     * This method is connected with readMessage and manages the game when it actually begins.
     *
     * @param message is the client message
     */
    private void turn(ClientMessage message) {
        switch (message.getGameState()) {
            case WORKER_CHOICE -> {
                try {
                    int selectionWorker = Integer.parseInt(message.getContent());
                    TurnManager.getInstance().workerChoice(selectionWorker);
                } catch (IllegalArgumentException e) {
                    clientConnection.asyncSend(SystemMessage.getInstance().contentError);
                }
            }
            case PREBUILD_PROMETHEUS -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().prebuildPrometheus(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend(SystemMessage.getInstance().contentError);
                }
            }
            case MOVEMENT -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().movement(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend(SystemMessage.getInstance().contentError);
                }
            }
            case SECOND_MOVE -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().secondMove(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend(SystemMessage.getInstance().contentError);
                }
            }
            case CONSTRUCTION_CUPOLA -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().constructionCupola(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend(SystemMessage.getInstance().contentError);
                }
            }
            case DOUBLE_CONSTRUCTION -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().doubleConstruction(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend(SystemMessage.getInstance().contentError);
                }
            }
            case CONSTRUCTION -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().construction(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend(SystemMessage.getInstance().contentError);
                }
            }
            case SECOND_CONSTRUCTION_DEMETER -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().secondConstructionDemeter(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend(SystemMessage.getInstance().contentError);
                }
            }
            case SECOND_CONSTRUCTION_HESTIA -> {
                try {
                    String[] coordString = message.getContent().replace(" ", "").split(",");
                    TurnManager.getInstance().secondConstructionHestia(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]));
                } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                    clientConnection.asyncSend(SystemMessage.getInstance().contentError);
                }
            }
            default -> {
                String answer = message.getContent().toLowerCase().replace(" ", "");
                if (answer.equals("yes")) {
                    switch (message.getGameState()) {
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
                        case QUESTION_DEMETER, QUESTION_HESTIA -> {
                            Game.getInstance().setGameState(player, GameState.WAIT_TURN);
                            GameManager.getInstance().nextPlayer(player);
                        }
                        case QUESTION_PROMETHEUS -> Game.getInstance().setGameState(player, GameState.MOVEMENT);
                        default -> Game.getInstance().setGameState(player, GameState.CONSTRUCTION);
                    }
                } else {
                    clientConnection.asyncSend(SystemMessage.getInstance().contentError);
                }
            }
        }
    }

    /**
     * This private class is observer of the model for GameState.
     */
    private class ChangeState implements Observer<GameState> {

        /**
         * This method is the implementation of update(T) method in observer class and is used to notify the client.
         *
         * @param message is a GameState.
         */
        @Override
        public void update(GameState message){
            if(player.equals(GameManager.getInstance().getCurrPlayer())){
                if(player.equals(GameManager.getInstance().getPlayersInGame()[0])){
                    if(message == GameState.SET_WORKER) {
                        if (player.getWorkerSelected(1) == null) {
                            clientConnection.asyncSend(new MapCopy());
                        }
                    }
                }
                switch(message){
                    case CARD_CHOICE -> clientConnection.asyncSend(new DeckCopy());
                    case WAIT_PLAYERS -> clientConnection.asyncSend(player.getColor());
                    case INVALIDATION -> {
                        return;
                    }
                }
                clientConnection.asyncSend(message);
                if(message == GameState.WIN) {
                    clientConnection.closeConnection();
                }
            } else{
                switch(message){
                    case SET_WORKER -> {
                        if(GameManager.getInstance().getCurrPlayer().equals(GameManager.getInstance().getPlayersInGame()[0])){
                            if(GameManager.getInstance().getCurrPlayer().getWorkerSelected(1) == null){
                                clientConnection.asyncSend(new MapCopy());
                            }
                        }
                    }
                    case WIN -> {
                        clientConnection.asyncSend(GameManager.getInstance().getCurrPlayer().getNickname() + " has won!");
                        clientConnection.asyncSend(GameState.LOSE);
                        clientConnection.closeConnection();
                    }
                    case LOSE -> {
                        clientConnection.asyncSend(GameManager.getInstance().getCurrPlayer().getNickname() + " has lost!");
                        mapUpdate();
                    }
                    case INVALIDATION -> {
                        clientConnection.asyncSend(message);
                        clientConnection.closeConnection();
                    }
                }
            }
        }

    }

    /**
     * This private class is observer of the model for String.
     */
    private class Error implements Observer<String> {

        /**
         * This method is the implementation of update(T) method in observer class and is used to notify the client.
         *
         * @param message is a system string message.
         */
        @Override
        public void update(String message){
            if ((player.equals(GameManager.getInstance().getCurrPlayer()))){
                clientConnection.asyncSend(message);
            }
        }

    }

    /**
     * This private class is observer of the model for God.
     */
    private class ChangeInDeck implements Observer<God> {

        /**
         * This method is the implementation of update(T) method in observer class and is used to notify the client.
         *
         * @param message is a God card.
         */
        @Override
        public void update(God message){
            if(player.equals(GameManager.getInstance().getCurrPlayer())){
                clientConnection.asyncSend(("\nYou have the power of " + message.toString() + ": " + God.getGodDescription(message)));
            } else{
                clientConnection.asyncSend(GameManager.getInstance().getCurrPlayer().getNickname() + " has chosen the card "
                + message.toString() + ".");
            }
        }

    }

    /**
     * This private class is observer of the model for Player.
     */
    private class ChangeInMap implements Observer<Player> {

        /**
         * This method is the implementation of update(T) method in observer class and is used to notify the client.
         *
         * @param message is a Player in game.
         */
        @Override
        public void update(Player message){
            if(!player.equals(message)){
                clientConnection.asyncSend("Action of " + message.getNickname() + ".");
            }
            mapUpdate();
        }

    }

    /**
     * This method is used to send an updated map copy and the level of every workers of players in game (used only in Cli).
     */
    private void mapUpdate(){
        clientConnection.asyncSend(new MapCopy());
        Player[] players = GameManager.getInstance().getPlayersInGame();
        if(players[players.length - 1].getWorkerSelected(2) != null){
            for(Player p : players){
                if(p.getWorkerSelected(1) != null && p.getWorkerSelected(2) != null){
                    clientConnection.asyncSend(p.getNickname() + ": " +
                            p.getWorkerSelected(1).getIdWorker() + " (level " + p.getWorkerSelected(1).getCoordZ() + "), " +
                            p.getWorkerSelected(2).getIdWorker() + " (level " + p.getWorkerSelected(2).getCoordZ() + ")."
                    );
                }
            }
        }
    }

}