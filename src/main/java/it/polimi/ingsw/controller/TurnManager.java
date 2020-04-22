package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Board.Map;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Workers.Worker;
import it.polimi.ingsw.view.ViewModel;
import it.polimi.ingsw.view.ViewTurn;

public class TurnManager {

    private static Worker workerSelected;
    static boolean allowHeight = true;
    static boolean allowHeightPrometheus = true;

    public static boolean startTurn(Player player){
        return verifyRegularity(player, ViewTurn.turn(player));
    }

    public static boolean verifyRegularity(Player player, int selectionWorker){
        workerSelected = player.getWorkerSelected(selectionWorker);
        if(workerSelected.canMove()){
            selectAction(player);
        } else{
            System.out.println(workerSelected.getIdWorker() + " non può muoversi! Seleziono l'altro Worker.");
            if (selectionWorker == 1){
                selectionWorker ++;
            } else{
                selectionWorker --;
            }
            workerSelected = player.getWorkerSelected((selectionWorker));
            if (workerSelected.canMove()){
                System.out.println("Il worker selezionato è: "+ workerSelected.getIdWorker() + ".");
                selectAction(player);
            } else{
                System.out.println("Nemmeno " + workerSelected.getIdWorker() + " può muoversi!");
                GameManager.deletePlayer(player);
                return false;
            }
        }
        workerSelected = null;
        return true;
    }

