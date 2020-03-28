package it.polimi.ingsw.model;

public class Game {
    private Player[] players;
    private Map map;
    private Deck divinityCardsDeck;
    private boolean terminator;
    private boolean allow_ZChanges;

    public void addPlayer(){
        //codice
    }

    public void setOrderPlayer(){
        //codice
    }

    public void startGame(){
        //codice
    }

    public Player nextPlayer(){
        //codice
    }

    public boolean getAllow_ZChanges() {
        return allow_ZChanges;
    }

    public void setAllow_ZChanges(boolean allow_ZChanges) {
        this.allow_ZChanges = allow_ZChanges;
    }

    public void victory(){
        //codice
    }
}
