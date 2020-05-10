package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.SystemMessage;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.workers.Worker;
import it.polimi.ingsw.network.message.*;

public class TurnManager {

    private static TurnManager turnManager = null;
    private Worker workerSelected;
    private boolean allowHeight = true;
    private boolean allowHeightPrometheus = true;
    private boolean allowWin;
    private int startX, startY, buildX, buildY;

    public TurnManager(){
        turnManager = this;
    }

    public static TurnManager getInstance(){
        return turnManager;
    }

    public void workerChoice(int selectionWorker){
        if(selectionWorker == 1 || selectionWorker == 2){
            if(GameManager.getInstance().getCurrPlayer().getWorkerSelected(1).canMove() || GameManager.getInstance().getCurrPlayer().getWorkerSelected(2).canMove()){
                workerSelected = GameManager.getInstance().getCurrPlayer().getWorkerSelected(selectionWorker);
                if(workerSelected.canMove()){
                    if(GameManager.getInstance().getCurrPlayer().getGodChoice() == God.PROMETHEUS && workerSelected.canBuild()){
                        Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.QUESTION_PROMETHEUS);
                    } else{
                        Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.MOVEMENT);
                    }
                } else{
                    SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantMove);
                }
            } else{
                SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantDoNothing);
                GameManager.getInstance().deletePlayer(GameManager.getInstance().getCurrPlayer());
            }
            workerSelected = null;
        } else{
            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().contentError);
        }
    }

    public void prebuildPrometheus(int coordX, int coordY){
        if(ActionManager.getInstance().verifyCoordinateConstruction(workerSelected, false, coordX, coordY)){
            workerSelected.buildBlock(coordX, coordY);
            setAllowHeightPrometheus(false);
        }
        if(workerSelected.canMove()){
            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.MOVEMENT);
        } else{
            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantMove);
            setAllowHeightPrometheus(true);
            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
            GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
        }
    }

    public void movement(int coordX, int coordY){
        if(ActionManager.getInstance().verifyCoordinateMovement(workerSelected, GameManager.getInstance().getCurrPlayer().getGodChoice(), coordX, coordY)){
            if(GameManager.getInstance().getCurrPlayer().getGodChoice() == God.ARTEMIS){
                startX = workerSelected.getCoordX();
                startY = workerSelected.getCoordY();
            }

            if(win(coordX, coordY)){
                workerSelected.changePosition(coordX, coordY);
                GameManager.getInstance().endGame(GameManager.getInstance().getCurrPlayer());
            } else{
                workerSelected.changePosition(coordX, coordY);
                switch (GameManager.getInstance().getCurrPlayer().getGodChoice()){
                    case ARTEMIS -> {
                        if(workerSelected.canMove(startX, startY)) {
                            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.QUESTION_ARTEMIS);
                        } else {
                            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantMove);
                            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.CONSTRUCTION);
                        }
                    }
                    case ATLAS -> {
                        if(workerSelected.canBuild()) {
                            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.QUESTION_ATLAS);
                        }else{
                            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantBuild);
                            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
                            GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
                        }
                    }
                    case HEPHAESTUS -> {
                        if(workerSelected.canBuild()) {
                            if(workerSelected.canBuild(true)) {
                                Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.QUESTION_HEPHAESTUS);
                            } else {
                                SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantDoubleBuild);
                                Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.CONSTRUCTION);
                            }
                        }else{
                            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantBuild);
                            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
                            GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
                        }
                    }
                    default -> {
                        if(workerSelected.canBuild()) {
                            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.CONSTRUCTION);
                        } else{
                            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantBuild);
                            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
                            GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
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
                if (win(coordX, coordY)) {
                    workerSelected.changePosition(coordX, coordY);
                    GameManager.getInstance().endGame(GameManager.getInstance().getCurrPlayer());
                } else {
                    workerSelected.changePosition(coordX, coordY);
                    if(workerSelected.canBuild()) {
                        Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.CONSTRUCTION);
                    }else{
                        SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantBuild);
                        Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
                        GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
                    }
                }
            }
        }
    }

    public boolean win(int x, int y){
        if((x == 0 || x == 4 || y == 0 || y == 4) && cannotWin()){
            if(GameManager.getInstance().getCurrPlayer().getGodChoice() == God.HERA){
                return workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 3;
            }else {
                return false;
            }
        }else {
            switch (GameManager.getInstance().getCurrPlayer().getGodChoice()) {
                case CHRONUS -> {
                    return (Map.getInstance().numberOfTurrets() >= 5)
                            || (workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 3);
                }
                case PAN -> {
                    return (workerSelected.getCoordZ() == 3 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 1)
                            || (workerSelected.getCoordZ() == 3 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 0)
                            || (workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 0)
                            || (workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 3);
                }
                default -> {
                    return workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 3;
                }
            }
        }
    }

    public void construction(int coordX, int coordY){
        if(ActionManager.getInstance().verifyCoordinateConstruction(workerSelected, false, coordX, coordY)){
            workerSelected.buildBlock(coordX, coordY);
            switch (GameManager.getInstance().getCurrPlayer().getGodChoice()){
                case DEMETER -> {
                    buildX = coordX;
                    buildY = coordY;
                    if (workerSelected.canBuild(buildX, buildY)) {
                        Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.QUESTION_DEMETER);
                    } else {
                        SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantBuild);
                        Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
                        GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
                    }
                }
                case HESTIA -> {
                    if (workerSelected.canBuild(true)) {
                        Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.QUESTION_HESTIA);
                    } else {
                        SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantBuild);
                        Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
                        GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
                    }
                }
                default -> Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
            }
        }
    }

    public void secondConstructionDemeter(int coordX, int coordY){
        if (buildX == coordX && buildY == coordY) {
            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantRebuild);
        } else {
            if(ActionManager.getInstance().verifyCoordinateConstruction(workerSelected, false, coordX, coordY)){
                workerSelected.buildBlock(coordX, coordY);
                Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
                GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
            }
        }
    }

    public void secondConstructionHestia(int coordX, int coordY){
        if (coordX == 0 || coordX == 4 || coordY == 0 || coordY == 4) {
            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantRebuild);
        } else {
            if(ActionManager.getInstance().verifyCoordinateConstruction(workerSelected, false, coordX, coordY)){
                workerSelected.buildBlock(coordX, coordY);
                Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
                GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
            }
        }
    }

    public void constructionCupola(int coordX, int coordY){
        if(ActionManager.getInstance().verifyCoordinateConstruction(workerSelected, false, coordX, coordY)){
            workerSelected.buildBlock(true, coordX, coordY);
            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
            GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
        }
    }

    public void doubleConstruction(int coordX, int coordY){
        if(ActionManager.getInstance().verifyCoordinateConstruction(workerSelected, true, coordX, coordY)){
            workerSelected.buildBlock(true, coordX, coordY);
            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
            GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
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

    public void setAllowWin(boolean allowWin) {
        this.allowWin = allowWin;
    }

    public boolean cannotWin() {
        return !allowWin;
    }

}

