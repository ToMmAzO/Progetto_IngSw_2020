package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.workers.Worker;
import it.polimi.ingsw.network.message.*;

public class TurnManager {

    private static TurnManager turnManager = null;
    private Worker workerSelected;
    private boolean allowHeight = true;
    private boolean allowHeightPrometheus = true;
    private int startX, startY, buildX, buildY;

    public TurnManager(){
        turnManager = this;
    }

    public static TurnManager getInstance(){
        return turnManager;
    }

    public boolean verifyRegularity(Player player, int workerChosen){
        workerSelected = player.getWorkerSelected(workerChosen);
        if(workerSelected.canMove()){
            return true;
        } else{
            GameManager.getInstance().getCurrConnection().asyncSend(workerSelected.getIdWorker() + " non può muoversi! Seleziono l'altro Worker.");
            if(workerChosen == 1){
                workerChosen ++;
            } else{
                workerChosen --;
            }
            workerSelected = player.getWorkerSelected(workerChosen);
            GameManager.getInstance().getCurrConnection().asyncSend("Il worker selezionato è: "+ workerSelected.getIdWorker() + ".");
            if(workerSelected.canMove()){
                return true;
            } else{
                GameManager.getInstance().getCurrConnection().asyncSend("Nemmeno " + workerSelected.getIdWorker() + " può muoversi!");
                workerSelected = null;
                return false;
            }
        }
    }

    public void workerChoice(int selectionWorker){
        if(selectionWorker == 1 || selectionWorker == 2){
            if(verifyRegularity(GameManager.getInstance().getCurrPlayer(), selectionWorker)){
                if(GameManager.getInstance().getCurrPlayer().getGodChoice() == God.PROMETHEUS && workerSelected.canBuild()){
                    GameManager.getInstance().getCurrConnection().asyncSend(Map.getInstance());
                    GameManager.getInstance().getCurrConnection().asyncSend(new Message_QuestionPrometheus());
                } else{
                    GameManager.getInstance().getCurrConnection().asyncSend(Map.getInstance());
                    GameManager.getInstance().getCurrConnection().asyncSend(new Message_Movement());
                }
            } else{
                GameManager.getInstance().deletePlayer(GameManager.getInstance().getCurrPlayer());
            }
        } else{
            GameManager.getInstance().getCurrConnection().asyncSend("Numero scorretto! Scrivi 1 oppure 2: ");
        }
    }

    public void prebuildPrometheus(int coordX, int coordY){
        if(ActionManager.getInstance().verifyCoordinateConstruction(workerSelected, false, coordX, coordY)){
            workerSelected.buildBlock(coordX, coordY);
            GameManager.getInstance().getCurrConnection().asyncSend(Map.getInstance());
            setAllowHeightPrometheus(false);
        }
        if(workerSelected.canMove()) {
            GameManager.getInstance().getCurrConnection().asyncSend(new Message_Movement());
        }else{
            GameManager.getInstance().getCurrConnection().asyncSend(workerSelected.getIdWorker() + " non può più muoversi!");
            setAllowHeightPrometheus(true);
            //STOP TURN
        }
    }

    public void movement(int coordX, int coordY){
        if (ActionManager.getInstance().verifyCoordinateMovement(workerSelected, GameManager.getInstance().getCurrPlayer().getGodChoice(), coordX, coordY)) {
            if(GameManager.getInstance().getCurrPlayer().getGodChoice() == God.ARTEMIS){
                startX = workerSelected.getCoordX();
                startY = workerSelected.getCoordY();
            }

            if(GameManager.getInstance().getCurrPlayer().getGodChoice() == God.PAN){          //brutto
                if(winPan(coordX, coordY)) {
                    workerSelected.changePosition(coordX, coordY);
                    GameManager.getInstance().getCurrConnection().asyncSend(Map.getInstance());
                    GameManager.getInstance().endGame(GameManager.getInstance().getCurrPlayer());
                }
            }else{
                if (winDefault(coordX, coordY)) {
                    workerSelected.changePosition(coordX, coordY);
                    GameManager.getInstance().getCurrConnection().asyncSend(Map.getInstance());
                    GameManager.getInstance().endGame(GameManager.getInstance().getCurrPlayer());
                } else {
                    workerSelected.changePosition(coordX, coordY);
                    switch (GameManager.getInstance().getCurrPlayer().getGodChoice()) {
                        case ARTEMIS -> {
                            if(workerSelected.canMove(startX, startY)) {
                                GameManager.getInstance().getCurrConnection().asyncSend(new Message_QuestionArtemis());
                            } else {
                                GameManager.getInstance().getCurrConnection().asyncSend(workerSelected.getIdWorker() + " non può più muoversi!");
                                GameManager.getInstance().getCurrConnection().asyncSend(new Message_Construction());
                            }
                        }
                        case ATLAS -> {
                            if(workerSelected.canBuild()) {
                                GameManager.getInstance().getCurrConnection().asyncSend(new Message_QuestionAtlas());
                            }else{
                                GameManager.getInstance().getCurrConnection().asyncSend(workerSelected.getIdWorker() + " non può costruire!");
                                //STOP TURN
                            }
                        }
                        case HEPHAESTUS -> {
                            if(workerSelected.canBuild()) {
                                if(workerSelected.canBuild(true)) {
                                    GameManager.getInstance().getCurrConnection().asyncSend(new Message_QuestionHephaestus());
                                } else {
                                    GameManager.getInstance().getCurrConnection().asyncSend(workerSelected.getIdWorker() + " NON può costruire 2 blocchi!");
                                    GameManager.getInstance().getCurrConnection().asyncSend(new Message_Construction());
                                }
                            }else{
                                GameManager.getInstance().getCurrConnection().asyncSend(workerSelected.getIdWorker() + " non può costruire!");
                                //STOP TURN
                            }
                        }
                        default -> {
                            if(workerSelected.canBuild()) {
                                GameManager.getInstance().getCurrConnection().asyncSend(new Message_Construction());
                            }else{
                                GameManager.getInstance().getCurrConnection().asyncSend(workerSelected.getIdWorker() + " non può costruire!");
                                //STOP TURN
                            }
                        }
                    }
                }
            }
        }
    }

