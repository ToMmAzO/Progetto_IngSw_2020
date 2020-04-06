package it.polimi.ingsw.model.Cards;

public class Deck {

    private static God[] godList;

    public Deck(){
        godList = new God[9];
        GodDescription.setList();
        int i = 0;
        for(Divinity d: Divinity.values()) {
            godList[i] = new God(d, GodDescription.list[i]);
            i++;
        }
    }

    public static God[] extractCards(int number){
        int[] casual = new int[number];
        casual[0] = (int) (Math.random() * 9);
        while (true){
            int random = (int) (Math.random() * 9);
            if (random != casual[0]) {
                casual[1] = random;
                break;
            }
        }
        if (number == 3){
            while (true){
                int random = (int) (Math.random() * 9);
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
