package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Cards.Deck;
import it.polimi.ingsw.model.Cards.God;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.view.ViewGame;
import it.polimi.ingsw.view.ViewModel;

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
        System.out.println("Avrai il colore " + players.get(0).getColor().toString() + ".\n");
    }

    public static int addPlayer(String nickname){
        System.out.print("Sei il giocatore numero " + (players.size()+1) + " .");
        System.out.println("Attendi che " + players.get(players.size()-1).getNickname() + " concluda la sua configurazione.");
        players.add(new Player(nickname));
        System.out.println("Avrai il colore " + players.get(players.size()-1).getColor().toString() + ".\n");
        return players.size()-1;
    }

    public static void choiceOfCard(int indexOfPlayer, int cardNumber){
        God g = Deck.getCardToPlayer(cardNumber);
        players.get(indexOfPlayer).setGodChoice(g);
        ViewModel.printCardChosen(g);
    }

    public static void startGame(){
        for (int i = 0; i < players.size(); i++){
            firstTurn(i);
        }
        int currPlayer = 0;
        while (!victory){
            System.out.println("\n*VISUALE " + players.get(currPlayer).getNickname() + "*\n");
            ViewModel.printMap();
            for (Player player : players) {
                ViewModel.printWorkersPositions(player);
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

    public static void deletePlayer(Player player){
        Iterator<Player> i = players.iterator();
        while (i.hasNext()) {
            Player currPlayer = i.next();
            if (currPlayer.equals(player)) {
                System.out.println("Hai perso!");
                Map.deleteWorkerInCell(currPlayer.getWorkerSelected(1));
                Map.deleteWorkerInCell(currPlayer.getWorkerSelected(2));
                i.remove();
                System.out.println("\n*CONNESSIONE CLIENT CHIUSA*\n");
                break;
            }
        }
    }

    private static void firstTurn(int indexOfPlayer){
        ViewModel.printMap();
        if (indexOfPlayer > 0){
            ViewModel.printWorkersPositions(GameManager.getPlayersInGame()[0]);
            if (indexOfPlayer > 1) {
                ViewModel.printWorkersPositions(GameManager.getPlayersInGame()[1]);
            }
        }
        ViewGame.setWorker(players.get(indexOfPlayer), 1);
        ViewGame.setWorker(players.get(indexOfPlayer), 2);
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
            ViewModel.printWorkersPositions(currPlayer);
        }
    }

}
