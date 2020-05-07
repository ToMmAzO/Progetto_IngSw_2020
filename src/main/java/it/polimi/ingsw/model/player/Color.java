package it.polimi.ingsw.model.player;

import java.util.HashMap;

public enum Color {

    RED, YELLOW, BLUE;

    static HashMap<Color, Boolean> availability = new HashMap<>() {
        {
            put(RED, true);
            put(YELLOW, true);
            put(BLUE, true);
        }
    };

    public static Boolean getAvailability(Color color) {
        return availability.get(color);
    }

    public static void setAvailabilityToFalse(Color color) {
        availability.put(color, false);
    }

}