package it.polimi.ingsw.model.Cards;

public class Deck {

    private static God[] godSelected;
    private static boolean[] availability;

    public Deck(int numberOfCards){
        God[] godList = new God[God.values().length];
        int i = 0;
        for(God d: God.values()) {
            godList[i] = d;
            i++;
        }
        int[] casual = extractCasualNumbers(numberOfCards);
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

    private static int[] extractCasualNumbers(int numberOfNumbers){
        int numberOfGods = God.values().length;
        int[] casual = new int[numberOfNumbers];
        casual[0] = (int) (Math.random() * numberOfGods);
        while (true){
            int random = (int) (Math.random() * numberOfGods);
            if (random != casual[0]) {
                casual[1] = random;
                if (numberOfNumbers == 3){
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
        return casual;
    }

}
