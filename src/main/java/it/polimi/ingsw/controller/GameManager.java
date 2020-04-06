package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Player;

public class GameManager {

    private Player[] players;
    private Map map;
    private static boolean allowHeight;
    private static int numberOfPlayers;

    public void addPlayer(){
        //codice
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        GameManager.numberOfPlayers = numberOfPlayers;
    }

    public static int getNumberOfPlayers() {
        return numberOfPlayers;
    }


    public void startGame(){
        //codice
    }

    public Player nextPlayer(){
        //codice
        return players[0];//esempio
    }

    public void deletePlayer(){
        //codice
    }

    public static void setAllowHeight(boolean setAllowHeight) {
        allowHeight = setAllowHeight;
    }

    public boolean getAllowHeight() {
        return allowHeight;
    }

    public static void victory(){
        //codice
    }

}
