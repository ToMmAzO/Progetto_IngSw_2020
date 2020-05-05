package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Workers.Worker;

public class TurnManager {

    private static Worker workerSelected;
    private static boolean allowHeight = true;
    private static boolean allowHeightPrometheus = true;
    private static int startX, startY;
    private static int buildX, buildY;

    public static boolean verifyRegularity(Player player, int workerChosen){
        setWorkerSelected(player.getWorkerSelected(workerChosen));
        if(workerSelected.canMove()){
            return true;
        } else{
            System.out.println(workerSelected.getIdWorker() + " non può muoversi! Seleziono l'altro Worker.");      //il controller può scrivere?
            if(workerChosen == 1){
                workerChosen ++;
            } else{
                workerChosen --;
            }
            setWorkerSelected(player.getWorkerSelected(workerChosen));
            System.out.println("Il worker selezionato è: "+ workerSelected.getIdWorker() + ".");
            if(workerSelected.canMove()){
                return true;
            } else{
                System.out.println("Nemmeno " + workerSelected.getIdWorker() + " può muoversi!");
                workerSelected = null;
                return false;
            }
        }
    }

    public static boolean movement(int x, int y){
        if(workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 3) {
            workerSelected.changePosition(x, y);
            GameManager.setVictory();
            return true;
        } else{
            workerSelected.changePosition(x, y);
            return false;
        }
    }

    public static boolean movementPan(int x, int y){
        if((workerSelected.getCoordZ() == 3 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 1)
                || (workerSelected.getCoordZ() == 3 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 0)
                || (workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 0)
                || (workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 3)){
            workerSelected.changePosition(x, y);
            GameManager.setVictory();
            return true;
        } else{
            workerSelected.changePosition(x, y);
            return false;
        }
    }

    public static void construction(int x, int y){
        workerSelected.buildBlock(x, y);
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

    public static Worker getWorkerSelected() {
        return workerSelected;
    }

    public static void setWorkerSelected(Worker workerSelected) {
        TurnManager.workerSelected = workerSelected;
    }

    public static int getStartX() {
        return startX;
    }

    public static void setStartX(int startX) {
        TurnManager.startX = startX;
    }

    public static int getStartY() {
        return startY;
    }

    public static void setStartY(int startY) {
        TurnManager.startY = startY;
    }

    public static int getBuildX() {
        return buildX;
    }

    public static void setBuildX(int buildX) {
        TurnManager.buildX = buildX;
    }

    public static int getBuildY() {
        return buildY;
    }

    public static void setBuildY(int buildY) {
        TurnManager.buildY = buildY;
    }
}

