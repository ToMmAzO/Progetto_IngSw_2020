package it.polimi.ingsw.model;

public abstract class Worker {

    private int coordX, coordY, coordZ;
    private Color playerColor;


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

    public Color getPlayerColor() {
        return playerColor;
    }

    public void changePosition(){
        //codice
    }

    public void buildBlock(){
        //codice
    }

}
