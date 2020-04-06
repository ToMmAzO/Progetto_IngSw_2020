package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.util.Scanner;

public class GameManager {

    private Player[] players;
    private Map map;
    private static boolean allowHeight;
    private static int numberOfPlayers;


    public void addPlayer(int x){// tipo int x = Server.getNumberOfConnection;
        switch (x){
            case 0:{
                System.out.println("Sei il primo giocatore ad unirsi alla lobby.");
                System.out.println("Scegli quanti giocatori avrà la partita (min: 2, max: 3).");
                Scanner scanner = new Scanner(System.in);
                int n= Integer.parseInt((scanner.nextLine()));
                setNumberOfPlayers(n);
                new Game();
                new Map();
                players = new Player[n];
                this.players[0] = new Player("nickname passato dal client");

                //chiede colore

                //chiama un metodo della view che stampa a video le 3 carte con Game.getCardsSelected()
                System.out.println("Scegli il numero di una delle 3 carte.");
                this.players[0].setGodChoice(Game.getCardToPlayer(Integer.parseInt((scanner.nextLine()))));

            }
            case 1:{
                System.out.println("Sei il secondo giocatore ad unirsi alla lobby.");
                System.out.println("Attendi che il giocatore precedente concluda la sua configurazione.");
                this.players[1] = new Player("nickname passato dal client");
                Scanner scanner = new Scanner(System.in);

                //chiede colore

                //chiama un metodo della view che stampa a video le 3 carte con Game.getCardsSelected()
                System.out.println("Scegli il numero di una delle 3 carte ancora disponibili.");
                int y = Integer.parseInt((scanner.nextLine()));
                while (!Game.getAvailability()[y - 1]){
                    System.out.println("Carta non disponibile, seleziona il numero di una disponibile!");
                    y = Integer.parseInt((scanner.nextLine()));
                }
                this.players[1].setGodChoice(Game.getCardToPlayer(y));

            }
            case 2:{
                System.out.println("Sei il terzo giocatore ad unirsi alla lobby.");
                System.out.println("Attendi che il giocatore precedente concluda la sua configurazione.");
                this.players[2] = new Player ("nickname passato dal client");
                Scanner scanner = new Scanner(System.in);

                //chiede colore

                //chiama un metodo della view che stampa a video le 3 carte con Game.getCardsSelected()
                System.out.println("Scegli il numero di una delle 3 carte ancora disponibili.");
                int y = Integer.parseInt((scanner.nextLine()));
                while (!Game.getAvailability()[y - 1]){
                    System.out.println("Carta non disponibile, seleziona il numero di una disponibile!");
                    y = Integer.parseInt((scanner.nextLine()));
                }
                this.players[2].setGodChoice(Game.getCardToPlayer(y));

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
