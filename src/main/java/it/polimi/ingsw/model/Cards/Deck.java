package it.polimi.ingsw.model.Cards;

public class Deck {

    private static God[] godSelected;
    private static boolean[] availability;

    public Deck(int numberOfCards){
        int numberOfGods = God.values().length;
        God[] godList = new God[numberOfGods];
        int i = 0;
        for(God d: God.values()) {
            godList[i] = d;
            i++;
        }
        int[] casual = new int[numberOfCards];
        casual[0] = (int) (Math.random() * numberOfGods);
        while (true){
            int random = (int) (Math.random() * numberOfGods);
            if (random != casual[0]) {
                casual[1] = random;
                if (numberOfCards == 3){
                    while (true){
                        random = (int) (Math.random() * numberOfGods);
                        if (random != casual[0] && random != casual[1]) {
                            casual[2] = random;
                            break;
                        }
                    }
                }
                break;
            }
        }
        godSelected = new God[numberOfCards];
        availability = new boolean[numberOfCards];
        for (i = 0; i < numberOfCards; i++){
            godSelected[i] = godList[casual[i]];
            availability[i] = true;
        }
    }

    public static God[] getCardsSelected(){
        return godSelected;
    }

    public static boolean[] getAvailability() {
        return availability;
    }

    public static God getCardToPlayer(int indexOfCardSelected){
        availability[indexOfCardSelected-1] = false;
        return godSelected[indexOfCardSelected-1];
    }

}
