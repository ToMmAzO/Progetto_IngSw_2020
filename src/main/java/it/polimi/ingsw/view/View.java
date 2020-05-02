package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Cards.Deck;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.network.message.ClientMessage;
import it.polimi.ingsw.network.server.ClientConnection;

public class View {

    private final ClientConnection clientConnection;
    private final String nickname;

    public View(String nickname, ClientConnection c) {
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
                        //fine turno, aggiornare il client dopo ogni posizionamento avversario
                    } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                        clientConnection.asyncSend("Formato Input scorretto!");
                    }
                }
            }
        }

    }

}
