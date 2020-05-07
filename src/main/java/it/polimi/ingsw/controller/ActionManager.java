package it.polimi.ingsw.controller;

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
                                    System.out.print("ATTENTO, c'è attivo il potere di ATHENA! ");
                                }
                            } else {
                                if (g == God.PROMETHEUS && TurnManager.getInstance().cannotGoUpPrometheus()) {
                                    if (Map.getInstance().getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ()) {
                                        return true;
                                    } else {
                                        System.out.print("ATTENTO, hai appena costruito e sei PROMETHEUS! ");
                                    }
                                } else if (Map.getInstance().getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ() + 1) {
                                    return true;
                                } else {
                                    System.out.print("ATTENTO, non puoi salire di due livelli! ");
                                }
                            }
                        } else {
                            System.out.print("ATTENTO, c'è una cupola! ");
                        }
                    } else {
                        if (g == God.APOLLO) {
                            if (!Map.getInstance().getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3).equals(w.getIdWorker().substring(0, 3))) {
                                if (Map.getInstance().getCellBlockType(coordX, coordY) != BlockType.CUPOLA) {
                                    if (TurnManager.getInstance().cannotGoUp()) {
                                        if (Map.getInstance().getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ()) {
                                            return true;
                                        } else {
                                            System.out.print("ATTENTO, c'è attivo il potere di ATHENA! ");
                                        }
                                    } else {
                                        if (Map.getInstance().getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ() + 1) {
                                            return true;
                                        } else {
                                            System.out.print("ATTENTO, non puoi salire di due livelli! ");
                                        }
                                    }
                                } else {
                                    System.out.print("ATTENTO, c'è una cupola! ");
                                }
                            } else {
                                System.out.print("ATTENTO, non è un worker avversario! ");
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
                                                System.out.print("ATTENTO, non puoi spingere quel worker! ");
                                            }
                                        } else {
                                            System.out.print("ATTENTO, c'è attivo il potere di ATHENA! ");
                                        }
                                    } else {
                                        if (Map.getInstance().getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ() + 1) {
                                            if (w.canPush(coordX, coordY)) {
                                                return true;
                                            } else {
                                                System.out.print("ATTENTO, non puoi spingere quel worker! ");
                                            }
                                        } else {
                                            System.out.print("ATTENTO, non puoi salire di due livelli! ");
                                        }
                                    }
                                } else {
                                    System.out.print("ATTENTO, c'è una cupola! ");
                                }
                            } else {
                                System.out.print("ATTENTO, non è un worker avversario! ");
                            }
                        }
                        if (g != God.APOLLO && g != God.MINOTAUR) {
                            System.out.print("ATTENTO, c'è un altro worker! ");
                        }
                    }
                } else {
                    System.out.print("ATTENTO, ci sei già tu! ");
                }
            } else {
                System.out.print("Devi selezionare una delle 8 caselle intorno a worker! ");
            }
        } else {
            System.out.print("Puoi inserire solo interi da 0 a 4! ");
        }
        return false;
    }

    public boolean verifyCoordinateConstruction(Worker w, boolean doubleConstruction, int coordX, int coordY) {
        if (validCoords(coordX, coordY)) {
            if (isAround(w.getCoordX(), w.getCoordY(), coordX, coordY)) {
                if (w.getCoordX() != coordX || w.getCoordY() != coordY) {
                    if (Map.getInstance().noWorkerHere(coordX, coordY)) {
                        if (Map.getInstance().getCellBlockType(coordX, coordY) != BlockType.CUPOLA) {
                            if (doubleConstruction) {
                                if (Map.getInstance().getCellBlockType(coordX, coordY).getAbbreviation() < 2) {
                                    return true;
                                } else {
                                    System.out.print("ATTENTO, sei Hephaestus! ");
                                }
                            } else {
                                return true;
                            }
                        } else {
                            System.out.print("ATTENTO, c'è una cupola! ");
                        }
                    } else {
                        System.out.print("ATTENTO, c'è un altro worker! ");
                    }
                } else {
                    System.out.print("ATTENTO, ci sei già tu! ");
                }
            } else {
                System.out.print("Devi selezionare una delle 8 caselle intorno a worker! ");
            }
        } else {
            System.out.print("Puoi inserire solo interi da 0 a 4! ");
        }
        return false;
    }

}