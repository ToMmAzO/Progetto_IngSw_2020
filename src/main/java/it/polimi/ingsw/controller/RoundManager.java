package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Map;
import it.polimi.ingsw.model.Player;

public class RoundManager {

    private Player[] players;
    private Map map;
    private boolean allowHeight;
    private int numberOfPlayers;

    public void addPlayer(){
        //codice
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

    public void setAllowHeight(boolean allowHeight) {
        this.allowHeight = allowHeight;
    }

    public boolean getAllowHeight() {
        return allowHeight;
    }

    public void victory(){
        //codice
    }

}
