package it.polimi.ingsw.model.cards;

import java.util.HashMap;

public enum God {

    APOLLO, ARTEMIS, ATHENA, ATLAS, CHRONUS, DEMETER, HEPHAESTUS, HERA, HESTIA, MINOTAUR, PAN, PROMETHEUS, TRITON, ZEUS;

    static HashMap<God, String> godDescription = new HashMap<>() {
        {
            put(APOLLO, "Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated");
            put(ARTEMIS, "Your Worker may move one additional time, but not back to its initial space.");
            put(ATHENA, "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.");
            put(ATLAS, "Your Worker may build a dome at any level.");
            put(CHRONUS, "You also win when there are at least five complete Towers on the board.");
            put(DEMETER, "Your Worker may build one additional time, but not on the same space.");
            put(HEPHAESTUS, "Your Worker may build one additional block (not dome) on top of your first block.");
            put(HERA, "An opponent cannot win by moving into a perimeter space.");
            put(HESTIA, "Your Worker may build one additional time, but this cannot be on a perimeter space.");
            put(MINOTAUR, "Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.");
            put(PAN, "You also win if your Worker moves down two or more levels.");
            put(PROMETHEUS, "If your Worker does not move up, it may build both before and after moving.");
            put(TRITON, "Each time your Worker moves into a perimeter space, it may immediately move again.");
            put(ZEUS, "Your Worker may build a block under itself.");
        }
    };

    public static String getGodDescription(God god){
        return godDescription.get(god);
    }

}
