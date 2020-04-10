package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Cards.Deck;
import it.polimi.ingsw.model.Cards.God;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.View;

import java.util.Objects;
import java.util.Scanner;

public class GameManager {

    private static Player[] players;
    private static int numberOfPlayers;
    private static boolean victory = false;

    private static void setNumberOfPlayers(int numberOfPlayers) {
        GameManager.numberOfPlayers = numberOfPlayers;
    }

    public static void setVictory(){
        victory = true;
    }

    public static void addPlayer(int numberConnection, String nickname){
        Scanner scanner = new Scanner(System.in);
        switch (numberConnection){
            case 0:{
                System.out.println("\n*VISUALE CLIENT " + numberConnection + "*\n");
                System.out.println("Sei il primo giocatore ad unirsi alla lobby.");
                System.out.println("Scegli quanti giocatori avrà la partita (min: 2, max: 3).");
                int numberOfPlayers= Integer.parseInt((scanner.nextLine()));
                while (numberOfPlayers != 2 && numberOfPlayers != 3){
                    System.out.println("Devi inserire 2 oppure 3!");
                    numberOfPlayers= Integer.parseInt((scanner.nextLine()));
                }
                setNumberOfPlayers(numberOfPlayers);
                new Deck(numberOfPlayers);
                new Map();
                players = new Player[numberOfPlayers];
                players[0] = new Player(nickname);
                System.out.println("Avrai il colore " + players[0].getColor().toString() + ".");
                break;
            }
            case 1:{
                System.out.println("\n*VISUALE CLIENT " + numberConnection + "*\n");
                players[1] = new Player(nickname);
                System.out.println("Sei il secondo giocatore ad unirsi alla lobby, avrai il colore " + players[1].getColor().toString() + ".");
                System.out.println("Attendi che " + players[0].getNickname() + " concluda la sua configurazione.");
                break;
            }
            case 2:{
                System.out.println("\n*VISUALE CLIENT " + numberConnection + "*\n");
                players[2] = new Player (nickname);
                System.out.println("Sei il terzo giocatore ad unirsi alla lobby, avrai il colore " + players[2].getColor().toString() + ".");
                System.out.println("Attendi che " + players[1].getNickname() + " concluda la sua configurazione.");
                break;
            }
        }
        choiceOfCard(numberConnection);
    }

    public static void startGame(){
        int currPlayer;
        for (currPlayer = 0; currPlayer < numberOfPlayers; currPlayer++){
            System.out.println("\n*VISUALE CLIENT " + currPlayer + "*\n");
            View.printMap();
            if (currPlayer > 0){
                View.printWorkersPositions(players[0]);
                if (currPlayer > 1) {
                    View.printWorkersPositions(players[1]);
                }
            }
            positioningWorkerOnMap(currPlayer, 1);
            positioningWorkerOnMap(currPlayer, 2);
        }
        currPlayer = 0;
        while (!victory){
            System.out.println("\n*VISUALE CLIENT " + currPlayer + "*\n");
            View.printMap();
            for (int i = 0; i < numberOfPlayers; i++){
                if (players[i] != null){
                    View.printWorkersPositions(players[i]);
                }
            }
            TurnManager.startTurn(Objects.requireNonNull(players[currPlayer]));
            if (!victory){
                if (players[currPlayer] == null){
                    currPlayer = nextPlayer(currPlayer);
                    if (numberOfPlayers == 2){
                        setVictory();
                    } else if (numberOfPlayers == 3){
                        if (players[currPlayer] == null){
                            currPlayer = nextPlayer(currPlayer);
                            setVictory();
                        }
                    }
                } else{
                    currPlayer = nextPlayer(currPlayer);
                }
            }
        }
        endGame(players[currPlayer]);
    }

    public static void deletePlayer(Player player){
        int i = 0;
        while (!players[i].equals(player)){
            i++;
        }
        System.out.println("Hai perso!");
        Map.deleteWorkerInCell(players[i].getWorkerSelected(1));
        Map.deleteWorkerInCell(players[i].getWorkerSelected(2));
        players[i] = null;
        System.out.println("\n*CONNESSIONE CLIENT CHIUSA*\n");
    }

    private static void choiceOfCard(int indexOfPlayer){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Scegli il numero di una delle " + numberOfPlayers + " carte disponibili:");
        View.printCardsSelected();
        int cardNumber = Integer.parseInt((scanner.nextLine()));
        while ((cardNumber < 1 || cardNumber > numberOfPlayers) || (!Deck.getAvailability()[cardNumber - 1])){
            System.out.println("Devi inserire il numero corrispondente a una delle carte e deve essere disponibile!");
            cardNumber= Integer.parseInt((scanner.nextLine()));
        }
        God g = Deck.getCardToPlayer(cardNumber);
        players[indexOfPlayer].setGodChoice(g);
        View.printCardChosen(g);
    }

    private static void positioningWorkerOnMap(int indexOfPlayer, int numberOfWorker){
        System.out.println("Inserisci le cordinate in cui vuoi posizionare il lavoratore " + (numberOfWorker) + ".");
        Scanner scanner = new Scanner(System.in);
        String[] coordString = scanner.nextLine().split(",");
        int coordRow = Integer.parseInt(coordString[0]);
        int coordColumn = Integer.parseInt(coordString[1]);
        boolean exitCondition = false;
        while (!exitCondition){
            if ((coordRow >= 0 && coordRow <= 4) && (coordColumn >= 0 && coordColumn <= 4)){
                switch (numberOfWorker){
                    case 1:{
                        if (players[indexOfPlayer].setWorker1(coordRow, coordColumn)){
                            exitCondition = true;
                            continue;
                        } else{
                            break;
                        }
                    }
                    case 2:{
                        if (players[indexOfPlayer].setWorker2(coordRow, coordColumn)){
                            exitCondition = true;
                            continue;
                        } else{
                            break;
                        }
                    }
                }
                System.out.println("È già presente un lavoratore quì, inserisci delle altre coordinate!");
            } else{
                System.out.println("Inserisci delle coordinate valide!");
            }
            coordString = scanner.nextLine().split(",");
            coordRow = Integer.parseInt(coordString[0]);
            coordColumn = Integer.parseInt(coordString[1]);
        }
    }

    private static int nextPlayer(int indexOfActualPlayer){
        if (indexOfActualPlayer == numberOfPlayers - 1){
            return 0;
        } else{
            return indexOfActualPlayer + 1;
        }
    }

    private static void endGame(Player winnerPlayer){
        int i = 0;
        while (!players[i].equals(winnerPlayer)){
            i++;
        }
        String winner = players[i].getNickname();
        System.out.println("\n*VISUALE CLIENT " + i + "*\n");
        System.out.println("Congratulazioni, hai vinto la partita!");
        players[i] = null;
        System.out.println("\n*CONNESSIONE CLIENT CHIUSA*\n");
        for (i = 0; i < numberOfPlayers; i++){
            if (players[i] != null){
                System.out.println("\n*VISUALE CLIENT " + 1 + "*\n");
                System.out.println("GAME OVER! " + winner + " ha vinto.");
                System.out.println("\n*CONNESSIONE CLIENT CHIUSA*\n");
            }
        }
    }

}
