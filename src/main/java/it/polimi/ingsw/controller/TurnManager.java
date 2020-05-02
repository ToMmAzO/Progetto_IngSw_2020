package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Workers.Worker;

public class TurnManager {

    private static Worker workerSelected;
    private static boolean allowHeight = true;
    private static boolean allowHeightPrometheus = true;

    /*
    public static boolean startTurn(Player player){
        if(verifyRegularity(player)){       //ActionManager.workerChoice(player)
            selectAction(player);
            workerSelected = null;
            return true;
        } else{
            GameManager.deletePlayer(player);
            workerSelected = null;
            return false;
        }
    }
    */

    public static boolean verifyRegularity(Player player, int workerChosen){
        workerSelected = player.getWorkerSelected(workerChosen);
        if(workerSelected.canMove()){
            return true;
        } else{
            System.out.println(workerSelected.getIdWorker() + " non può muoversi! Seleziono l'altro Worker.");      //il controller può scrivere?
            if(workerChosen == 1){
                workerChosen ++;
            } else{
                workerChosen --;
            }
            workerSelected = player.getWorkerSelected((workerChosen));
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

    /*

    private static void selectAction(Player player){
        switch (player.getGodChoice()){
            case ARTEMIS -> actionArtemis(player);
            case ATLAS -> actionAtlas(player);
            case DEMETER -> actionDemeter(player);
            case HEPHAESTUS -> actionHephaestus(player);
            case PAN -> actionPan(player);
            case PROMETHEUS -> actionPrometheus(player);
            default -> actionDefault(player);//APOLLO, ATHENA, ATLAS, MINOTAUR
        }
    }

    private static void actionArtemis(Player player){
        int[] coords;
        int startX = workerSelected.getCoordX();
        int startY = workerSelected.getCoordY();
        System.out.println("MOVIMENTO:");
        if(movement(ActionManager.insertCoordinateMovement(workerSelected, player.getGodChoice()))){
            return;
        }
        if(workerSelected.canMove(startX, startY)){
            System.out.println("Vuoi muovere ancora? (Yes o No)");
            String answer = ActionManager.yesOrNo();
            if(answer.equals("yes")) {
                coords = ActionManager.insertCoordinateMovement(workerSelected, player.getGodChoice());
                while(startX == coords[0] && startY == coords[1]) {
                    System.out.print("Non puoi tornare indietro! ");
                    coords = ActionManager.insertCoordinateMovement(workerSelected, player.getGodChoice());
                }
                if(movement(coords)){
                    return;
                }
            }
        } else{
            System.out.println(workerSelected.getIdWorker() + " NON può muoversi una seconda volta!");
        }
        if(workerSelected.canBuild()) {
            System.out.println("COSTRUZIONE:");
            construction(ActionManager.insertCoordinateConstruction(workerSelected));
        } else{
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }
    }

    private static void actionAtlas(Player player){
        int[] coords;
        System.out.println("MOVIMENTO:");
        if(movement(ActionManager.insertCoordinateMovement(workerSelected, player.getGodChoice()))){
            return;
        }
        if(workerSelected.canBuild()){
            System.out.println("COSTRUZIONE:");
            System.out.println("Vuoi costruire una CUPOLA? (Yes o No)");
            String answer = ActionManager.yesOrNo();
            if(answer.equals("yes")){
                coords = ActionManager.insertCoordinateConstruction(workerSelected);
                workerSelected.buildBlock(true, coords[0], coords[1]);
            } else{
                coords = ActionManager.insertCoordinateConstruction(workerSelected);
                workerSelected.buildBlock(false, coords[0], coords[1]);
            }
        } else{
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }
    }

    private static void actionDemeter(Player player){
        int[] coords;
        int x, y;
        System.out.println("MOVIMENTO:");
        if(movement(ActionManager.insertCoordinateMovement(workerSelected, player.getGodChoice()))){
            return;
        }
        if(workerSelected.canBuild()) {
            System.out.println("COSTRUZIONE:");
            coords = ActionManager.insertCoordinateConstruction(workerSelected);
            x = coords[0];
            y = coords[1];
            construction(coords);
            if(workerSelected.canBuild(x, y)){
                System.out.println("Vuoi costruire ancora? (Yes o No)");
                String answer = ActionManager.yesOrNo();
                if(answer.equals("yes")){
                    Map.getInstance().print();
                    GameManager.printPlayerInGame();
                    coords =  ActionManager.insertCoordinateConstruction(workerSelected);
                    while(x == coords[0] && y == coords[1]) {
                        System.out.print("Non puoi costruire nello stesso punto di prima! ");
                        coords = ActionManager.insertCoordinateConstruction(workerSelected);
                    }
                    construction(coords);
                }
            } else{
                System.out.println(workerSelected.getIdWorker() + " NON può costruire una seconda volta!");
            }
        } else{
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }
    }

    private static void actionHephaestus(Player player){
        int[] coords;
        System.out.println("MOVIMENTO:");
        if(movement(ActionManager.insertCoordinateMovement(workerSelected, player.getGodChoice()))){
            return;
        }
        if(workerSelected.canBuild()) {
            System.out.println("COSTRUZIONE:");
            System.out.println("Vuoi costruire 2 blocchi? (Yes o No)");
            String answer = ActionManager.yesOrNo();
            if((answer.equals("yes")) && workerSelected.canBuild(true)) {
                coords = ActionManager.insertCoordinateConstruction(workerSelected);
                workerSelected.buildBlock(true, coords[0], coords[1]);
            } else {
                if(!workerSelected.canBuild(true)) {
                    System.out.println(workerSelected.getIdWorker() + " NON può costruire 2 blocchi!");
                }
                coords = ActionManager.insertCoordinateConstruction(workerSelected);
                workerSelected.buildBlock(false, coords[0], coords[1]);
            }
        } else{
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }
    }

    private static void actionPan(Player player){
        int[] coords;
        System.out.println("MOVIMENTO:");
        coords =  ActionManager.insertCoordinateMovement(workerSelected, player.getGodChoice());
        if(movementPan(ActionManager.insertCoordinateMovement(workerSelected, player.getGodChoice()))){
            return;
        }
        System.out.println("COSTRUZIONE:");
        if(workerSelected.canBuild()) {
            construction(ActionManager.insertCoordinateConstruction(workerSelected));
        } else{
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }
    }

    private static void actionPrometheus(Player player){
        if(workerSelected.canBuild()) {
            System.out.println("Vuoi costruire prima di muoverti? (Yes o No)");
            String answer = ActionManager.yesOrNo();
            if (answer.equals("yes")) {
                construction(ActionManager.insertCoordinateConstruction(workerSelected));
                setAllowHeightPrometheus(false);
            }
        } else{
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }
        actionDefault(player);
        setAllowHeightPrometheus(true);
    }

    private static void actionDefault(Player player) {
        System.out.println("MOVIMENTO:");
        if (movement(ActionManager.insertCoordinateMovement(workerSelected, player.getGodChoice()))) {
            return;
        }
        System.out.println("COSTRUZIONE:");
        if(workerSelected.canBuild()) {
            construction(ActionManager.insertCoordinateConstruction(workerSelected));
        } else{
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }
    }

     */

    public static boolean movement(int x, int y){
        if(workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 3) {
            workerSelected.changePosition(x, y);
            Map.getInstance().print();
            GameManager.printPlayerInGame();
            GameManager.setVictory();
            return true;
        } else{
            workerSelected.changePosition(x, y);
            Map.getInstance().print();
            GameManager.printPlayerInGame();
            return false;
        }
    }

    public static boolean movementPan(int x, int y){
        if((workerSelected.getCoordZ() == 3 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 1)
                || (workerSelected.getCoordZ() == 3 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 0)
                || (workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 0)
                || (workerSelected.getCoordZ() == 2 && Map.getInstance().getCellBlockType(x, y).getAbbreviation() == 3)){
            workerSelected.changePosition(x, y);
            Map.getInstance().print();
            GameManager.printPlayerInGame();
            GameManager.setVictory();
            return true;
        } else{
            workerSelected.changePosition(x, y);
            Map.getInstance().print();
            GameManager.printPlayerInGame();
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

}

