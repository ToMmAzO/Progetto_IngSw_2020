package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameManager;

public class Prova {

    public static void main (String[] arg){
        GameManager.addPlayer(0, "Player 1");
        GameManager.addPlayer(1, "Player 2");
        GameManager.addPlayer(2, "Player 3");
        GameManager.startGame();
    }

}
