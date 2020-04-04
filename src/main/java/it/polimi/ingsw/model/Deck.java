package it.polimi.ingsw.model;

public class Deck {

    private God[] godList;

    public Deck(){
        godList= new God[9];
        int i = 0;
        for(Divinity d: Divinity.values()) {
            godList[i] = new God(d, GodDescription.list[i]);
            i++;
        }
    }

    public void shuffle(){
        //codice
    }

}
