package it.polimi.ingsw.model.Cards;

import static it.polimi.ingsw.model.Cards.Divinity.godDescription;

public class Deck {

    private static God[] godList;

    public Deck(){
        godList = new God[Divinity.values().length];
        int i = 0;
        for(Divinity d: Divinity.values()) {
            godList[i] = new God(d, godDescription.get(d));
            i++;
        }
    }

    public static God[] extractCards(int number){
        int numberOfDivinities = Divinity.values().length;
        int[] casual = new int[number];
        casual[0] = (int) (Math.random() * numberOfDivinities);
        while (true){
            int random = (int) (Math.random() * numberOfDivinities);
            if (random != casual[0]) {
                casual[1] = random;
                break;
            }
        }
        if (number == 3){
            while (true){
                int random = (int) (Math.random() * numberOfDivinities);
                if (random != casual[0] && random != casual[1]) {
                    casual[2] = random;
                    break;
                }
            }
        }
        God[] selected = new God[number];
        for (int i = 0; i < number; i++){
            selected[i] = (godList[casual[i]]);
        }
        return selected;
    }

}
