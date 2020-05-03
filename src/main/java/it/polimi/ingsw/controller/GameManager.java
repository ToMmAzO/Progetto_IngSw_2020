package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Cards.Deck;
import it.polimi.ingsw.model.Player.Player;

import java.util.ArrayList;
import java.util.Iterator;

public class GameManager {

    private static final ArrayList<Player> players = new ArrayList<>();
    private static int numberOfPlayers;
    private static boolean victory = false;

    public static int getNumberOfPlayers(){
        return numberOfPlayers;
    }

    public static Player[] getPlayersInGame(){
        if(players.size() == 1){
            return new Player[]{players.get(0)};
        } else if(players.size() == 2){
            return new Player[]{players.get(0), players.get(1)};
        } else{
            return new Player[]{players.get(0), players.get(1), players.get(2)};
        }
    }

    public static void setVictory(){
        victory = true;
    }

    public static void addFirstPlayer(String nickname, int numberOfPlayers){
        GameManager.numberOfPlayers = numberOfPlayers;
        new Deck(numberOfPlayers);
        new Map();
        players.add(0, new Player(nickname));
    }

    public static void addPlayer(String nickname){
        players.add(new Player(nickname));
    }

    public static void choiceOfCard(Player player, int cardNumber){
        player.setGodChoice(Deck.getInstance().getCardToPlayer(cardNumber));
    }

    public static void setWorker(Player player, int coordRow, int coordColumn, int numberOfWorker){
        if (ActionManager.validCoords(coordRow, coordColumn)){
            switch (numberOfWorker) {
                case 1 -> {
                    if (player.setWorker1(coordRow, coordColumn)) {
                        return;
                    }
                }
                case 2 -> {
                    if (player.setWorker2(coordRow, coordColumn)) {
                        return;
                    }
                }
            }
            System.out.print("È già presente un lavoratore quì! ");
        } else{
            System.out.print("Puoi inserire solo interi da 0 a 4! ");
        }
    }

    /*
    //si inserisce un attributo currplayer privato e si passa quello a start turn
    //currplayer alla fine del turno viene aggiornato normalmente con nextplayer
    public static void startGame(){//diventa start turn

        for (int i = 0; i < players.size(); i++){
            firstTurn(i);
        }


        int currPlayer = 0;
        while (!victory){//via il currplayer e il while

            stampa visuale

            Map map = Map.getInstance();
            map.print();
            for (Player player : players) {
                player.printWorkersPositions();
            }



            if (TurnManager.startTurn((players.get(currPlayer)))){
                if(!victory){
                    currPlayer = nextPlayer(currPlayer);
                }
            } else {
                if (players.size() == 1){
                    currPlayer = 0;
                    setVictory();
                } else{
                    if (currPlayer == 2){
                        currPlayer = 0;
                    }
                }
            }
        }
        endGame(players.get(currPlayer));
    }
    */

    public static void deletePlayer(Player player){
        Iterator<Player> i = players.iterator();
        while (i.hasNext()) {
            Player currPlayer = i.next();
            if (currPlayer.equals(player)) {
                System.out.println("Hai perso!");
                Map map = Map.getInstance();
                map.deleteWorkerInCell(currPlayer.getWorkerSelected(1));
                map.deleteWorkerInCell(currPlayer.getWorkerSelected(2));
                i.remove();
                System.out.println("\n*CONNESSIONE CLIENT CHIUSA*\n");
                break;
            }
        }
    }

    private static int nextPlayer(int indexOfActualPlayer){
        if (indexOfActualPlayer == players.size() - 1){
            return 0;
        } else{
            return indexOfActualPlayer + 1;
        }
    }

    private static void endGame(Player winnerPlayer){
        String winner = null;
        Iterator<Player> i = players.iterator();
        while (i.hasNext()) {
            Player currPlayer = i.next();
            if (currPlayer.equals(winnerPlayer)) {
                winner = currPlayer.getNickname();
                System.out.println("\n*VISUALE " + currPlayer.getNickname() + "*\n");
                System.out.println("Congratulazioni, hai vinto la partita!");
                i.remove();
                System.out.println("\n*CONNESSIONE CLIENT CHIUSA*\n");
                break;
            }
        }
        if(players.size() > 1) {
            for (Player player : players) {
                System.out.println("\n*VISUALE CLIENT " + player.getNickname() + "*\n");
                System.out.println("GAME OVER! " + winner + " ha vinto.");
                System.out.println("\n*CONNESSIONE CLIENT CHIUSA*\n");
            }
        }
    }

    protected static void printPlayerInGame(){
        for (Player currPlayer : players) {
            currPlayer.printWorkersPositions();
        }
    }

}
