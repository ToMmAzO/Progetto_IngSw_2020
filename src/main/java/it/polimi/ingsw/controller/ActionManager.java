package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Cards.God;
import it.polimi.ingsw.model.Workers.Worker;

public class ActionManager {

    public static boolean isAround(int x, int y, int newX, int newY){
        return newX >= x - 1 && newX <= x + 1 && newY >= y - 1 && newY <= y + 1;
    }
    
    public static boolean validCoords(int i, int j){
        return (i >= 0 && i <= 4) && (j >= 0 && j <= 4);
    }

    public static boolean verifyCoordinateMovement(Worker w, God g, int coordX, int coordY){
        if(validCoords(coordX, coordY)){
            if(isAround(w.getCoordX(), w.getCoordY(), coordX, coordY)){
                if(w.getCoordX() != coordX || w.getCoordY() != coordY){
                    if(Map.noWorkerHere(coordX, coordY)){
                        if(Map.getCellBlockType(coordX, coordY) != BlockType.CUPOLA){
                            if(TurnManager.cannotGoUp()){
                                if(Map.getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ()){
                                    return true;
                                } else{
                                    System.out.print("ATTENTO, c'è attivo il potere di ATHENA! ");
                                }
                            } else{
                                if(g == God.PROMETHEUS && TurnManager.cannotGoUpPrometheus()){
                                    if(Map.getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ()){
                                        return true;
                                    } else{
                                        System.out.print("ATTENTO, hai appena costruito e sei PROMETHEUS! ");
                                    }
                                }else if(Map.getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ() + 1){
                                    return true;
                                } else{
                                    System.out.print("ATTENTO, non puoi salire di due livelli! ");
                                }
                            }
                        } else{
                            System.out.print("ATTENTO, c'è una cupola! ");
                        }
                    } else{
                        if(g == God.APOLLO){
                            if(!Map.getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3).equals(w.getIdWorker().substring(0, 3))){
                                if(Map.getCellBlockType(coordX, coordY) != BlockType.CUPOLA){
                                    if(TurnManager.cannotGoUp()){
                                        if(Map.getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ()){
                                            return true;
                                        } else{
                                            System.out.print("ATTENTO, c'è attivo il potere di ATHENA! ");
                                        }
                                    } else{
                                        if(Map.getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ() + 1){
                                            return true;
                                        } else{
                                            System.out.print("ATTENTO, non puoi salire di due livelli! ");
                                        }
                                    }
                                } else{
                                    System.out.print("ATTENTO, c'è una cupola! ");
                                }
                            } else{
                                System.out.print("ATTENTO, non è un worker avversario! ");
                            }
                        }
                        if(g == God.MINOTAUR){
                            if(!Map.getWorkerInCell(coordX, coordY).getIdWorker().substring(0, 3).equals(w.getIdWorker().substring(0, 3))){
                                if(Map.getCellBlockType(coordX, coordY) != BlockType.CUPOLA){
                                    if(TurnManager.cannotGoUp()){
                                        if(Map.getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ()){
                                            if (w.canPush(coordX, coordY)){
                                                return true;
                                            } else{
                                                System.out.print("ATTENTO, non puoi spingere quel worker! ");
                                            }
                                        } else{
                                            System.out.print("ATTENTO, c'è attivo il potere di ATHENA! ");
                                        }
                                    } else{
                                        if(Map.getCellBlockType(coordX, coordY).getAbbreviation() <= w.getCoordZ() + 1){
                                            if(w.canPush(coordX, coordY)){
                                                return true;
                                            } else{
                                                System.out.print("ATTENTO, non puoi spingere quel worker! ");
                                            }
                                        } else{
                                            System.out.print("ATTENTO, non puoi salire di due livelli! ");
                                        }
                                    }
                                } else{
                                    System.out.print("ATTENTO, c'è una cupola! ");
                                }
                            } else{
                                System.out.print("ATTENTO, non è un worker avversario! ");
                            }
                        }
                        if(g != God.APOLLO && g != God.MINOTAUR){
                            System.out.print("ATTENTO, c'è un altro worker! ");
                        }
                    }
                } else{
                    System.out.print("ATTENTO, ci sei già tu! ");
                }
            } else{
                System.out.print("Devi selezionare una delle 8 caselle intorno a worker! ");
            }
        } else{
            System.out.print("Puoi inserire solo interi da 0 a 4! ");
        }
        return false;
    }

    public static boolean verifyCoordinateConstruction(Worker w, int coordX, int coordY){
        if(validCoords(coordX, coordY)){
            if(isAround(w.getCoordX(), w.getCoordY(), coordX, coordY)){
                if(w.getCoordX() != coordX || w.getCoordY() != coordY){
                    if(Map.noWorkerHere(coordX, coordY)){
                        if(Map.getCellBlockType(coordX, coordY) != BlockType.CUPOLA){
                            return true;
                        } else{
                            System.out.print("ATTENTO, c'è una cupola! ");
                        }
                    } else{
                        System.out.print("ATTENTO, c'è un altro worker! ");
                    }
                } else{
                    System.out.print("ATTENTO, ci sei già tu! ");
                }
            } else{
                System.out.print("Devi selezionare una delle 8 caselle intorno a worker! ");
            }
        } else{
            System.out.print("Puoi inserire solo interi da 0 a 4! ");
        }
        return false;
    }

}