    public void secondMove(int coordX, int coordY){
        if (startX == coordX && startY == coordY) {
            GameManager.getInstance().getCurrConnection().asyncSend("ATTENTO, non puoi tornare indietro! ");
        } else {
            if (ActionManager.getInstance().verifyCoordinateMovement(workerSelected, GameManager.getInstance().getCurrPlayer().getGodChoice(), coordX, coordY)) {
                if (winDefault(coordX, coordY)) {
                    workerSelected.changePosition(coordX, coordY);
                    GameManager.getInstance().getCurrConnection().asyncSend(Map.getInstance());
                    GameManager.getInstance().endGame(GameManager.getInstance().getCurrPlayer());
                } else {
                    workerSelected.changePosition(coordX, coordY);
                    GameManager.getInstance().getCurrConnection().asyncSend(Map.getInstance());
                    if(workerSelected.canBuild()) {
                        GameManager.getInstance().getCurrConnection().asyncSend(new Message_Construction());
                    }else{
                        GameManager.getInstance().getCurrConnection().asyncSend(workerSelected.getIdWorker() + " non può costruire!");
                        //STOP TURN
                    }
                }
            }
        }
    }

    public boolean winDefault(int x, int y){
        return workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 3;
    }

    public boolean winPan(int x, int y){
        return (workerSelected.getCoordZ() == 3 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 1)
                || (workerSelected.getCoordZ() == 3 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 0)
                || (workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 0)
                || (workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 3);
    }

    public void construction(int coordX, int coordY){
        if(ActionManager.getInstance().verifyCoordinateConstruction(workerSelected, false, coordX, coordY)){
            workerSelected.buildBlock(coordX, coordY);
            GameManager.getInstance().getCurrConnection().asyncSend(Map.getInstance());
            if (GameManager.getInstance().getCurrPlayer().getGodChoice() == God.DEMETER) {
                buildX = coordX;
                buildY = coordY;
                if(workerSelected.canBuild(buildX, buildY)) {
                    GameManager.getInstance().getCurrConnection().asyncSend(new Message_QuestionDemeter());
                }else{
                    GameManager.getInstance().getCurrConnection().asyncSend(workerSelected.getIdWorker() + " non può più costruire!");
                    //STOP TURN
                }
            }
            /*
            else {
                //STOP TURN
            }
            */
        }
    }

    public void secondConstruction(int coordX, int coordY){
        if (buildX == coordX && buildY == coordY) {
            System.out.print("ATTENTO, non puoi costruire nello stesso punto di prima!");
        } else {
            if(ActionManager.getInstance().verifyCoordinateConstruction(workerSelected, false, coordX, coordY)){
                workerSelected.buildBlock(coordX, coordY);
                GameManager.getInstance().getCurrConnection().asyncSend(Map.getInstance());
                //STOP TURN
            }
        }
    }

    public void constructionCupola(int coordX, int coordY){
        if(ActionManager.getInstance().verifyCoordinateConstruction(workerSelected, false, coordX, coordY)){
            workerSelected.buildBlock(true, coordX, coordY);
            GameManager.getInstance().getCurrConnection().asyncSend(Map.getInstance());
            //STOP TURN
        }
    }

    public void doubleConstruction(int coordX, int coordY){
        if(ActionManager.getInstance().verifyCoordinateConstruction(workerSelected, true, coordX, coordY)){
            workerSelected.buildBlock(true, coordX, coordY);
            GameManager.getInstance().getCurrConnection().asyncSend(Map.getInstance());
            //STOP TURN
        }
    }

    public void setAllowHeight(boolean allowHeight) {
        this.allowHeight = allowHeight;
    }

    public boolean cannotGoUp() {
        return !allowHeight;
    }

    public void setAllowHeightPrometheus(boolean allowHeightPrometheus) {
        this.allowHeightPrometheus = allowHeightPrometheus;
    }

    public boolean cannotGoUpPrometheus() {
        return !allowHeightPrometheus;
    }

}

