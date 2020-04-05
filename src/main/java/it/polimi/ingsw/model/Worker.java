package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Board.Map;

public abstract class Worker {

    private String idWorker;
    private int coordX, coordY, coordZ;
    private Color color;    //inutile?

    public Worker(Player p, int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.coordZ = 0;
        this.color = p.getColor();
    }

    public void setIdWorker(String idWorker) {
        this.idWorker = idWorker;
    }

    public String getIdWorker() {
        return idWorker;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public int getCoordZ() {
        return coordZ;
    }

    public Color getColor() {
        return color;
    }

    public boolean canMove(Map field){
        for(int i = coordX - 1; i < coordX + 1; i++){
            for(int j = coordY - 1; j < coordY + 1; j++) {
                if (i != coordX && j != coordY) {
                    if (field.getCell(i, j).getWorkerToken() != null || field.getCell(i, j).getBlock().getAbbreviation() >= coordZ + 2 || field.getCell(i, j).getBlock() == BlockType.CUPOLA) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean changePosition(Map field, int newX, int newY){
        if(coordZ == 2 && field.getCell(newX, newY).getBlock().getAbbreviation() == 3){
            coordX = newX;
            coordY = newY;
            coordZ = field.getCell(newX, newY).getBlock().getAbbreviation();
            return true;        //endgame
        }else{
            coordX = newX;
            coordY = newY;
            coordZ = field.getCell(newX, newY).getBlock().getAbbreviation();
            return false;
        }
    }

    public boolean canBuild(Map field){
        for(int i = coordX - 1; i < coordX + 1; i++){
            for(int j = coordY - 1; j < coordY + 1; j++) {
                if (i != coordX && j != coordY) {
                    if (field.getCell(i, j).getWorkerToken() != null || field.getCell(i, j).getBlock() == BlockType.CUPOLA) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void buildBlock(Map field, int buildX, int buildY){
        if(field.getCell(buildX, buildY).getBlock() == BlockType.EMPTY){
            field.getCell(buildX, buildY).setBlock(BlockType.BLOCK1);
        }else if(field.getCell(buildX, buildY).getBlock() == BlockType.BLOCK1){
            field.getCell(buildX, buildY).setBlock(BlockType.BLOCK2);
        }else if(field.getCell(buildX, buildY).getBlock() == BlockType.BLOCK2){
            field.getCell(buildX, buildY).setBlock(BlockType.BLOCK3);
        }else if(field.getCell(buildX, buildY).getBlock() == BlockType.BLOCK3){
            field.getCell(buildX, buildY).setBlock(BlockType.CUPOLA);
        }
    }

}
