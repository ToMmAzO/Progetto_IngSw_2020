package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.game.SystemMessage;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.workers.Worker;

public class ActionManager {

    private static ActionManager actionManager;

    public ActionManager(){
        actionManager = this;
    }

    public static ActionManager getInstance(){
        return actionManager;
    }

    /**
     * Verify that the new coordinates are around the workers's coordinates
     *
     * @param x row coordinate of the worker
     * @param y column coordinate of the worker
     * @param newX new row coordinate
     * @param newY new row coordinate
     *
     * @return true if the new coordinates are around the workers's coordinates
     */
    public boolean isAround(int x, int y, int newX, int newY) {
        return newX >= x - 1 && newX <= x + 1 && newY >= y - 1 && newY <= y + 1;
    }

    /**
     * Verify that these coordinates are in the map
     *
     * @param x row coordinate to be verified
     * @param y column coordinate to be verified
     *
     * @return true if they are map's coordinates
     */
    public boolean validCoords(int x, int y) {
        return (x >= 0 && x <= 4) && (y >= 0 && y <= 4);
    }

    /**
     * Verify that the worker can do the movement in these coordinates
     *
     * @param w worker that must move
     * @param g worker's god
     * @param coordX new row coordinate
     * @param coordY new column coordinate
     *
     * @return true if can move
     */
    public boolean verifyCoordinateMovement(Worker w, God g, int coordX, int coordY) {
        if (validCoords(coordX, coordY)) {
            if (isAround(w.getCoordX(), w.getCoordY(), coordX, coordY)) {
                if (w.getCoordX() != coordX || w.getCoordY() != coordY) {
                    if (Map.getInstance().noWorkerHere(coordX, coordY)) {
                        if (Map.getInstance().getCellBlockType(coordX, coordY) != BlockType.CUPOLA) {
                            if (TurnManager.getInstance().cannotGoUp()) {
                                if (Map.getInstance().getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ()) {
                                    return true;
                                } else {
                                    if(g == God.ATHENA){
                                        return true;
                                    } else {
                                        SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().powerAthena);
                                    }
                                }
                            } else {
                                if (g == God.PROMETHEUS && TurnManager.getInstance().cannotGoUpPrometheus()) {
                                    if (Map.getInstance().getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ()) {
                                        return true;
                                    } else {
                                        SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().powerPrometheus);
                                    }
                                } else if (Map.getInstance().getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ() + 1) {
                                    return true;
                                } else {
                                    SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().doubleGoUp);
                                }
                            }
                        } else {
                            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cupolaPresence);
                        }
                    } else {
                        if (g == God.APOLLO) {
                            if (!Map.getInstance().getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3).equals(w.getIdWorker().substring(0, 3))) {
                                if (TurnManager.getInstance().cannotGoUp()) {
                                    if (Map.getInstance().getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ()) {
                                        return true;
                                    } else {
                                        SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().powerAthena);
                                    }
                                } else {
                                    if (Map.getInstance().getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ() + 1) {
                                        return true;
                                    } else {
                                        SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().doubleGoUp);
                                    }
                                }
                            } else {
                                SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().yourWorker);
                            }
                        }
                        if (g == God.MINOTAUR) {
                            if (!Map.getInstance().getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3).equals(w.getIdWorker().substring(0, 3))) {
                                if (TurnManager.getInstance().cannotGoUp()) {
                                    if (Map.getInstance().getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ()) {
                                        if (w.canPush(coordX, coordY)) {
                                            return true;
                                        } else {
                                            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantPush);
                                        }
                                    } else {
                                        SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().powerAthena);
                                    }
                                } else {
                                    if (Map.getInstance().getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ() + 1) {
                                        if (w.canPush(coordX, coordY)) {
                                            return true;
                                        } else {
                                            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantPush);
                                        }
                                    } else {
                                        SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().doubleGoUp);
                                    }
                                }
                            } else {
                                SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().yourWorker);
                            }
                        }
                        if (g != God.APOLLO && g != God.MINOTAUR) {
                            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().workerPresence);
                        }
                    }
                } else {
                    SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().yourPosition);
                }
            } else {
                SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().distantCell);
            }
        } else {
            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().exceedMap);
        }
        return false;
    }

    /**
     * Verify that the worker can do the construction in these coordinates
     *
     * @param w worker that must build
     * @param g worker's god
     * @param doubleConstruction true if the worker can do the double construction
     * @param coordX new row coordinate
     * @param coordY new column coordinate
     *
     * @return true if can build
     */
    public boolean verifyCoordinateConstruction(Worker w, God g, boolean doubleConstruction, int coordX, int coordY) {
        if (validCoords(coordX, coordY)) {
            if (isAround(w.getCoordX(), w.getCoordY(), coordX, coordY)) {
                if (w.getCoordX() != coordX || w.getCoordY() != coordY) {
                    if (Map.getInstance().noWorkerHere(coordX, coordY)) {
                        if (Map.getInstance().getCellBlockType(coordX, coordY) != BlockType.CUPOLA) {
                            if (doubleConstruction) {
                                if (Map.getInstance().getCellBlockType(coordX, coordY).getAbbreviation() < 2) {
                                    return true;
                                } else {
                                    SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().youHephaestus);
                                }
                            } else {
                                return true;
                            }
                        } else {
                            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cupolaPresence);
                        }
                    } else {
                        SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().workerPresence);
                    }
                } else {
                    if(g == God.ZEUS){
                        if(Map.getInstance().getCellBlockType(coordX, coordY).getAbbreviation() < 3) {
                            return true;
                        }else{
                            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cantBuild);
                        }
                    }else {
                        SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().yourPosition);
                    }
                }
            } else {
                SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().distantCell);
            }
        } else {
            SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().exceedMap);
        }
        return false;
    }

}