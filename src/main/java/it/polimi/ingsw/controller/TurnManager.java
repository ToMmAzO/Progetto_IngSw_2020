package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.workers.Worker;
import it.polimi.ingsw.network.message.*;

public class TurnManager {

    private static Worker workerSelected;
    private static boolean allowHeight = true;
    private static boolean allowHeightPrometheus = true;
    private static int startX, startY;
    private static int buildX, buildY;

    public static boolean verifyRegularity(Player player, int workerChosen){
        workerSelected = player.getWorkerSelected(workerChosen);
        if(workerSelected.canMove()){
            return true;
        } else{
            GameManager.getCurrConnection().asyncSend(workerSelected.getIdWorker() + " non può muoversi! Seleziono l'altro Worker.");
            if(workerChosen == 1){
                workerChosen ++;
            } else{
                workerChosen --;
            }
            workerSelected = player.getWorkerSelected(workerChosen);
            GameManager.getCurrConnection().asyncSend("Il worker selezionato è: "+ workerSelected.getIdWorker() + ".");
            if(workerSelected.canMove()){
                return true;
            } else{
                GameManager.getCurrConnection().asyncSend("Nemmeno " + workerSelected.getIdWorker() + " può muoversi!");
                workerSelected = null;
                return false;
            }
        }
    }

    public static void workerChoice(int selectionWorker){
        if(selectionWorker == 1 || selectionWorker == 2){
            if(verifyRegularity(GameManager.getCurrPlayer(), selectionWorker)){
                if(GameManager.getCurrPlayer().getGodChoice() == God.PROMETHEUS && workerSelected.canBuild()){
                    GameManager.getCurrConnection().asyncSend(Map.getInstance());
                    GameManager.getCurrConnection().asyncSend(new Message_QuestionPrometheus());
                } else{
                    GameManager.getCurrConnection().asyncSend(Map.getInstance());
                    GameManager.getCurrConnection().asyncSend(new Message_Movement());
                }
            } else{
                GameManager.deletePlayer(GameManager.getCurrPlayer());
            }
        } else{
            GameManager.getCurrConnection().asyncSend("Numero scorretto! Scrivi 1 oppure 2: ");
        }
    }

    public static void prebuildPrometheus(int coordX, int coordY){
        if(ActionManager.verifyCoordinateConstruction(workerSelected, false, coordX, coordY)){
            workerSelected.buildBlock(coordX, coordY);
            GameManager.getCurrConnection().asyncSend(Map.getInstance());
            setAllowHeightPrometheus(false);
        }
        if(workerSelected.canMove()) {
            GameManager.getCurrConnection().asyncSend(new Message_Movement());
        }else{
            GameManager.getCurrConnection().asyncSend(workerSelected.getIdWorker() + " non può più muoversi!");
            setAllowHeightPrometheus(true);
            //STOP TURN
        }
    }

    public static void movement(int coordX, int coordY){
        if (ActionManager.verifyCoordinateMovement(workerSelected, GameManager.getCurrPlayer().getGodChoice(), coordX, coordY)) {
            if(GameManager.getCurrPlayer().getGodChoice() == God.ARTEMIS){
                startX = workerSelected.getCoordX();
                startY = workerSelected.getCoordY();
            }

            if(GameManager.getCurrPlayer().getGodChoice() == God.PAN){          //brutto
                if(winPan(coordX, coordY)) {
                    workerSelected.changePosition(coordX, coordY);
                    GameManager.getCurrConnection().asyncSend(Map.getInstance());
                    GameManager.endGame(GameManager.getCurrPlayer());
                }
            }else{
                if (winDefault(coordX, coordY)) {
                    workerSelected.changePosition(coordX, coordY);
                    GameManager.getCurrConnection().asyncSend(Map.getInstance());
                    GameManager.endGame(GameManager.getCurrPlayer());
                } else {
                    workerSelected.changePosition(coordX, coordY);
                    switch (GameManager.getCurrPlayer().getGodChoice()) {
                        case ARTEMIS -> {
                            if(workerSelected.canMove(startX, startY)) {
                                GameManager.getCurrConnection().asyncSend(new Message_QuestionArtemis());
                            } else {
                                GameManager.getCurrConnection().asyncSend(workerSelected.getIdWorker() + " non può più muoversi!");
                                GameManager.getCurrConnection().asyncSend(new Message_Construction());
                            }
                        }
                        case ATLAS -> {
                            if(workerSelected.canBuild()) {
                                GameManager.getCurrConnection().asyncSend(new Message_QuestionAtlas());
                            }else{
                                GameManager.getCurrConnection().asyncSend(workerSelected.getIdWorker() + " non può costruire!");
                                //STOP TURN
                            }
                        }
                        case HEPHAESTUS -> {
                            if(workerSelected.canBuild()) {
                                if(workerSelected.canBuild(true)) {
                                    GameManager.getCurrConnection().asyncSend(new Message_QuestionHephaestus());
                                } else {
                                    GameManager.getCurrConnection().asyncSend(workerSelected.getIdWorker() + " NON può costruire 2 blocchi!");
                                    GameManager.getCurrConnection().asyncSend(new Message_Construction());
                                }
                            }else{
                                GameManager.getCurrConnection().asyncSend(workerSelected.getIdWorker() + " non può costruire!");
                                //STOP TURN
                            }
                        }
                        default -> {
                            if(workerSelected.canBuild()) {
                                GameManager.getCurrConnection().asyncSend(new Message_Construction());
                            }else{
                                GameManager.getCurrConnection().asyncSend(workerSelected.getIdWorker() + " non può costruire!");
                                //STOP TURN
                            }
                        }
                    }
                }
            }
        }
    }

