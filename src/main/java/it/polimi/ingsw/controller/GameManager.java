package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Cards.God;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.View;

import java.util.Scanner;

public class GameManager {

    private static Player[] players;
    private static boolean allowHeight, allowHeightPrometheus;
    private static int numberOfPlayers;
    private Boolean victory = false;

    public void addPlayer(int numberConnection, String nickname){
        switch (numberConnection){
            case 0:{
                System.out.println("Sei il primo giocatore ad unirsi alla lobby.");
                System.out.println("Scegli quanti giocatori avrà la partita (min: 2, max: 3).");
                Scanner scanner = new Scanner(System.in);
                int numberOfPlayers= Integer.parseInt((scanner.nextLine()));
                setNumberOfPlayers(numberOfPlayers);
                new Game();
                new Map();
                players = new Player[numberOfPlayers];
                players[0] = new Player(nickname);
                System.out.println("Hai il colore " + players[0].getColor().toString());
                System.out.println("Scegli il numero di una delle " + GameManager.numberOfPlayers + " carte:");
                View.printCardsSelected();
                God g = Game.getCardToPlayer(Integer.parseInt((scanner.nextLine())));
                players[0].setGodChoice(g);
                View.printCardChosen(g);
                break;
            }
            case 1:{
                System.out.println("Sei il secondo giocatore ad unirsi alla lobby.");
                System.out.println("Attendi che " + players[0].getNickname() + " concluda la sua configurazione.");
                Scanner scanner = new Scanner(System.in);
                players[1] = new Player(nickname);
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
                System.out.println("Sei il terzo giocatore ad unirsi alla lobby.");
                System.out.println("Attendi che " + players[1].getNickname() + " concluda la sua configurazione.");
                Scanner scanner = new Scanner(System.in);
                players[2] = new Player (nickname);
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

    public void setNumberOfPlayers(int numberOfPlayers) {
        GameManager.numberOfPlayers = numberOfPlayers;
    }

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void startGame(){
        Scanner scanner = new Scanner(System.in);
        int currPlayer;
        //posizionamento lavoratori
        for (currPlayer = 0; currPlayer != players.length; currPlayer++){
            System.out.println("\n*VISUALE CLIENT " + (currPlayer) + "\n");
            View.printMapToPlayer();
            String[] coordString = new String[2];
            System.out.println("Inserisci le cordinate in cui vuoi posizionare il primo lavoratore.");
            coordString = scanner.nextLine().split(",");
            while(true){
                if(Integer.parseInt(coordString[0]) >= 0 && Integer.parseInt(coordString[0]) <= 4 &&
                        Integer.parseInt(coordString[1]) >= 0 && Integer.parseInt(coordString[1]) <= 4){
                    while(!players[currPlayer].setWorker1(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]))){
                        System.out.println("Impossibile posizionare il lavoratore, inserisci delle coordinate valide!");
                        coordString = scanner.nextLine().split(",");
                    }
                    break;
                } else{
                    System.out.println("Inserisci delle coordinate valide!");
                    coordString = scanner.nextLine().split(",");
                }
            }
            System.out.println("Inserisci le cordinate in cui vuoi posizionare il secondo lavoratore.");
            coordString = scanner.nextLine().split(",");
            while(true){
                if(Integer.parseInt(coordString[0]) >= 0 && Integer.parseInt(coordString[0]) <= 4 &&
                        Integer.parseInt(coordString[1]) >= 0 && Integer.parseInt(coordString[1]) <= 4){
                    while(!players[currPlayer].setWorker2(Integer.parseInt(coordString[0]), Integer.parseInt(coordString[1]))){
                        System.out.println("Impossibile posizionare il lavoratore, inserisci delle coordinate valide!");
                        coordString = scanner.nextLine().split(",");
                    }
                    break;
                } else{
                    System.out.println("Inserisci delle coordinate valide!");
                    coordString = scanner.nextLine().split(",");
                }
            }
        }
        //inizio dei turni
        currPlayer = 0;
        while (!victory){
            System.out.println("\n*VISUALE CLIENT " + (currPlayer++) + "\n");
            TurnManager.startTurn(players[currPlayer]);
            currPlayer = nextPlayer(currPlayer);
        }
    }

    public int nextPlayer(int indexOfActualPlayer){
        if(indexOfActualPlayer == players.length - 1){
            return 0;
        } else{
            return indexOfActualPlayer + 1;
        }
    }

    public void deletePlayer(Player player){
        int i = 0;
        while (!players[i].equals(player)){
            i++;
        }
        //si stampa a player[i]
        System.out.println("Hai perso!");
        String nickname = player.getNickname();
        //devo eliminare i suoi costruttori dalla mappa
        //elimino il giocatore da players[]
        //chiudo la connessione con il client
        if (players.length == 1){
            victory();// <--- players[0]
        } else {
            //si stampa ai due giocatori rimasti
            System.out.println(nickname + " è stato eliminato, e i suoi costruttori rimossi dal gioco.");
            setNumberOfPlayers(players.length);
        }
    }

    public static void setAllowHeight(boolean allowHeight) {
        GameManager.allowHeight = allowHeight;
    }

    public static boolean cannotGoUp() {
        return !allowHeight;
    }

    public static void setAllowHeightPrometheus(boolean allowHeightPrometheus) {
        GameManager.allowHeightPrometheus = allowHeightPrometheus;
    }

    public static boolean getAllowHeightPrometheus() {
        return allowHeightPrometheus;
    }

    public static void victory(){/*<--- Player player
        //si stampa al giocatore che ha vinto
        System.out.println("Congratulazioni, hai vinto la partita!");
        if (players.length == 1){
            //chiudo la connessione col client
            //chiudo il server
        } else {
            int i = 0;
            while (!players[i].equals(player)){
                i++;
            }
            for (int j = 0; j < players.length; j++){
                if(j != i){
                    //si manda ai giocatori players[j] (quelli rimanenti)
                    System.out.println(players[i].getNickname() + " ha vinto.");
                    //chiudo il client players[j]
                }
            }
            //chiudo il server
        }
        */
    }

}
