package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.view.ViewGame;

public class Prova {

    public static void main (String[] arg){
        ViewGame.joinGameFirst("Player 1");
        ViewGame.joinGame("Player 2");
        ViewGame.joinGame("Player 3");

        GameManager.startGame();
    }

}