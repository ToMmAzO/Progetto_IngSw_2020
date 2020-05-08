package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.SystemMessage;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.God;
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

    public void workerChoice(int selectionWorker){
        if(selectionWorker == 1 || selectionWorker == 2){
            workerSelected = GameManager.getInstance().getCurrPlayer().getWorkerSelected(selectionWorker);
            if(workerSelected.canMove()){
                if(GameManager.getInstance().getCurrPlayer().getGodChoice() == God.PROMETHEUS && workerSelected.canBuild()){
                    GameManager.getInstance().getCurrConnection().asyncSend(Map.getInstance());
                    GameManager.getInstance().getCurrConnection().asyncSend(new Message_QuestionPrometheus());
                } else{
                    GameManager.getInstance().getCurrConnection().asyncSend(Map.getInstance());
                    GameManager.getInstance().getCurrConnection().asyncSend(new Message_Movement());
                }
            } else{
                SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantMove);
                if(selectionWorker == 1){
                    selectionWorker ++;
                } else{
                    selectionWorker --;
                }
                workerSelected = GameManager.getInstance().getCurrPlayer().getWorkerSelected(selectionWorker);
                if(!workerSelected.canMove()){
                    SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantDoNothing);
                    GameManager.getInstance().deletePlayer(GameManager.getInstance().getCurrPlayer());
                }
                workerSelected = null;
            }
        } else{
            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().contentError);
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
            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantMove);
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
                                SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantMove);
                                GameManager.getInstance().getCurrConnection().asyncSend(new Message_Construction());
                            }
                        }
                        case ATLAS -> {
                            if(workerSelected.canBuild()) {
                                GameManager.getInstance().getCurrConnection().asyncSend(new Message_QuestionAtlas());
                            }else{
                                SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantBuild);
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
                                SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantBuild);
                                //STOP TURN
                            }
                        }
                        default -> {
                            if(workerSelected.canBuild()) {
                                GameManager.getInstance().getCurrConnection().asyncSend(new Message_Construction());
                            }else{
                                SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantBuild);
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
            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantComeBack);
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
                        SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantBuild);
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
                if (workerSelected.canBuild(buildX, buildY)) {
                    GameManager.getInstance().getCurrConnection().asyncSend(new Message_QuestionDemeter());
                } else {
                    SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantBuild);
                    //STOP TURN
                }
            } else {
                //STOP TURN
            }
        }
    }

    public void secondConstruction(int coordX, int coordY){
        if (buildX == coordX && buildY == coordY) {
            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantRebuild);
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

