package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Cards.God;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.View;

import java.util.Scanner;

public class GameManager {

    private static Player[] players;
    private static Boolean[] inGame;
    private static int numberOfPlayers;
    private static Boolean victory = false;

    public static void setNumberOfPlayers(int numberOfPlayers) {
        GameManager.numberOfPlayers = numberOfPlayers;
    }

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public static void setVictory(){
        victory = true;
    }

    public static void addPlayer(int numberConnection, String nickname){
        switch (numberConnection){
            case 0:{
                System.out.println("\n*VISUALE CLIENT " + numberConnection + "*\n");
                System.out.println("Sei il primo giocatore ad unirsi alla lobby.");
                System.out.println("Scegli quanti giocatori avrà la partita (min: 2, max: 3).");
                Scanner scanner = new Scanner(System.in);
                int numberOfPlayers= Integer.parseInt((scanner.nextLine()));
                setNumberOfPlayers(numberOfPlayers);
                new Game();
                new Map();
                players = new Player[numberOfPlayers];
                inGame = new Boolean[numberOfPlayers];
                players[0] = new Player(nickname);
                inGame[0] = true;
                System.out.println("Hai il colore " + players[0].getColor().toString());
                System.out.println("Scegli il numero di una delle " + GameManager.numberOfPlayers + " carte:");
                View.printCardsSelected();
                God g = Game.getCardToPlayer(Integer.parseInt((scanner.nextLine())));
                players[0].setGodChoice(g);
                View.printCardChosen(g);
                break;
            }
            case 1:{
                System.out.println("\n*VISUALE CLIENT " + numberConnection + "*\n");
                System.out.println("Sei il secondo giocatore ad unirsi alla lobby.");
                System.out.println("Attendi che " + players[0].getNickname() + " concluda la sua configurazione.");
                Scanner scanner = new Scanner(System.in);
                players[1] = new Player(nickname);
                inGame[1] = true;
                System.out.println("Hai il colore " + players[1].getColor().toString());
                System.out.println("Scegli il numero di una delle " + numberOfPlayers + " carte ancora disponibili:");
                View.printCardsSelected();
                int cardNumber = Integer.parseInt((scanner.nextLine()));
                while (!Game.getAvailability()[cardNumber - 1]){
                    System.out.println("Carta non disponibile, seleziona una carta disponibile!");
                    cardNumber = Integer.parseInt((scanner.nextLine()));
                }
                God g = Game.getCardToPlayer(cardNumber);
                players[1].setGodChoice(g);
                View.printCardChosen(g);
                break;
            }
            case 2:{
                System.out.println("\n*VISUALE CLIENT " + numberConnection + "*\n");
                System.out.println("Sei il terzo giocatore ad unirsi alla lobby.");
                System.out.println("Attendi che " + players[1].getNickname() + " concluda la sua configurazione.");
                Scanner scanner = new Scanner(System.in);
                players[2] = new Player (nickname);
                inGame[2] = true;
                System.out.println("Hai il colore " + players[2].getColor().toString());
                System.out.println("Scegli il numero di una delle  " + numberOfPlayers + " carte ancora disponibili:");
                View.printCardsSelected();
                int cardNumber = Integer.parseInt((scanner.nextLine()));
                while (!Game.getAvailability()[cardNumber - 1]){
                    System.out.println("Carta non disponibile, seleziona il numero di una disponibile!");
                    cardNumber = Integer.parseInt((scanner.nextLine()));
                }
                God g = Game.getCardToPlayer(cardNumber);
                players[2].setGodChoice(g);
                View.printCardChosen(g);
                break;
            }
        }
    }

