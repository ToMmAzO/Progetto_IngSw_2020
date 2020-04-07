package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Cards.God;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.View;

import java.util.Scanner;

public class GameManager {

    private Player[] players;
    private static boolean allowHeight;
    private static int numberOfPlayers;

    public void addPlayer(int numberConnection){// tipo int x = Server.getNumberOfConnection;
        switch (numberConnection){
            case 0:{
                System.out.println("Sei il primo giocatore ad unirsi alla lobby.");
                System.out.println("Scegli quanti giocatori avrà la partita (min: 2, max: 3).");
                Scanner scanner = new Scanner(System.in);
                int n= Integer.parseInt((scanner.nextLine()));
                setNumberOfPlayers(n);
                new Game();
                new Map();
                players = new Player[n];
                this.players[0] = new Player("Player 1");
                /*
                Scelta del colore con relativi controlli

                System.out.println("Scegli il tuo colore.");

                this.players[0].chooseColor();
                */
                View.printCardsSelected();
                System.out.println("Scegli il numero di una delle 3 carte.");
                God g = Game.getCardToPlayer(Integer.parseInt((scanner.nextLine())));
                this.players[0].setGodChoice(g);
                View.printCardChosen(g);
            }
            case 1:{
                System.out.println("Sei il secondo giocatore ad unirsi alla lobby.");
                System.out.println("Attendi che il giocatore" + this.players[0].getNickname() + " concluda la sua configurazione.");
                Scanner scanner = new Scanner(System.in);
                this.players[1] = new Player("Player 2");
                /*
                Scelta del colore con relativi controlli

                System.out.println("Scegli il tuo colore.");

                this.players[1].chooseColor();
                */
                View.printCardsSelected();
                System.out.println("Scegli il numero di una delle 3 carte ancora disponibili.");
                int y = Integer.parseInt((scanner.nextLine()));
                while (!Game.getAvailability()[y - 1]){
                    System.out.println("Carta non disponibile, seleziona una carta disponibile!");
                    y = Integer.parseInt((scanner.nextLine()));
                }
                God g = Game.getCardToPlayer(y);
                this.players[1].setGodChoice(g);
                View.printCardChosen(g);
            }
            case 2:{
                System.out.println("Sei il terzo giocatore ad unirsi alla lobby.");
                System.out.println("Attendi che il giocatore" + this.players[1].getNickname() + " concluda la sua configurazione.");
                Scanner scanner = new Scanner(System.in);
                this.players[2] = new Player ("Player 3");
                /*
                Scelta del colore con relativi controlli

                System.out.println("Scegli il tuo colore.");

                this.players[2].chooseColor();
                */
                View.printCardsSelected();
                System.out.println("Scegli il numero di una delle 3 carte ancora disponibili.");
                int y = Integer.parseInt((scanner.nextLine()));
                while (!Game.getAvailability()[y - 1]){
                    System.out.println("Carta non disponibile, seleziona il numero di una disponibile!");
                    y = Integer.parseInt((scanner.nextLine()));
                }
                God g = Game.getCardToPlayer(y);
                this.players[2].setGodChoice(g);
                View.printCardChosen(g);
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

        View.printMapToPlayer();
        //tutti i player posizionano i lavoratori

        int currPlayer = 0;
        while (true){
            TurnManager.startTurn(players[currPlayer]);
            //il server decide di mandare questo al client corrispondente al giocatore corrente
            if (currPlayer == 1000){//tipo se turnmanager torna false qualcuno ha chiamato vitory e quindi si esce dal ciclo){
                break;
            }
            currPlayer = nextPlayer(currPlayer);
        }
    }

    public int nextPlayer(int indexOfActualPlayer){
        if(indexOfActualPlayer == players.length){
            return 0;
        } else{
            return indexOfActualPlayer + 1;
        }
    }

    public void deletePlayer(){
        //codice
    }

    public static void setAllowHeight(boolean setAllowHeight) {
        allowHeight = setAllowHeight;
    }

    public static boolean getAllowHeight() {
        return allowHeight;
    }

    public static void victory(){
        //codice
    }

}