    public static void secondMove(int coordX, int coordY){
        if (startX == coordX && startY == coordY) {
            GameManager.getCurrConnection().asyncSend("ATTENTO, non puoi tornare indietro! ");
        } else {
            if (ActionManager.verifyCoordinateMovement(workerSelected, GameManager.getCurrPlayer().getGodChoice(), coordX, coordY)) {
                if (winDefault(coordX, coordY)) {
                    workerSelected.changePosition(coordX, coordY);
                    GameManager.getCurrConnection().asyncSend(Map.getInstance());
                    GameManager.endGame(GameManager.getCurrPlayer());
                } else {
                    workerSelected.changePosition(coordX, coordY);
                    GameManager.getCurrConnection().asyncSend(Map.getInstance());
                    if(workerSelected.canBuild()) {
                        GameManager.getCurrConnection().asyncSend(new Message_Construction());
                    }else{
                        GameManager.getCurrConnection().asyncSend(workerSelected.getIdWorker() + " non può costruire!");
                        //STOP TURN
                    }
                }
            }
        }
    }

    public static boolean winDefault(int x, int y){
        return workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 3;
    }

    public static boolean winPan(int x, int y){
        return (workerSelected.getCoordZ() == 3 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 1)
                || (workerSelected.getCoordZ() == 3 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 0)
                || (workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 0)
                || (workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 3);
    }

    public static void construction(int coordX, int coordY){
        if(ActionManager.verifyCoordinateConstruction(workerSelected, false, coordX, coordY)){
            workerSelected.buildBlock(coordX, coordY);
            GameManager.getCurrConnection().asyncSend(Map.getInstance());
            if (GameManager.getCurrPlayer().getGodChoice() == God.DEMETER) {
                buildX = coordX;
                buildY = coordY;
                if(workerSelected.canBuild(buildX, buildY)) {
                    GameManager.getCurrConnection().asyncSend(new Message_QuestionDemeter());
                }else{
                    GameManager.getCurrConnection().asyncSend(workerSelected.getIdWorker() + " non può più costruire!");
                    //STOP TURN
                }
            } else {
                //STOP TURN
            }
        }
    }

    public static void secondConstruction(int coordX, int coordY){
        if (buildX == coordX && buildY == coordY) {
            System.out.print("ATTENTO, non puoi costruire nello stesso punto di prima!");
        } else {
            if(ActionManager.verifyCoordinateConstruction(workerSelected, false, coordX, coordY)){
                workerSelected.buildBlock(coordX, coordY);
                GameManager.getCurrConnection().asyncSend(Map.getInstance());
                //STOP TURN
            }
        }
    }

    public static void constructionCupola(int coordX, int coordY){
        if(ActionManager.verifyCoordinateConstruction(workerSelected, false, coordX, coordY)){
            workerSelected.buildBlock(true, coordX, coordY);
            GameManager.getCurrConnection().asyncSend(Map.getInstance());
            //STOP TURN
        }
    }

    public static void doubleConstruction(int coordX, int coordY){
        if(ActionManager.verifyCoordinateConstruction(workerSelected, true, coordX, coordY)){
            workerSelected.buildBlock(true, coordX, coordY);
            GameManager.getCurrConnection().asyncSend(Map.getInstance());
            //STOP TURN
        }
    }

    public static void setAllowHeight(boolean allowHeight) {
        TurnManager.allowHeight = allowHeight;
    }

    public static boolean cannotGoUp() {
        return !allowHeight;
    }

    public static void setAllowHeightPrometheus(boolean allowHeightPrometheus) {
        TurnManager.allowHeightPrometheus = allowHeightPrometheus;
    }

    public static boolean cannotGoUpPrometheus() {
        return !allowHeightPrometheus;
    }

}

