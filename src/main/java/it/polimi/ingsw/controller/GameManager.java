package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Cards.Deck;
import it.polimi.ingsw.model.Cards.God;
import it.polimi.ingsw.model.Player.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class GameManager {

    private static final ArrayList<Player> players = new ArrayList<>();
    private static final Scanner scanner= new Scanner(System.in);
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
        System.out.print("Sei il giocatore numero " + (players.size()+1) + ". ");
        System.out.println("Attendi che " + players.get(players.size()-1).getNickname() + " concluda la sua configurazione.");
        players.add(new Player(nickname));
        System.out.println("Avrai il colore " + players.get(players.size()-1).getColor().toString() + ".\n");
        return players.size()-1;
    }

    public static void choiceOfCard(int indexOfPlayer, int cardNumber){
        God g = Deck.getInstance().getCardToPlayer(cardNumber);
        players.get(indexOfPlayer).setGodChoice(g);
        God.printCardChosen(g);
    }

    public static void startGame(){
        for (int i = 0; i < players.size(); i++){
            firstTurn(i);
        }
        int currPlayer = 0;
        while (!victory){
            System.out.println("\n*VISUALE " + players.get(currPlayer).getNickname() + "*\n");
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

    private static void firstTurn(int indexOfPlayer){
        Map map = Map.getInstance();
        map.print();
        if (indexOfPlayer > 0){
            GameManager.getPlayersInGame()[0].printWorkersPositions();
            if (indexOfPlayer > 1) {
                GameManager.getPlayersInGame()[1].printWorkersPositions();
            }
        }
        setWorker(players.get(indexOfPlayer), 1);
        setWorker(players.get(indexOfPlayer), 2);
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

    public static void joinGameFirst(String nickname){
        System.out.println("Sei il primo giocatore ad unirsi alla lobby.");
        System.out.print("Scegli quanti giocatori avrà la partita. ");
        int numberChosen = 0;
        while (numberChosen != 2 && numberChosen != 3){
            try{
                System.out.print("Scrivi 2 oppure 3: ");
                numberChosen= Integer.parseInt((scanner.nextLine()));
            } catch(IllegalArgumentException e){
                System.out.print("Formato Input scorretto! ");
            }
        }
        addFirstPlayer(nickname, numberChosen);
        godChoice(0);
    }

    public static void joinGame(String nickname){
        int playerIndex = addPlayer(nickname);
        godChoice(playerIndex);
    }

    public static void setWorker(Player player, int numberOfWorker){
        System.out.print("Posizionamento lavoratore " + (numberOfWorker) + ". ");
        String[] coordString;
        int coordRow;
        int coordColumn;
        while (true){
            try{
                System.out.print("Inserisci delle coordinate x, y: ");
                coordString = scanner.nextLine().replace(" ", "").split(",");
                coordRow = Integer.parseInt(coordString[0]);
                coordColumn = Integer.parseInt(coordString[1]);
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
            } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                System.out.print("Formato Input scorretto! ");
            }
        }
    }

    private static void godChoice(int playerIndex){
        Deck.getInstance().printCards();
        int cardNumber = 0;
        while (!Deck.getInstance().isAvailable(cardNumber)){
            try{
                System.out.print("Scegli il numero di una delle " + getNumberOfPlayers() + " carte ancora disponibili: ");
                cardNumber= Integer.parseInt((scanner.nextLine()));
            } catch(IllegalArgumentException e){
                System.out.print("Formato Input scorretto! ");
            }
        }
        choiceOfCard(playerIndex, cardNumber);
    }
}
