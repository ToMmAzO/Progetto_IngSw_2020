package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

import static it.polimi.ingsw.model.Cards.Divinity.ARTEMIS;

public class TurnManager {

    private static Worker workerSelected;
    private static int row, column;
    private static boolean moveAgain, buildCupola, buildAgain, buildFirst;      //può essere anche un solo move e un solo bulid

    public static void setRow(int row) {
        TurnManager.row = row;
    }

    public static void setColumn(int column) {
        TurnManager.column = column;
    }

    public static void setMoveAgain(boolean moveAgain) {
        TurnManager.moveAgain = moveAgain;
    }

    public static void setBuildCupola(boolean buildCupola) {
        TurnManager.buildCupola = buildCupola;
    }

    public static void setBuildAgain(boolean buildAgain) {
        TurnManager.buildAgain = buildAgain;
    }

    public static void setBuildFirst(boolean buildFirst) {
        TurnManager.buildFirst = buildFirst;
    }

    public static void startTurn(Player player){
        workerSelected = player.getWorkerSelected();
        selectAction(player);
        workerSelected = null;
    }

    public static void selectAction(Player player){
        switch (player.getGodChoice()) {
            case ARTEMIS:
                if(ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)) {
                    workerSelected.canMove();
                    workerSelected.changePosition(row, column);
                }

                if(moveAgain){          //richiede le due coordinate e gliele ripassa controllando che siano diverse
                    if(ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)) {    //sposta sopra + bello
                        workerSelected.canMove();
                        workerSelected.changePosition(row, column);
                    }
                }

                if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)){
                    workerSelected.canBuild();
                    workerSelected.buildBlock(row, column);
                }
                break;

            case ATLAS:
                if(ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)) {
                    workerSelected.canMove();
                    workerSelected.changePosition(row, column);
                }

                if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)){
                    workerSelected.canBuild();
                    workerSelected.buildBlock(buildCupola, row, column);
                }
                break;

            case DEMETER:
                if(ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)) {
                    workerSelected.canMove();
                    workerSelected.changePosition(row, column);
                }

                if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)){
                    workerSelected.canBuild();
                    workerSelected.buildBlock(row, column);
                }

                if(buildAgain){          //richiede le due coordinate e gliele ripassa controllando che siano diverse
                    if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)){    //sposta sopra + bello
                        workerSelected.canBuild();
                        workerSelected.buildBlock(row, column);
                    }
                }
                break;

            case HEPHAESTUS:
                if(ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)) {
                    workerSelected.canMove();
                    workerSelected.changePosition(row, column);
                }

                if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)){
                    workerSelected.canBuild(buildAgain);
                    workerSelected.buildBlock(buildAgain, row, column);
                }
                break;
            case PROMETHEUS:
                if(buildFirst){          //richiede le due coordinate
                    if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)){    //sposta sopra + bello
                        workerSelected.canBuild();
                        workerSelected.buildBlock(row, column);
                    }
                }

                if(ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)) {
                    workerSelected.canMove();
                    workerSelected.changePosition(row, column);
                }

                if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)){
                    workerSelected.canBuild();
                    workerSelected.buildBlock(row, column);
                }
                break;

            default:    //APOLLO, ATHENA, ATLAS, MINOTAUR, PAN
                if(ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)) {
                    workerSelected.canMove();
                    workerSelected.changePosition(row, column);
                }

                if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)){
                    workerSelected.canBuild();
                    workerSelected.buildBlock(row, column);
                }
                break;
        }
    }
}
