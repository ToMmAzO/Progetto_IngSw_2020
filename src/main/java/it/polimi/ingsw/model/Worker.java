package it.polimi.ingsw.model;

public abstract class Worker {

    private String idWorker;
    private int[] coord;
    private Color color;

    public void setIdWorker(String idWorker) {
        this.idWorker = idWorker;
    }

    public void setCoord() {
        //codice
    }

    public String getIdWorker() {
        return idWorker;
    }

    public int[] getCoord() {
        return coord;
    }

    public Color getColor() {
        return color;
    }

    public void changePosition(){
        //codice
    }

    public void buildBlock(){
        //codice
    }

}
