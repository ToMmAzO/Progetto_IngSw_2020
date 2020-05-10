package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.game.SystemMessage;
import it.polimi.ingsw.model.board.BlockType;
import it.polimi.ingsw.model.board.Map;
import it.polimi.ingsw.model.cards.God;
import it.polimi.ingsw.model.workers.Worker;

public class ActionManager {

    private static ActionManager actionManager = null;

    public ActionManager(){
        actionManager = this;
    }

    public static ActionManager getInstance(){
        return actionManager;
    }

    public boolean isAround(int x, int y, int newX, int newY) {
        return newX >= x - 1 && newX <= x + 1 && newY >= y - 1 && newY <= y + 1;
    }

    public boolean validCoords(int i, int j) {
        return (i >= 0 && i <= 4) && (j >= 0 && j <= 4);
    }

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
                                    SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().powerAthena);
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
                                if (Map.getInstance().getCellBlockType(coordX, coordY) != BlockType.CUPOLA) {
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
                                    SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cupolaPresence);
                                }
                            } else {
                                SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().yourWorker);
                            }
                        }
                        if (g == God.MINOTAUR) {
                            if (!Map.getInstance().getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3).equals(w.getIdWorker().substring(0, 3))) {
                                if (Map.getInstance().getCellBlockType(coordX, coordY) != BlockType.CUPOLA) {
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
                                    SystemMessage.getInstance().serverMessage(SystemMessage.getInstance().cupolaPresence);
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
                        return true;
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