    public static void startGame(){
        Scanner scanner = new Scanner(System.in);
        int currPlayer;
        //posizionamento lavoratori
        for (currPlayer = 0; currPlayer < getNumberOfPlayers(); currPlayer++){
            System.out.println("\n*VISUALE CLIENT " + currPlayer + "*\n");
            View.printMap();
            switch(currPlayer){
                case 1:{
                    View.printWorkersPositions(players[0]);
                    break;
                }
                case 2:{
                    View.printWorkersPositions(players[0]);
                    View.printWorkersPositions(players[1]);
                    break;
                }
            }
            String[] coordString;
            System.out.println("Inserisci le cordinate in cui vuoi posizionare il primo lavoratore.");
            coordString = scanner.nextLine().split(",");
            int coordRow = Integer.parseInt(coordString[0]);
            int coordColumn = Integer.parseInt(coordString[1]);
            while(true){
                if((coordRow >= 0 && coordRow <= 4) && (coordColumn >= 0 && coordColumn <= 4)){
                    if(players[currPlayer].setWorker1(coordRow, coordColumn)){
                        break;
                    } else{
                        System.out.println("È già presente un lavoratore quì, inserisci delle altre coordinate!");
                        coordString = scanner.nextLine().split(",");
                        coordRow = Integer.parseInt(coordString[0]);
                        coordColumn = Integer.parseInt(coordString[1]);
                    }
                } else{
                    System.out.println("Inserisci delle coordinate valide!");
                    coordString = scanner.nextLine().split(",");
                    coordRow = Integer.parseInt(coordString[0]);
                    coordColumn = Integer.parseInt(coordString[1]);
                }
            }
            System.out.println("Inserisci le cordinate in cui vuoi posizionare il secondo lavoratore.");
            coordString = scanner.nextLine().split(",");
            coordRow = Integer.parseInt(coordString[0]);
            coordColumn = Integer.parseInt(coordString[1]);
            while(true){
                if((coordRow >= 0 && coordRow <= 4) && (coordColumn >= 0 && coordColumn <= 4)){
                    if(players[currPlayer].setWorker2(coordRow, coordColumn)){
                        break;
                    } else{
                        System.out.println("È già presente un lavoratore quì, inserisci delle altre coordinate!");
                        coordString = scanner.nextLine().split(",");
                        coordRow = Integer.parseInt(coordString[0]);
                        coordColumn = Integer.parseInt(coordString[1]);
                    }
                } else{
                    System.out.println("Inserisci delle coordinate valide!");
                    coordString = scanner.nextLine().split(",");
                    coordRow = Integer.parseInt(coordString[0]);
                    coordColumn = Integer.parseInt(coordString[1]);
                }
            }
        }
        //inizio dei turni
        currPlayer = 0;
        while (!victory){
            System.out.println("\n*VISUALE CLIENT " + currPlayer + "*\n");
            View.printMap();
            for (int i = 0; i < getNumberOfPlayers(); i++){
                if(inGame[i]){
                    View.printWorkersPositions(players[i]);
                }
            }
            TurnManager.startTurn(players[currPlayer]);
            if(!victory){
                //condizioni di vittoria per eliminazione di un giocatore
                switch(numberOfPlayers){
                    case 2:{
                        if(!inGame[currPlayer]){
                            currPlayer = nextPlayer(currPlayer);
                            setVictory();
                        } else{
                            currPlayer = nextPlayer(currPlayer);
                        }
                        break;
                    }
                    case 3:{
                        if(!inGame[currPlayer]){
                            currPlayer = nextPlayer(currPlayer);
                            if(!inGame[currPlayer]){
                                currPlayer = nextPlayer(currPlayer);
                                setVictory();
                            }
                        }
                        break;
                    }
                }
            }
        }
        endGame(players[currPlayer]);
    }

    private static int nextPlayer(int indexOfActualPlayer){
        if(indexOfActualPlayer == getNumberOfPlayers() - 1){
            return 0;
        } else{
            return indexOfActualPlayer + 1;
        }
    }

    public static void deletePlayer(Player player){
        int i = 0;
        while (!players[i].equals(player)){
            i++;
        }
        inGame[i] = false;
        System.out.println("Hai perso!");
        Map.deleteWorkerInCell(players[i].getWorkerSelected(1));
        Map.deleteWorkerInCell(players[i].getWorkerSelected(2));
        System.out.println("\n*CONNESSIONE CLIENT CHIUSA*\n");
    }

    public static void endGame(Player player){
        int i = 0;
        while (!players[i].equals(player)){
            i++;
        }
        String winner = players[i].getNickname();
        System.out.println("\n*VISUALE CLIENT " + i + "*\n");
        System.out.println("Congratulazioni, hai vinto la partita!");
        inGame[i] = false;
        System.out.println("\n*CONNESSIONE CLIENT CHIUSA*\n");
        for (i = 0; i < getNumberOfPlayers(); i++){
            if (inGame[i]){
                System.out.println("\n*VISUALE CLIENT " + 1 + "*\n");
                System.out.println("GAME OVER! " + winner + " ha vinto.");
                System.out.println("\n*CONNESSIONE CLIENT CHIUSA*\n");
            }
        }
    }

}
