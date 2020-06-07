package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.game.Game;
import it.polimi.ingsw.model.game.GameState;
import it.polimi.ingsw.model.game.SystemMessage;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.workers.Worker;

public class TurnManager {

    private static TurnManager turnManager;
    private Worker workerSelected;
    private boolean allowHeightAthena = true;
    private boolean allowHeightPrometheus = true;
    private boolean allowWinHera = true;
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
                    workerSelected = null;
                }
            } else{
                SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantDoNothing);
                workerSelected = null;
                GameManager.getInstance().deletePlayer(GameManager.getInstance().getCurrPlayer());
            }
        } else{
            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().contentError);
        }
    }

    public void prebuildPrometheus(int coordX, int coordY){
        if(ActionManager.getInstance().verifyCoordinateConstruction(workerSelected, GameManager.getInstance().getCurrPlayer().getGodChoice(), false, coordX, coordY)){
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
                    case APOLLO -> {
                        if(workerSelected.canBuild()) {
                            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.CONSTRUCTION);
                        } else{
                            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantBuild);
                            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
                            GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
                        }
                    }
                    case ARTEMIS -> {
                        if(workerSelected.canDoAgain(startX, startY)) {
                            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.QUESTION_ARTEMIS);
                        } else {
                            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantMove);
                            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.CONSTRUCTION);
                        }
                    }
                    case ATLAS -> Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.QUESTION_ATLAS);
                    case HEPHAESTUS -> {
                        if(workerSelected.canBuild()) {
                            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.QUESTION_HEPHAESTUS);
                        } else {
                            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantDoubleBuild);
                            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.CONSTRUCTION);
                        }
                    }
                    case TRITON -> {
                        if(workerSelected.getCoordX() == 0 || workerSelected.getCoordX() == 4 || workerSelected.getCoordY() == 0 || workerSelected.getCoordY() == 4) {
                            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.QUESTION_TRITON);
                        } else{
                            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.CONSTRUCTION);
                        }
                    }
                    default -> Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.CONSTRUCTION);
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
                    Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.CONSTRUCTION);
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
                    if(Map.getInstance().getNumberOfCompleteTurrets() >= 5){
                        return true;
                    }
                }
                case PAN -> {
                    if((workerSelected.getCoordZ() == 3 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 1)
                            || (workerSelected.getCoordZ() == 3 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 0)
                            || (workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 0)){
                        return true;
                    }
                }
            }
            return workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 3;
        }
    }

    public void construction(int coordX, int coordY){
        if(ActionManager.getInstance().verifyCoordinateConstruction(workerSelected, GameManager.getInstance().getCurrPlayer().getGodChoice(), false, coordX, coordY)){
            workerSelected.buildBlock(coordX, coordY);
            switch (GameManager.getInstance().getCurrPlayer().getGodChoice()){
                case CHRONUS -> {
                    if(win(workerSelected.getCoordX(), workerSelected.getCoordY())){
                        GameManager.getInstance().endGame(GameManager.getInstance().getCurrPlayer());
                    } else{
                        Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
                        GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
                    }
                }
                case DEMETER -> {
                    buildX = coordX;
                    buildY = coordY;
                    if (workerSelected.canDoAgain(buildX, buildY)) {
                        Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.QUESTION_DEMETER);
                    } else {
                        SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantBuild);
                        Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
                        GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
                    }
                }
                case HESTIA -> {
                    if (workerSelected.canBuild()) {
                        Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.QUESTION_HESTIA);
                    } else {
                        SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantBuild);
                        Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
                        GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
                    }
                }
                default -> {
                    Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
                    GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
                }
            }
        }
    }

    public void secondConstructionDemeter(int coordX, int coordY){
        if (buildX == coordX && buildY == coordY) {
            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantRebuildDemeter);
        } else {
            if(ActionManager.getInstance().verifyCoordinateConstruction(workerSelected, GameManager.getInstance().getCurrPlayer().getGodChoice(), false, coordX, coordY)){
                workerSelected.buildBlock(coordX, coordY);
                Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
                GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
            }
        }
    }

    public void secondConstructionHestia(int coordX, int coordY){
        if (coordX == 0 || coordX == 4 || coordY == 0 || coordY == 4) {
            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantRebuildHestia);
        } else {
            if(ActionManager.getInstance().verifyCoordinateConstruction(workerSelected, GameManager.getInstance().getCurrPlayer().getGodChoice(), false, coordX, coordY)){
                workerSelected.buildBlock(coordX, coordY);
                Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
                GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
            }
        }
    }

    public void constructionCupola(int coordX, int coordY){
        if(ActionManager.getInstance().verifyCoordinateConstruction(workerSelected, GameManager.getInstance().getCurrPlayer().getGodChoice(), false, coordX, coordY)){
            workerSelected.specialBuild(coordX, coordY);
            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
            GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
        }
    }

    public void doubleConstruction(int coordX, int coordY){
        if(ActionManager.getInstance().verifyCoordinateConstruction(workerSelected, GameManager.getInstance().getCurrPlayer().getGodChoice(), true, coordX, coordY)){
            workerSelected.specialBuild(coordX, coordY);
            Game.getInstance().setGameState(GameManager.getInstance().getCurrPlayer(), GameState.WAIT_TURN);
            GameManager.getInstance().nextPlayer(GameManager.getInstance().getCurrPlayer());
        }
    }

    public Worker getWorkerSelected(){
        return workerSelected;
    }

    public void setAllowHeightAthena(boolean allowHeightAthena) {
        this.allowHeightAthena = allowHeightAthena;
    }

    public boolean cannotGoUp() {
        return !allowHeightAthena;
    }

    public void setAllowHeightPrometheus(boolean allowHeightPrometheus) {
        this.allowHeightPrometheus = allowHeightPrometheus;
    }

    public boolean cannotGoUpPrometheus() {
        return !allowHeightPrometheus;
    }

    public void setAllowWinHera(boolean allowWinHera) {
        this.allowWinHera = allowWinHera;
    }

    public boolean cannotWin() {
        return !allowWinHera;
    }

}

