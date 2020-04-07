package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;

public class TurnManager {

    private static Worker workerSelected;
    private static int row, column;
    private static boolean yes;
    private static int x, y;             //richiede le due coordinate e gliele ripassa controllando che siano diverse

    public static void setRow(int row) {
        TurnManager.row = row;
    }

    public static void setColumn(int column) {
        TurnManager.column = column;
    }

    public static void setYes(boolean yes) {
        TurnManager.yes = yes;
    }

    public static void startTurn(Player player){
        workerSelected = player.getWorkerSelected();
        selectAction(player);
        workerSelected = null;
    }

    public static void selectAction(Player player){
        switch (player.getGodChoice()) {
            case ARTEMIS:
                x = workerSelected.getCoordX();
                y = workerSelected.getCoordY();

                if(ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)) {        //tutti questi if possono essere try catch
                    workerSelected.canMove();
                    workerSelected.changePosition(row, column);
                }

                if(yes){
                    if(x != row && y != column) {
                        if (ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)) {    //sposta sopra + bello
                            workerSelected.canMove();
                            workerSelected.changePosition(row, column);
                        }
                    }else{
                        //ERRORE
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
                    workerSelected.buildBlock(yes, row, column);
                }
                break;

            case DEMETER:
                if(ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)) {
                    workerSelected.canMove();
                    workerSelected.changePosition(row, column);
                }

                if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)){
                    x = row;
                    y = column;
                    workerSelected.canBuild();
                    workerSelected.buildBlock(row, column);
                }

                if(yes){
                    if(x != row && y != column) {
                        if (ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)) {    //sposta sopra + bello
                            workerSelected.canBuild();
                            workerSelected.buildBlock(row, column);
                        }
                    }else{
                        //ERRORE
                    }
                }
                break;

            case HEPHAESTUS:
                if(ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)) {
                    workerSelected.canMove();
                    workerSelected.changePosition(row, column);
                }

                if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)){
                    workerSelected.canBuild(yes);
                    workerSelected.buildBlock(yes, row, column);
                }
                break;
            case PROMETHEUS:
                if(yes){
                    if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)){    //sposta sopra + bello
                        workerSelected.canBuild();
                        workerSelected.buildBlock(row, column);
                        GameManager.setAllowHeightPrometheus(false);
                    }
                }

                if(ActionManager.movementManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)) {
                    workerSelected.canMove();
                    workerSelected.changePosition(row, column);
                }

                if(ActionManager.buildManager(workerSelected.getCoordX(), workerSelected.getCoordY(), row, column)){
                    workerSelected.canBuild();
                    workerSelected.buildBlock(row, column);
                    GameManager.setAllowHeightPrometheus(true);
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
