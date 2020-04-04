package it.polimi.ingsw.model;

public class God {

    private final Divinity godName;
    private final String godPower;

    public God(Divinity godName, String godPower){
        this.godName = godName;
        this.godPower = godPower;
    }

    public Divinity getGodName() {
        return godName;
    }

    public String getGodPower() {
        return godPower;
    }

}