    private static void selectAction(Player player){
        switch (player.getGodChoice()) {
            case ARTEMIS:
                actionArtemis(player);
                break;

            case ATLAS:
                actionAtlas(player);
                break;

            case DEMETER:
                actionDemeter(player);
                break;

            case HEPHAESTUS:
                actionHephaestus(player);
                break;

            case PAN:
                actionPan(player);
                break;

            case PROMETHEUS:
                actionPrometheus(player);
                break;

            default://APOLLO, ATHENA, ATLAS, MINOTAUR
                if(movement(player)){
                    break;
                }
                construction();
                break;
        }
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

    private static void actionArtemis(Player player){
        String answer;
        int[] coords;

        int x = workerSelected.getCoordX();
        int y = workerSelected.getCoordY();

        if(movement(player)){
            return;
        }

        if(workerSelected.canMove(x, y)){
            System.out.println("Vuoi muovere ancora? (Yes o No)");
            answer = ActionManager.yesOrNo();

            if(answer.equals("yes")) {
                System.out.println("MOVIMENTO: ");
                coords = ActionManager.insertCoordinateMovement(workerSelected, player.getGodChoice());
                while (x == coords[0] && y == coords[1]) {
                    System.out.println("Inserisci delle coordinate DIVERSE da quelle di partenza!");
                    coords = ActionManager.insertCoordinateMovement(workerSelected, player.getGodChoice());
                }

                if(workerSelected.getCoordZ() == 2 && Map.getCellBlockType(coords[0], coords[1]).getAbbreviation() == 3) {
                    workerSelected.changePosition(coords[0], coords[1]);
                    ViewModel.printMap();
                    GameManager.printPlayerInGame();
                    GameManager.setVictory();
                    return;
                }else{
                    workerSelected.changePosition(coords[0], coords[1]);
                    ViewModel.printMap();
                    GameManager.printPlayerInGame();
                }
            }
        }else{
            System.out.println(workerSelected.getIdWorker() + " NON può muoversi una seconda volta!");
        }

        construction();
    }

    private static void actionAtlas(Player player){
        String answer;
        int[] coords;

        if(movement(player)){
            return;
        }

        if(workerSelected.canBuild()) {
            System.out.println("Vuoi costruire una CUPOLA? (Yes o No)");
            answer = ActionManager.yesOrNo();

            if(answer.equals("yes")){
                System.out.println("COSTRUZIONE: ");
                coords =  ActionManager.insertCoordinateConstruction(workerSelected);

                workerSelected.buildBlock(true, coords[0], coords[1]);
            }else{
                construction();
            }
        }else{
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }
    }

    private static void actionDemeter(Player player){
        String answer;
        int[] coords;
        int x, y;

        if (movement(player)){
            return;
        }

        if(workerSelected.canBuild()) {
            System.out.println("COSTRUZIONE: ");
            coords =  ActionManager.insertCoordinateConstruction(workerSelected);

            x = coords[0];
            y = coords[1];

            workerSelected.buildBlock(coords[0], coords[1]);

            ViewModel.printMap();
            GameManager.printPlayerInGame();

            System.out.println("Vuoi costruire ancora? (Yes o No)");
            answer = ActionManager.yesOrNo();

            if(answer.equals("yes")){
                System.out.println("COSTRUZIONE: ");
                coords =  ActionManager.insertCoordinateConstruction(workerSelected);
                while (x == coords[0] && y == coords[1]) {
                    System.out.println("Inserisci delle coordinate DIVERSE da quelle dove hai costruito prima!");
                    coords = ActionManager.insertCoordinateConstruction(workerSelected);
                }

                workerSelected.buildBlock(coords[0], coords[1]);
            }
        }else{
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }
    }

    private static void actionHephaestus(Player player){
        int[] coords;
        String answer;

        if (movement(player)){
            return;
        }

        if(workerSelected.canBuild()) {
            System.out.println("Vuoi costruire 2 volte? (Yes o No)");
            answer = ActionManager.yesOrNo();

            if ((answer.equals("yes")) && workerSelected.canBuild(true)) {
                System.out.println("COSTRUZIONE: ");
                coords = ActionManager.insertCoordinateConstruction(workerSelected);

                workerSelected.buildBlock(true, coords[0], coords[1]);
            } else {
                if (!workerSelected.canBuild(true)) {
                    System.out.println(workerSelected.getIdWorker() + " NON può costruire 2 volte!");
                }
                construction();
            }
        } else {
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }
    }

    private static void actionPan(Player player){
        int[] coords;
        System.out.println("MOVIMENTO: ");
        coords =  ActionManager.insertCoordinateMovement(workerSelected, player.getGodChoice());

        if((workerSelected.getCoordZ() == 3 && Map.getCellBlockType(coords[0], coords[1]).getAbbreviation() == 1) || (workerSelected.getCoordZ() == 3 && Map.getCellBlockType(coords[0], coords[1]).getAbbreviation() == 0) || (workerSelected.getCoordZ() == 2 && Map.getCellBlockType(coords[0], coords[1]).getAbbreviation() == 0) || (workerSelected.getCoordZ() == 2 && Map.getCellBlockType(coords[0], coords[1]).getAbbreviation() == 3)){
            workerSelected.changePosition(coords[0], coords[1]);
            ViewModel.printMap();
            GameManager.printPlayerInGame();
            GameManager.setVictory();
            return;
        }else{
            workerSelected.changePosition(coords[0], coords[1]);
            ViewModel.printMap();
            GameManager.printPlayerInGame();
        }

        construction();
    }

    private static void actionPrometheus(Player player){
        String answer;

        if (workerSelected.canBuild()) {
            System.out.println("Vuoi costruire prima di muoverti? (Yes o No)");
            answer = ActionManager.yesOrNo();

            if (answer.equals("yes")) {
                construction();
                setAllowHeightPrometheus(false);
            }

        }else {
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }

        if (movement(player)){
            return;
        }

        construction();

        setAllowHeightPrometheus(true);
    }

    private static boolean movement(Player player){
        int[] coords;
        System.out.println("MOVIMENTO: ");
        coords =  ActionManager.insertCoordinateMovement(workerSelected, player.getGodChoice());

        if(workerSelected.getCoordZ() == 2 && Map.getCellBlockType(coords[0], coords[1]).getAbbreviation() == 3) {
            workerSelected.changePosition(coords[0], coords[1]);
            ViewModel.printMap();
            GameManager.printPlayerInGame();
            GameManager.setVictory();
            return true;
        }else{
            workerSelected.changePosition(coords[0], coords[1]);
            ViewModel.printMap();
            GameManager.printPlayerInGame();
            return false;
        }
    }

    private static void construction(){
        int[] coords;
        if(workerSelected.canBuild()) {
            System.out.println("COSTRUZIONE: ");
            coords =  ActionManager.insertCoordinateConstruction(workerSelected);

            workerSelected.buildBlock(coords[0], coords[1]);
        }else{
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }
    }
}

