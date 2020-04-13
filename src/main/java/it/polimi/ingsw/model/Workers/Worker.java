package it.polimi.ingsw.model.Workers;

import it.polimi.ingsw.controller.GameManager;
import it.polimi.ingsw.controller.TurnManager;
import it.polimi.ingsw.model.Board.BlockType;
import it.polimi.ingsw.model.Board.Map;

public abstract class Worker {

    private String idWorker;
    private int coordX, coordY, coordZ;

    public Worker(String idWorker, int coordX, int coordY) {
        this.idWorker = idWorker;
        this.coordX = coordX;
        this.coordY = coordY;
        this.coordZ = 0;
    }

    public String getIdWorker() {
        return idWorker;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public int getCoordZ() {
        return coordZ;
    }

    public void setCoordZ(int coordZ) {
        this.coordZ = coordZ;
    }

    public boolean canMove() {
        for (int i = coordX - 1; i <= coordX + 1; i++) {           //se no funziona --> while
            for (int j = coordY - 1; j <= coordY + 1; j++) {
                System.out.println(i + " e " + j);
                if (!(i == coordX && j == coordY) && Map.isAcceptable(i, j) && Map.noWorkerHere(i, j) && Map.getCellBlockType(i, j) != BlockType.CUPOLA){
                    if(TurnManager.cannotGoUp()){
                        if(Map.getCellBlockType(i, j).getAbbreviation() <= coordZ) {
                            System.out.println(i + " e " + j + "trovato");
                            return true;
                        }
                    }else{
                        if(Map.getCellBlockType(i, j).getAbbreviation() <= coordZ + 1) {
                            System.out.println(i + " e " + j + "trovato");
                            return true;
                        }
                    }
                }
            }
        }
        System.out.println("canMove = false");
        return false;
    }
/*
        int i, j;

        j=coordY -1;
        for(i=coordX -1;i <= coordX + 1; i++) {
            System.out.println(i + " e " + j);
              if (Map.isAcceptable(i, j)) {
                  if (TurnManager.cannotGoUp()) {
                      if (Map.noWorkerHere(i, j) && (Map.getCellBlockType(i, j).getAbbreviation() <= coordZ) && (Map.getCellBlockType(i, j) != BlockType.CUPOLA)) {
                          System.out.println(i + " e " + j + "trovato");
                          return true;
                      }
                  }
                  if (!TurnManager.cannotGoUp()) {
                      if (Map.noWorkerHere(i, j) && (Map.getCellBlockType(i, j).getAbbreviation() <= coordZ + 1) && (Map.getCellBlockType(i, j) != BlockType.CUPOLA)) {
                          System.out.println(i + " e " + j + "trovato");
                          return true;
                      }
                  }
              }
        }

        j=coordY +1;
        for(i=coordX -1;i <= coordX + 1; i++){
            System.out.println(i + " e " + j);
            if (Map.isAcceptable(i,j)) {
                if (TurnManager.cannotGoUp()){
                    if (Map.noWorkerHere(i, j) && (Map.getCellBlockType(i, j).getAbbreviation() <= coordZ) && (Map.getCellBlockType(i, j) != BlockType.CUPOLA)) {
                        System.out.println(i + " e " + j + "trovato");
                        return true;
                    }
                }
                if (!TurnManager.cannotGoUp()) {
                    if (Map.noWorkerHere(i, j) && (Map.getCellBlockType(i, j).getAbbreviation() <= coordZ + 1) && (Map.getCellBlockType(i, j) != BlockType.CUPOLA)) {
                        System.out.println(i + " e " + j + "trovato");
                        return true;
                    }
                }
            }
        }

        j=coordY;
        i=coordX-1;
        System.out.println(i + " e " + j);
        if (Map.isAcceptable(i,j)) {
            if (TurnManager.cannotGoUp()) {
                if (Map.noWorkerHere(i, j) && (Map.getCellBlockType(i, j).getAbbreviation() <= coordZ) && (Map.getCellBlockType(i, j) != BlockType.CUPOLA)) {
                    System.out.println(i + " e " + j + "trovato");
                    return true;
                }
            }
            if (!TurnManager.cannotGoUp()) {
                if (Map.noWorkerHere(i, j) && (Map.getCellBlockType(i, j).getAbbreviation() <= coordZ + 1) && (Map.getCellBlockType(i, j) != BlockType.CUPOLA)) {
                    System.out.println(i + " e " + j + "trovato");
                    return true;
                }
            }
        }

        j=coordY;
        i=coordX+1;
        System.out.println(i + " e " + j);
        if (Map.isAcceptable(i,j)) {
            if (TurnManager.cannotGoUp()){
                if (Map.noWorkerHere(i, j) && (Map.getCellBlockType(i, j).getAbbreviation() <= coordZ) && (Map.getCellBlockType(i, j) != BlockType.CUPOLA)) {
                    System.out.println(i + " e " + j + "trovato");
                    return true;
                }
            }
            if (!TurnManager.cannotGoUp()) {
                if (Map.noWorkerHere(i, j) && (Map.getCellBlockType(i, j).getAbbreviation() <= coordZ + 1) && (Map.getCellBlockType(i, j) != BlockType.CUPOLA)) {
                    System.out.println(i + " e " + j + "trovato");
                    return true;
                }
            }
        }

        return false;
    }

    /*
    public boolean canMove(){
        boolean check1 = false, check2 = false, check3 = false, check4 = false;
        for(int i = coordX - 1; i < coordX ; i++){
            for(int j = coordY - 1; j < coordY + 1; j++) {
                if ((i != coordX && j != coordY) && (Map.noWorkerHere(i, j)) && (!TurnManager.cannotGoUp() && !(Map.getCellBlockType(i, j).getAbbreviation() > coordZ +1)) && !(Map.getCellBlockType(i, j).getAbbreviation() >= coordZ + 2) && !(Map.getCellBlockType(i, j) == BlockType.CUPOLA)) {
                    check1 = true;
                }
            }
        }

        for (int i = coordX; i <= coordX; i++){
            for(int j = coordY - 1; j <= coordY - 1; j++) {
                if ((i != coordX && j != coordY) && Map.noWorkerHere(i, j) && (!TurnManager.cannotGoUp() && !(Map.getCellBlockType(i, j).getAbbreviation() > coordZ +1)) && !(Map.getCellBlockType(i, j).getAbbreviation() >= coordZ + 2) && !(Map.getCellBlockType(i, j) == BlockType.CUPOLA)) {
                    check2 = true;
                }
            }
            for(int j = coordY + 1; j <= coordY + 1; j++) {
                //if ((i == coordX && j == coordY) || !Map.noWorkerHere(i, j) || (TurnManager.cannotGoUp() && Map.getCellBlockType(i, j).getAbbreviation() > coordZ) || Map.getCellBlockType(i, j).getAbbreviation() >= coordZ + 2 || Map.getCellBlockType(i, j) == BlockType.CUPOLA) {
                if ((i != coordX && j != coordY) && Map.noWorkerHere(i, j) && (!TurnManager.cannotGoUp()) && !(Map.getCellBlockType(i, j).getAbbreviation() > coordZ +1)) && !(Map.getCellBlockType(i, j).getAbbreviation() >= coordZ + 2) && !(Map.getCellBlockType(i, j) == BlockType.CUPOLA)) {
                    check3 = true;
                }
            }

        }

        for(int i = coordX + 1; i <= coordX + 1 ; i++){
            for(int j = coordY - 1; j < coordY + 1; j++) {
                //if ((i == coordX && j == coordY) || !Map.noWorkerHere(i, j) || (TurnManager.cannotGoUp() && Map.getCellBlockType(i, j).getAbbreviation() > coordZ) || Map.getCellBlockType(i, j).getAbbreviation() >= coordZ + 2 || Map.getCellBlockType(i, j) == BlockType.CUPOLA) {
                if ((i != coordX && j != coordY) && Map.noWorkerHere(i, j) && (!TurnManager.cannotGoUp() && !(Map.getCellBlockType(i, j).getAbbreviation() > coordZ +1)) && !(Map.getCellBlockType(i, j).getAbbreviation() >= coordZ + 2) && !(Map.getCellBlockType(i, j) == BlockType.CUPOLA)) {
                    check4 = true;
                }
            }
        }

        if(check1==true || check2==true || check3==true || check4==true)
            return true;
        else
            return false;
    }*/

    public void changePosition(int newX, int newY){
        if(coordZ == 2 && Map.getCellBlockType(newX, newY).getAbbreviation() == 3){
            coordX = newX;
            coordY = newY;
            coordZ = Map.getCellBlockType(newX, newY).getAbbreviation();
            //setWorkerInCell NO?
            GameManager.setVictory();
        }else{
            coordX = newX;
            coordY = newY;
            coordZ = Map.getCellBlockType(newX, newY).getAbbreviation();
        }
    }

    public boolean canBuild(){
        for (int i = getCoordX() - 1; i <= getCoordX() + 1; i++) {
            for (int j = getCoordY() - 1; j <= getCoordY() + 1; j++) {
                if (!(i == getCoordX() && j == getCoordY()) && Map.isAcceptable(i, j) && Map.noWorkerHere(i, j) && Map.getCellBlockType(i, j) != BlockType.CUPOLA) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canBuild(boolean buildAgain) {       //solo per sottoclassi se no dava fastidio l'override HEPHAESTUS
        return true;
    }

    public void buildBlock(int buildX, int buildY){
        if(Map.getCellBlockType(buildX, buildY) == BlockType.GROUND){
            Map.setCellBlockType(buildX, buildY, BlockType.BLOCK1);
        }else if(Map.getCellBlockType(buildX, buildY) == BlockType.BLOCK1){
            Map.setCellBlockType(buildX, buildY, BlockType.BLOCK2);
        }else if(Map.getCellBlockType(buildX, buildY) == BlockType.BLOCK2){
            Map.setCellBlockType(buildX, buildY, BlockType.BLOCK3);
        }else if(Map.getCellBlockType(buildX, buildY) == BlockType.BLOCK3){
            Map.setCellBlockType(buildX, buildY, BlockType.CUPOLA);
        }
    }

    public void buildBlock(boolean build, int buildX, int buildY) {         //solo per sottoclassi se no dava fastidio l'override HEPHAESTUS e ATLAS

    }

}
