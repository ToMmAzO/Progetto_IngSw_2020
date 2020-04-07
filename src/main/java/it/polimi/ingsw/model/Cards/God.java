package it.polimi.ingsw.model.Cards;

public class God {

    private Divinity godName;
    private String godPower;

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
