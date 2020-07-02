package it.polimi.ingsw.model.player;

import java.util.HashMap;

public enum Color {

    RED, YELLOW, BLUE;

    static HashMap<Color, Boolean> availability = new HashMap<>();

    /**
     * initialize the hashmap with the 3 colors as keys and set them to 'true'
     * ('true' means the color is still available)
     */
    public static void initialization(){
        availability.put(RED, true);
        availability.put(YELLOW, true);
        availability.put(BLUE, true);
    }

    public static Boolean getAvailability(Color color) {
        return availability.get(color);
    }

    public static void setAvailabilityToFalse(Color color) {
        availability.put(color, false);
    }

}
