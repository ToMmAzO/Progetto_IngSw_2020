package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.ActionManager;
import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.model.Cards.Deck;
import it.polimi.ingsw.model.Cards.God;
import it.polimi.ingsw.model.Player.Player;

import java.util.Scanner;

public class ViewGame {

    private static final Scanner scanner= new Scanner(System.in);

    public static void joinGameFirst(String nickname){
        int playerIndex = 0;
        System.out.println("Sei il primo giocatore ad unirsi alla lobby.");
        System.out.print("Scegli quanti giocatori avrà la partita. ");
        int numberChosen = 0;
        while (numberChosen != 2 && numberChosen != 3){
            try{
                System.out.print("Scrivi 2 oppure 3: ");
                numberChosen= Integer.parseInt((scanner.nextLine()));
            } catch(IllegalArgumentException e){
                System.out.print("Formato Input scorretto! ");
            }
        }
        GameManager.addFirstPlayer(nickname, numberChosen);
        godChoice(playerIndex);
        setTurn(GameManager.getPlayersInGame()[0], 0);
    }

    public static void joinGame(String nickname){
        int playerIndex = GameManager.addPlayer(nickname);
        godChoice(playerIndex);
        ViewGame.setTurn(GameManager.getPlayersInGame()[playerIndex], playerIndex);
    }

    private static void godChoice(int playerIndex){
        ViewModel.printCardsSelected();
        int cardNumber = 0;
        while (!Deck.isAvailable(cardNumber)){
            try{
                System.out.print("Scegli il numero di una delle " + GameManager.getNumberOfPlayers() + " carte ancora disponibili: ");
                cardNumber= Integer.parseInt((scanner.nextLine()));
            } catch(IllegalArgumentException e){
                System.out.print("Formato Input scorretto! ");
            }
        }
        God g = GameManager.choiceOfCard(playerIndex, cardNumber);
        ViewModel.printCardChosen(g);
    }

    private static void setTurn(Player player, int indexOfPlayer){
        ViewModel.printMap();
        if (indexOfPlayer > 0){
            ViewModel.printWorkersPositions(GameManager.getPlayersInGame()[0]);
            if (indexOfPlayer > 1) {
                ViewModel.printWorkersPositions(GameManager.getPlayersInGame()[1]);
            }
        }
        setWorker(player, 1);
        setWorker(player, 2);
    }

    private static void setWorker(Player player, int numberOfWorker){
        System.out.print("Posizionamento lavoratore " + (numberOfWorker) + ". ");
        String[] coordString;
        int coordRow;
        int coordColumn;
        while (true){
            try{
                System.out.print("Inserisci delle coordinate x, y: ");
                coordString = scanner.nextLine().replace(" ", "").split(",");
                coordRow = Integer.parseInt(coordString[0]);
                coordColumn = Integer.parseInt(coordString[1]);
                if (ActionManager.validCoords(coordRow, coordColumn)){
                    switch (numberOfWorker) {
                        case 1 -> {
                            if (player.setWorker1(coordRow, coordColumn)) {
                                return;
                            }
                        }
                        case 2 -> {
                            if (player.setWorker2(coordRow, coordColumn)) {
                                return;
                            }
                        }
                    }
                    System.out.print("È già presente un lavoratore quì! ");
                } else{
                    System.out.print("Puoi inserire solo interi da 0 a 4! ");
                }
            } catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                System.out.print("Formato Input scorretto! ");
            }
        }
    }

}
