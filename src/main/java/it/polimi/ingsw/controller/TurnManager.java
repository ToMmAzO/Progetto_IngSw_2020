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
            System.out.println("Il worker selezionato è: "+ workerSelected.getIdWorker() + ".");
            if (workerSelected.canMove()){
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
            case ARTEMIS -> actionArtemis(player);
            case ATLAS -> actionAtlas(player);
            case DEMETER -> actionDemeter(player);
            case HEPHAESTUS -> actionHephaestus(player);
            case PAN -> actionPan(player);
            case PROMETHEUS -> actionPrometheus(player);
            default -> actionDefault(player);               //APOLLO, ATHENA, ATLAS, MINOTAUR
        }
    }

    private static void actionArtemis(Player player){
        String answer;
        int[] coords;

        int x = workerSelected.getCoordX();
        int y = workerSelected.getCoordY();

        if(movement(ViewTurn.insertCoordinateMovement(workerSelected, player.getGodChoice()))){
            return;
        }

        if(workerSelected.canMove(x, y)){
            System.out.println("Vuoi muovere ancora? (Yes o No)");
            answer = ViewTurn.yesOrNo();

            if(answer.equals("yes")) {
                System.out.println("MOVIMENTO: ");
                coords = ViewTurn.insertCoordinateMovement(workerSelected, player.getGodChoice());
                while (x == coords[0] && y == coords[1]) {
                    System.out.println("Inserisci delle coordinate DIVERSE da quelle di partenza!");
                    coords = ViewTurn.insertCoordinateMovement(workerSelected, player.getGodChoice());
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

        if(workerSelected.canBuild()) {
            construction(ViewTurn.insertCoordinateConstruction(workerSelected));
        }else{
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }
    }

    private static void actionAtlas(Player player){
        String answer;
        int[] coords;

        if(movement(ViewTurn.insertCoordinateMovement(workerSelected, player.getGodChoice()))){
            return;
        }

        if(workerSelected.canBuild()) {
            System.out.println("Vuoi costruire una CUPOLA? (Yes o No)");
            answer = ViewTurn.yesOrNo();

            if(answer.equals("yes")){
                System.out.println("COSTRUZIONE: ");
                coords =  ViewTurn.insertCoordinateConstruction(workerSelected);

                workerSelected.buildBlock(true, coords[0], coords[1]);
            }else{
                construction(ViewTurn.insertCoordinateConstruction(workerSelected));
            }
        }else{
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }
    }

    private static void actionDemeter(Player player){
        String answer;
        int[] coords;
        int x, y;

        if (movement(ViewTurn.insertCoordinateMovement(workerSelected, player.getGodChoice()))){
            return;
        }

        if(workerSelected.canBuild()) {
            System.out.println("COSTRUZIONE: ");
            coords =  ViewTurn.insertCoordinateConstruction(workerSelected);

            x = coords[0];
            y = coords[1];

            workerSelected.buildBlock(coords[0], coords[1]);

            ViewModel.printMap();
            GameManager.printPlayerInGame();

            System.out.println("Vuoi costruire ancora? (Yes o No)");
            answer = ViewTurn.yesOrNo();

            if(answer.equals("yes")){
                System.out.println("COSTRUZIONE: ");
                coords =  ViewTurn.insertCoordinateConstruction(workerSelected);
                while (x == coords[0] && y == coords[1]) {
                    System.out.println("Inserisci delle coordinate DIVERSE da quelle dove hai costruito prima!");
                    coords = ViewTurn.insertCoordinateConstruction(workerSelected);
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

        if (movement(ViewTurn.insertCoordinateMovement(workerSelected, player.getGodChoice()))){
            return;
        }

        if(workerSelected.canBuild()) {
            System.out.println("Vuoi costruire 2 volte? (Yes o No)");
            answer = ViewTurn.yesOrNo();

            if ((answer.equals("yes")) && workerSelected.canBuild(true)) {
                System.out.println("COSTRUZIONE: ");
                coords = ViewTurn.insertCoordinateConstruction(workerSelected);

                workerSelected.buildBlock(true, coords[0], coords[1]);
            } else {
                if (!workerSelected.canBuild(true)) {
                    System.out.println(workerSelected.getIdWorker() + " NON può costruire 2 volte!");
                }
                construction(ViewTurn.insertCoordinateConstruction(workerSelected));
            }
        } else {
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }
    }

    private static void actionPan(Player player){
        int[] coords;
        System.out.println("MOVIMENTO: ");
        coords =  ViewTurn.insertCoordinateMovement(workerSelected, player.getGodChoice());

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

        if(workerSelected.canBuild()) {
            construction(ViewTurn.insertCoordinateConstruction(workerSelected));
        }else{
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }
    }

    private static void actionPrometheus(Player player){
        String answer;

        if (workerSelected.canBuild()) {
            System.out.println("Vuoi costruire prima di muoverti? (Yes o No)");
            answer = ViewTurn.yesOrNo();

            if (answer.equals("yes")) {
                construction(ViewTurn.insertCoordinateConstruction(workerSelected));
                setAllowHeightPrometheus(false);
            }

        }else {
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }

        if (movement(ViewTurn.insertCoordinateMovement(workerSelected, player.getGodChoice()))){
            return;
        }

        if(workerSelected.canBuild()) {
            construction(ViewTurn.insertCoordinateConstruction(workerSelected));
        }else{
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }

        setAllowHeightPrometheus(true);
    }

    private static void actionDefault(Player player) {
        if (movement(ViewTurn.insertCoordinateMovement(workerSelected, player.getGodChoice()))) {
            return;
        }

        if(workerSelected.canBuild()) {
            construction(ViewTurn.insertCoordinateConstruction(workerSelected));
        }else{
            System.out.println(workerSelected.getIdWorker() + " NON può costruire!");
        }
    }

    private static boolean movement(int[] coords){
        System.out.println("MOVIMENTO: ");
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

    private static void construction(int[] coords){
        System.out.println("COSTRUZIONE: ");
        workerSelected.buildBlock(coords[0], coords[1]);
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

